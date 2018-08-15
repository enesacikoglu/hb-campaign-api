FROM openjdk:8-jre-alpine
MAINTAINER enes.acikoglu@gmail.com
ADD target/campaign-api-*.jar campaign-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/campaign-api.jar"
