package cn.com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeignOrder8084 {
    public static void main(String[] args) {
        SpringApplication.run(FeignOrder8084.class,args);
    }
}
