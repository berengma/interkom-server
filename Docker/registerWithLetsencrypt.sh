#!/usr/bin/bash
if [[ "$EUID" -ne 0 ]]; then
    echo "This script must be run as root." >&2
    exit 1
fi

url_re='^[^.[:space:]]+\.[^.[:space:]]+\.[^.[:space:]]+$'
config_file=".env"

# Verify parameter with given email address
if [[ $# -lt 1 || -z "$1" ]]; then
    echo "Error: specifying an email address is mandatory." >&2
    echo "Usage: $0 'your@email.here'" >&2
    exit 1
fi
EMAIL=$1

echo "Reading configuration from .env file!"
while IFS='=' read -r key value; do
    # Ignore blank lines and comments
    [[ -z "$key" || "$key" == \#* ]] && continue
    if [[ "$key" == "SERVERNAME" ]]; then
        export "$key=$value"
        servername="$value"
        break
    fi
done < "$config_file"

# Verify if the servername can be used with Letsencrypt
if ! [[ -n "$servername" && "$servername" =~ $url_re ]]; then
    echo "'$servername' is not a valid URL, which can be used with letsencrypt."
    exit 1
fi

echo "'$servername' will now be registered by certbot! ..."

if docker compose run --rm certbot certonly \
  --webroot \
  --webroot-path /var/www/certbot \
  --domain "$servername" \
  --email "$EMAIL" \
  --agree-tos \
  --no-eff-email
then
    echo "Certificate successfully obtained"
else
    exit_code=$?
    echo "Certbot failed with exit code: $exit_code" >&2
    exit "$exit_code"
fi

# Save email in .env file
printf 'EMAIL=%s\n' "$EMAIL" >> .env

# Replace protokoll in .env file
sed -i '/^INTERKOM_URL=/s|http://|https://|' .env

# activate https SSL block in the nginx.conf template
sed -i 's/^#//' ./nginx.conf.d/default.conf.template

# Save current path
SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd -P)"

# Generate renewal script
CRON_FILE="/etc/cron.d/interkom-cert-renewal"
SCRIPT="/etc/interkom-server/cert-renewal.sh"

cat > "$SCRIPT" <<EOF
#!/usr/bin/bash
cd $SCRIPT_DIR
if docker compose run --rm certbot renew; then
   docker compose restart reverse-proxy
else
   echo "Certificate renewal failed" >&2
   exit 1
fi
EOF
chmod 700 "$SCRIPT"

# Register new job to cron
cat > "$CRON_FILE" <<EOF
# Renew Interkom TLS certificates twice daily
17 0,12 * * * root $SCRIPT >> /var/log/interkom-cert-renewal.log 2>&1
EOF
chmod 644 "$CRON_FILE"
echo "Cron job installed in $CRON_FILE"

# Restart all containers
docker compose restart --build
