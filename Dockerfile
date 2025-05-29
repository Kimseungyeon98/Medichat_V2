FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/medichat-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]