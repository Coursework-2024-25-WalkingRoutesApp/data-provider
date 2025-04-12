FROM amazoncorretto:23-alpine-jdk

WORKDIR /routes-provider
ADD build/libs/routes-provider-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
