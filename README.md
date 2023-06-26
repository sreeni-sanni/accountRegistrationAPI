# accountRegistrationAPI

Customer can register and open a new account online. This API developed using java 17,spring boot 3 and maven.

Please follow below steps to run API in local machine.
- Build project using maven:
   mvn clean package
- Run application using docker compose. It will use docker file to create docker image for API:
   docker-compose up --build
- Health check using Actuator URL:
   http://localhost:8080/actuator/health
- Postman collection is added in the repo. It has all the required scenarios to be tested.
 
  



