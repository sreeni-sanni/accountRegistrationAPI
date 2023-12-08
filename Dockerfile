FROM openjdk:17-alpine

EXPOSE 8080
WORKDIR app
COPY accountRegistrationAPI-0.0.1.jar app/
CMD ["java","-jar","app/accountRegistrationAPI-0.0.1.jar"]
