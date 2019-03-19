FROM openjdk:8-jdk-alpine

# Install maven
RUN apk update && apk add maven

WORKDIR /

# Prepare by downloading dependencies
ADD pom.xml /pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD src /src
RUN ["mvn", "package"]

EXPOSE 4567
CMD ["java", "-jar", "target/bankservice-jar-with-dependencies.jar"]