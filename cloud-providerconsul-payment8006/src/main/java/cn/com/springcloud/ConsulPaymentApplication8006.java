package cn.com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulPaymentApplication8006 {
    public static void main(String[] args) {
        SpringApplication.run(ConsulPaymentApplication8006.class,args);
    }
}
