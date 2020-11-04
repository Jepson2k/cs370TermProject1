FROM maven:3.6.3-jdk-11 as maven
COPY ../pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ../src ./src
RUN mvn package
FROM openjdk:11-jre-slim
WORKDIR /team22
COPY --from=maven target/cs370TermProject1-1.0-SNAPSHOT-jar-with-dependencies.jar ./
CMD ["java -cp target/cs370TermProject1-1.0-SNAPSHOT-jar-with-dependencies.jar RestfulServer"]
