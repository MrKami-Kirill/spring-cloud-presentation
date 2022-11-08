package spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProxyModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyModuleApplication.class, args);
    }

}