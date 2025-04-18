FROM amazoncorretto:23-alpine-jdk

WORKDIR /data-provider
ADD build/libs/data-provider-0.0.1-SNAPSHOT.jar data-provider.jar

CMD ["java", "-jar", "data-provider.jar"]
