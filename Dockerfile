FROM openjdk:17-alpine

EXPOSE 8080
WORKDIR app
COPY target/* app/
CMD ["java","-jar","app/accountRegistrationAPI-0.0.1-SNAPSHOT.jar"]
