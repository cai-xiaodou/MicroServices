package cn.com.springcloud.controller;

import cn.com.springcloud.service.OrderHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private OrderHystrixService orderHystrixService;


    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id) {
        String result = orderHystrixService.paymentInfo_ok(id);
        return result;
    }

    @GetMapping("/payment/hystrix/timeOut/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutFallbackMethod",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeOutInMilliseconds",value = "1500")
//    })
    @HystrixCommand
    public String paymentInfo_timeOut(@PathVariable("id") Integer id) {
        String result = orderHystrixService.paymentInfo_timeOut(id);
        return result;
    }

    public String paymentInfoTimeOutFallbackMethod() {
        return "我是消费者8085，对方支付系统繁忙，请稍后再试。";
    }

    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试。";
    }
}
