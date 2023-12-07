FROM openjdk:17-alpine

EXPOSE 8080
WORKDIR app
COPY --from=build /home/vsts/work/1/a/sreeni-sanni/accountRegistrationAPI/accountRegistrationAPI-0.0.1-SNAPSHOT.jar app/accountRegistrationAPI-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","api/accountRegistrationAPI-0.0.1-SNAPSHOT.jar"]
