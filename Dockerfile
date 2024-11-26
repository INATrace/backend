FROM maven:3.8.5-openjdk-17-slim as build-stage

WORKDIR /src
COPY . .
RUN mvn clean package

FROM eclipse-temurin:17-jre as package-stage

ARG JAR_FILE=target/*.jar
COPY --from=build-stage /src/${JAR_FILE} ./app.jar

ENTRYPOINT ["java","-jar","app.jar"]
