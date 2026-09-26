#!/usr/bin/bash/bash
docker compose run --rm certbot certonly \
  --webroot \
  --webroot-path /var/www/certbot \
  --domain api.example.com \
  --email you@example.com \
  --agree-tos \
  --no-eff-email