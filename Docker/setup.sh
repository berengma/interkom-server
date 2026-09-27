#!/usr/bin/env bash
set -euo pipefail
if [[ "$EUID" -ne 0 ]]; then
    echo "This script must be run as root." >&2
    exit 1
fi

install -d -m 755 /etc/interkom-server/letsencrypt
install -d -m 755 /etc/interkom-server/certbot-www

docker compose build --no-cache
docker compose up --build -d
