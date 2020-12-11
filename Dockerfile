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
RUN mkdir -p /code/ClientServer
RUN mkdir -p /code/Backend
WORKDIR /code
# Copy the Restful API Project Contents into the Restful folder
COPY --from=clone /clone/cs370TermProject1/ClientServer/src /code/ClientServer/src
COPY --from=clone /clone/cs370TermProject1/ClientServer/pom.xml /code/ClientServer/pom.xml
WORKDIR /code/ClientServer
RUN mvn dependency:resolve
RUN mvn verify
RUN mvn clean
RUN mvn package
# Copy the Backend API Project Conects into the Backend folder
COPY --from=clone /clone/cs370TermProject1/BackendServer/src /code/BackendServer/src
COPY --from=clone /clone/cs370TermProject1/BackendServer/pom.xml /code/BackendServer/pom.xml
WORKDIR /code/Backend
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

# Now we want to run the RESTful API in an openjdk
# If we are changing the project's name we can add an argument to the docker file to specify that:
# ARG argname
# COPY ${argname}
FROM openjdk:11-jre
RUN mkdir -p /ClientServer
WORKDIR /ClientServer
COPY --from=build /code/ClientServer/target/ClientServer-1.0-SNAPSHOT-jar-with-dependencies.jar /ClientServer/Server.jar
EXPOSE 8080:8081
ENTRYPOINT ["java", "-jar", "/ClientServer/Server.jar"]