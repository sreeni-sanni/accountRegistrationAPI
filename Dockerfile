FROM openjdk:17-alpine

EXPOSE 8080
COPY target/accountRegistrationAPI-0.0.1-SNAPSHOT.jar api/accountRegistrationAPI-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","api/accountRegistrationAPI-0.0.1-SNAPSHOT.jar"]
