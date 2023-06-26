FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8080
ADD target/accountRegistrationAPI-0.0.1-SNAPSHOT.jar accountRegistrationAPI-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","accountRegistrationAPI-0.0.1-SNAPSHOT.jar"]