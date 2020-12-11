# First we need to create a docker layer working directory that will clone and store the repo
FROM alpine/git:latest AS clone
RUN mkdir -p /clone
WORKDIR /clone
RUN git clone https://github.com/tommybean/cs370TermProject1.git
# This is I guess a strategy to get it to skip using cache for the next command
WORKDIR /clone/cs370TermProject1
ADD "https://www.random.org/cgi-bin/randbyte?nbytes=10&format=h" skipcache
RUN git fetch --all
ADD "https://www.random.org/cgi-bin/randbyte?nbytes=10&format=h" skipcache
RUN git reset --hard origin/master

# Second we want to get a maven base
FROM maven:3.6.3-jdk-11 AS build
RUN mkdir -p /BackendServer
WORKDIR /BackendServer
# Copy the Backend API Project Conects into the Backend folder
COPY --from=clone /clone/cs370TermProject1/BackendServer/src /BackendServer/src
COPY --from=clone /clone/cs370TermProject1/BackendServer/pom.xml /BackendServer/pom.xml
WORKDIR /BackendServer
RUN mvn dependency:resolve
RUN mvn verify
RUN mvn clean
RUN mvn package

# Now we create the Backend openjdk
FROM openjdk:11-jre
RUN mkdir -p /BackendServer
WORKDIR /BackendServer
COPY --from=build /code/Backend/target/BackendServer-1.0-SNAPSHOT-jar-with-dependencies.jar /BackendServer/Server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/BackendServer/Server.jar"]