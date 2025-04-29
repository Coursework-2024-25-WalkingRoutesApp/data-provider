FROM amazoncorretto:23-alpine-jdk

WORKDIR /data-provider
ADD build/libs/data-provider-1.0.0.jar data-provider.jar

CMD ["java", "-jar", "data-provider.jar"]
