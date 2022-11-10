FROM openjdk:17
EXPOSE 9411
ADD /zipkin/zipkin-server.jar zipkin-server.jar
ENTRYPOINT ["java", "-jar", "zipkin-server.jar"]