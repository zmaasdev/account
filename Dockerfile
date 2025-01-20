FROM amazoncorretto:23-alpine3.17-jdk
ARG JAR_FILE=build/libs/account-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
