#!/bin/sh

# This is called a shebang -> hashtag!/bin/sh
# This is called a bashism -> hashtag!/bin/bash
# cd into correct directory
cd ~/cs370TermProject1/

#Filter the docker process list and if found then stop the container and remove it
docker network rm YourMom
docker network create --driver bridge YourMom

# This is called a shebang -> hashtag!/bin/sh
# This is called a bashism -> hashtag!/bin/bash
# cd into correct directory
cd ~/cs370TermProject1/BackendServer/

#Filter the docker process list and if found then stop the container and remove it
docker rm -fv backendserver

#We now need to build the container and tag the build with team 22
docker build -t backendserver .

#Run the container with the name team22 and forward incoming traffic from 8080 to the
# containers port 8080 using the build file team 22
# -it adds an interactive terminal 
# -d makes it detached
docker run -d --name=backendserver --network YourMom backendserver

# This is called a shebang -> hashtag!/bin/sh
# This is called a bashism -> hashtag!/bin/bash
# cd into correct directory
cd ~/cs370TermProject1/ClientServer/

#Filter the docker process list and if found then stop the container and remove it
docker rm -fv clientserver

#We now need to build the container and tag the build with team 22
docker build -t clientserver .

#Run the container with the name team22 and forward incoming traffic from 8080 to the
# containers port 8080 using the build file team 22
# -it adds an interactive terminal 
# -d makes it detached
docker run -it --name=clientserver --network YourMom clientserver