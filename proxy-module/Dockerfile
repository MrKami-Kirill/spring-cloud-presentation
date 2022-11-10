FROM openjdk:17
ADD /proxy-module/target/proxy-module.jar proxy-module.jar
EXPOSE 8890
ENTRYPOINT ["java", "-jar", "proxy-module.jar"]