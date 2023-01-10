#docker-compose
#FROM openjdk:17
#ADD /configuration-server/target/configuration-server.jar configuration-server.jar
#EXPOSE 8079
#ENTRYPOINT ["java", "-jar", "configuration-server.jar"]

#minikube
FROM openjdk:17
VOLUME /tmp
COPY target/configuration-server.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar --debug