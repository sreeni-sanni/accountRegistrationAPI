# AccountRegistrationAPI

When customer trying to register with his details using /register endpoint and once the registration process get success in the background IBAN will be created and assign to customer. Customer can also  logon using /logon endpoint. Later he can also view his account information using /overview endpoint. 
This API developed using latest frameworks like Java 17, Spring Boot 3.1.0 and Maven.

## Please follow below steps to run API in local machine.
- Build project using maven:
  - mvn clean package
- Run application using docker compose. It will use docker file to create docker image for API:
  - docker-compose up --build
- Health check using Actuator URL:
  - http://localhost:8080/actuator/health
- Postman collection is added in the repo. It has all the required scenarios to be tested.
 
  



