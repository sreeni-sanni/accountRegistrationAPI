# accountRegistrationAPI

 Customer can register and open a new account in online.
 
 This API developed using java 17,spring boot 3 and maven.
 
please follow below steps to run API in local machine:
 
 Build project using maven:
 
 mvn clean package
 
 Run application using docker compose. it will use docker file to create docker image for API:

 docker-compose up --build
 
 Health check using Actuator URL:
 
 http://localhost:8080/actuator/health
 
  



