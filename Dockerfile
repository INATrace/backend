FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar

RUN mkdir /app
WORKDIR /app

COPY ${JAR_FILE} ./app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
