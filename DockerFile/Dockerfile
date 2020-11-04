FROM java:8

#Update the Java Runtime Environment and install Maven 
RUN apt-get update
RUN apt-get install -y maven

#The Dockerfile is in the DockerFile folder so we need to go back one I think
WORKDIR ../

#Prepare by downloading dependencies
ADD pom.xml ./pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

#Adding source, compile and package into a f-fa-fat jar
ADD src ./src
RUN ["mvn", "package"]

EXPOSE 8080
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/team22-jar-with-dependencies.jar"]
