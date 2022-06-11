FROM openjdk:11

ARG JAR_FILE=build/libs/yasn-0.0.1-SNAPSHOT.jar

ARG DATA=src/main/resources/songs_normalize.csv

COPY ${JAR_FILE} app.jar
COPY ${DATA} src/main/resources/songs_normalize.csv

ENTRYPOINT ["java","-jar","/app.jar"]
