# CS370 Term-Project Team 22

# Description

Welcome to the respository for Team 22's term project. In this respository there is a web server created with Java Spark using RESTful API which accepts HTTP GET and POST requests. The beody of the requests are logged to standard output when the server is running. See usage for instructions on building/compiling and running the server. There is also a Dockerfile which builds a Docker image that starts the web server when run. See usage and Docker sections for more information on using Docker in our project. The current files in for our project are a base and platform to expand our ideas such that we can use containers to improve server side handling of data. Specifically, the API will be returning to a container that has a running server so sensitive data sent to the server is protected and generation of sensitive data can be influenced only by processes running server side.

# Usage

To build our project from the command line: <br/>
<br/>
Within the project directory: <br/>
`$ mvn package` <br/>
`$ java -cp target/cs370TermProject1-1.0-SNAPSHOT-jar-with-dependencies.jar RestfulServer` <br/>
<br/>
Using Docker:


# Docker
