#!/bin/bash
echo "All setup for Interkom ! ${INTERKOM_URL}:${WEB_PORT}"
export  INTERKOM_URL
export  WEB_PORT
exec java -jar /home/interkom/interkom-server/target/interkom-server-0.0.2-MVP.jar
