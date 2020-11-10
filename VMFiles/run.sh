#!/bin/sh

# This is called a shebang -> hashtag!/bin/sh
# This is called a bashism -> hashtag!/bin/bash

cd ~/cs370TermProject1
git fetch --all
git reset --hard origin/master

docker rm team22
docker build -t team22

# Ways to run it
docker run -it --name=team22 team22
#docker container run team22
#docker run --publish 8080:8080 team22