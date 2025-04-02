FROM amazoncorretto:23-alpine3.17-jdk
ARG JAR_FILE=*-SNAPSHOT.jar
COPY ./build/libs/${JAR_FILE} ./app.jar
RUN chmod 775 ./app.jar
ENTRYPOINT ["java","-jar","./app.jar"]
