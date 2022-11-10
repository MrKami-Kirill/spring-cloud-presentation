FROM openjdk:17
ADD /configuration-server/target/configuration-server.jar configuration-server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "configuration-server.jar"]