FROM maven:3.5-jdk-11-slim as build-stage

WORKDIR /src
COPY . .
RUN mvn clean package

FROM openjdk:11-jre-slim as package-stage

ARG JAR_FILE=target/*.jar
COPY --from=build-stage /src/${JAR_FILE} ./app.jar

ENTRYPOINT ["java","-jar","app.jar"]
