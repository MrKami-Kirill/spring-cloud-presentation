FROM openjdk:17
ADD /hystrix-dashboard/target/hystrix-dashboard.jar hystrix-dashboard.jar
EXPOSE 8076
ENTRYPOINT ["java", "-jar", "hystrix-dashboard.jar"]