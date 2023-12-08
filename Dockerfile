FROM openjdk:17-alpine

EXPOSE 8080
WORKDIR app
COPY target/accountRegistrationAPI-0.0.1-SNAPSHOT.jar app/accountRegistrationAPI-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","app/accountRegistrationAPI-0.0.1-SNAPSHOT.jar"]
