FROM openjdk:17
ADD /client-service/target/client-service.jar client-service.jar
EXPOSE 8891
ENTRYPOINT ["java", "-jar", "client-service.jar"]