FROM maven:3.6.3-jdk-11
COPY ./ ./
RUN mvn package
CMD ["java -cp target/cs370TermProject1-1.0-SNAPSHOT-jar-with-dependencies.jar RestfulServer"]
