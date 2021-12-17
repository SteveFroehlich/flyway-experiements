#!/bin/sh

docker run -d --name=postgres13 -p 5432:5432 -e POSTGRES_PASSWORD=sa postgres

# if The container name "/postgres13" is already in use by container "whatever..
# docker container start postgres13
