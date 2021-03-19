package cn.com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrderApplication8081 {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication8081.class,args);
    }
}
