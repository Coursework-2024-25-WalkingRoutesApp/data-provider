FROM amazoncorretto:23-alpine-jdk

WORKDIR /data-provider-service
ADD build/libs/data-provider-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
