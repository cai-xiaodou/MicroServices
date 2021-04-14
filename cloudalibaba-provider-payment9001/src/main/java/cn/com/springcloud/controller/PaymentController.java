package cn.com.springcloud.controller;

import cn.com.springcloud.entities.CommonResult;
import cn.com.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    private static HashMap<Long, Payment> hashMap = new HashMap<>();

    static {
        hashMap.put(1L,new Payment(1L,"serial001"));
        hashMap.put(2L,new Payment(2L,"serial002"));
        hashMap.put(3L,new Payment(3L,"serial003"));
    }

    @GetMapping("/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return "nacos register,serverPort: "+serverPort+"\t id: "+id;
    }

    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        Payment payment = hashMap.get(id);
        return new CommonResult(200,"from mysql,serverPort: "+serverPort,payment);
    }

}
