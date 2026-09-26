#!/usr/bin/bash

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
