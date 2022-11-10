FROM openjdk:17
ADD /eureka-discovery-server/target/eureka-discovery-server.jar eureka-discovery-server.jar
EXPOSE 8889
ENTRYPOINT ["java", "-jar", "eureka-discovery-server.jar"]