# First we need to create a docker layer working directory that will clone and store the repo
FROM alpine/git as clone
RUN mkdir -p /clone
WORKDIR /clone
RUN git clone https://github.com/tommybean/cs370TermProject1.git

# Second we want to get a maven base
FROM maven:3.6.3-jdk-11 AS build
RUN mkdir -p /code/Restful
RUN mkdir -p /code/Backend
WORKDIR /code
# Copy the Restful API Project Contents into the Restful folder
COPY --from=clone /clone/cs370TermProject1/src /code/Restful/src
COPY --from=clone /clone/cs370TermProject1/pom.xml /code/Restful/pom.xml
# Copy the Backend API Project Conects into the Backend folder
#COPY --from=clone /clone/cs370TermProject1/src /code/Backend/src
#COPY --from=clone /clone/cs370TermProject1/pom.xml /code/Backend/pom.xml
RUN mvn dependency:resolve
RUN mvn verify
RUN mvn clean
RUN mvn package

# Now we want to run the RESTful API in an openjdk
# If we are changing the project's name we can add an argument to the docker file to specify that:
# ARG argname
# COPY ${argname}
FROM openjdk:11-jre
RUN mkdir -p /Restful
WORKDIR /Restful
COPY --from=build /code/Restful/target/cs370TermProject1-1.0-SNAPSHOT-jar-with-dependencies.jar /Restful/RestfulServer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/Restful/RestfulServer.jar"]

# Now we create the Backend openjdk
#FROM openjdk:11-jre
#RUN mkdir -p /Backend
#WORKDIR /Backend
#COPY --from=build /code/Backend/target/cs370TermProject1-1.0-SNAPSHOT-jar-with-dependencies.jar /Backend/RestfulServer.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/Backend/RestfulServer.jar"]