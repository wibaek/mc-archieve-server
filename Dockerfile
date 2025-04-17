FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/mc-archieve-server-0.0.1.jar

ADD ${JAR_FILE} mc-archieve-server.jar

ENTRYPOINT ["java", "-jar", "mc-archieve-server.jar"]
