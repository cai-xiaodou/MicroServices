package cn.com.springcloud.controller;

import cn.com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_ok(id);
        return result;
    }

    @GetMapping("/payment/hystrix/timeOut/{id}")
    public String paymentInfo_timeOut(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_timeOut(id);
        return result;
    }

    // =====服务熔断

    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("result："+result);
        return result;
    }
}
