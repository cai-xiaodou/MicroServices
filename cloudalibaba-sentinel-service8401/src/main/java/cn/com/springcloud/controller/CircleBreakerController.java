package cn.com.springcloud.controller;

import cn.com.springcloud.entities.CommonResult;
import cn.com.springcloud.entities.Payment;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@RestController
public class CircleBreakerController {

    private static final String SERVER_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback 只负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler") //blockHandler 只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler")
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult result = restTemplate.getForObject(SERVER_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if(id == 4L) {
            throw new IllegalArgumentException("IllegalArgumentException--- 非法参数异常");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException---- 该ID没有对应记录，空指针异常");
        }
        return result;
    }

    public CommonResult handlerFallback(@PathVariable Long id,Throwable e){
        Payment payment = new Payment(id, "null");
        return new CommonResult(444,"兜底异常handlerFallback： "+e.getMessage(),payment);
    }

    public CommonResult blockHandler(@PathVariable Long id, BlockException e){
        Payment payment = new Payment(id, "null");
        return new CommonResult(445,"blockHandler-sentinel限流，无此流水，BlockException： "+e.getMessage(),payment);
    }
}
