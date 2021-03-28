package cn.com.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_ok(Integer id) {
        return Thread.currentThread().getName()+"：paymentInfo_ok,id："+id+"\t"+"O(∩_∩)O哈哈~";
    }

    /**
     * 超过3秒进行服务降级
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_timeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeOutInMilliseconds",value = "3000")})
    public String paymentInfo_timeOut(Integer id){
        Integer timeOut = 5;
        try {
            TimeUnit.SECONDS.sleep(timeOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName()+"：paymentInfo_failed,id："+id+"\t"+"55555";
    }

    public String paymentInfo_timeOutHandler(Integer id){
        return Thread.currentThread().getName()+"：paymentInfo_timeOutHandler，ID："+id;
    }

    //========服务熔断

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id 不能为负数");
        }
        String simpleUUID = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号："+simpleUUID;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){

        return "id 不能为负数，请稍后再试，ID："+id;
    }

}
