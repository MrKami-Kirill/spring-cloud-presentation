#docker-compose
#FROM openjdk:17
#ADD /hystrix-dashboard/target/hystrix-dashboard.jar hystrix-dashboard.jar
#EXPOSE 8076
#ENTRYPOINT ["java", "-jar", "hystrix-dashboard.jar"]

#minikube
FROM openjdk:17
VOLUME /tmp
COPY target/hystrix-dashboard.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar --debug