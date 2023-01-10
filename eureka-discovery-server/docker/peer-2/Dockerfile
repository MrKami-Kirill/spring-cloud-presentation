#docker-compose
FROM openjdk:17
ADD /eureka-discovery-server/target/eureka-discovery-server.jar eureka-discovery-server.jar
EXPOSE 8762
ENTRYPOINT ["java", "-jar", "eureka-discovery-server.jar"]