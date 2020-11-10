#!/bin/sh

# This is called a shebang -> hashtag!/bin/sh
# This is called a bashism -> hashtag!/bin/bash
# cd into correct directory
cd ~/cs370TermProject1

#Filter the docker process list and if found then stop the container and remove it
docker ps -q --filter "name=team22" | grep -q . && docker stop team22 && docker rm -fv team22

#We now need to build the container and tag the build with team 22
docker build -t team22

#Run the container with the name team22 and forward incoming traffic from 8080 to the
# containers port 8080 using the build file team 22
# -it adds an interactive terminal 
docker run -it --name=team22 -p 8080:8080 team22