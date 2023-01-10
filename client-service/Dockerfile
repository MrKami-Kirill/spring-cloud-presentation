#docker-compose
#FROM openjdk:17
#ADD /client-service/target/client-service.jar client-service.jar
#EXPOSE 8082
#ENTRYPOINT ["java", "-jar", "client-service.jar"]

#minikube
FROM openjdk:17
VOLUME /tmp
COPY target/client-service.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar --debug