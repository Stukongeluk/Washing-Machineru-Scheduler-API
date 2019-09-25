#!/bin/bash
docker run -t -p 5432:5432 -e "POSTGRES_USER=dbuser" -e "POSTGRES_PASSWORD=secretPassword" -e "POSTGRES_DB=scheduler" postgres:11.5-alpine