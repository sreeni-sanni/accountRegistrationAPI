FROM openjdk:17-alpine

EXPOSE 8080
COPY accountRegistrationAPI-0.0.1-SNAPSHOT.jar accountRegistrationAPI-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","accountRegistrationAPI-0.0.1-SNAPSHOT.jar"]
