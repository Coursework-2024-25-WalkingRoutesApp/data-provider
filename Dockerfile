FROM amazoncorretto:23-alpine-jdk

WORKDIR /data-provider
ARG VERSION
ADD build/libs/data-provider-${VERSION}.jar data-provider.jar

CMD ["java", "-jar", "data-provider.jar"]
