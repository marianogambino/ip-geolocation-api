FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/ip-geolocation-api-0.0.1-SNAPSHOT.jar ip-geolocation-api-0.0.1.jar
ENTRYPOINT ["java","-jar","/ip-geolocation-api-0.0.1.jar"]