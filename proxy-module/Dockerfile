#docker-compose
#FROM openjdk:17
#ADD /proxy-module/target/proxy-module.jar proxy-module.jar
#EXPOSE 8081
#ENTRYPOINT ["java", "-jar", "proxy-module.jar"]

#minikube
FROM openjdk:17
VOLUME /tmp
COPY target/proxy-module.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar --debug