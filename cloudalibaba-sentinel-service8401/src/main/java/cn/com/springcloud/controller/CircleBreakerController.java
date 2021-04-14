package cn.com.springcloud.controller;

import cn.com.springcloud.entities.CommonResult;
import cn.com.springcloud.entities.Payment;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
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
    @SentinelResource(value = "fallback")
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult result = restTemplate.getForObject(SERVER_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if(id == 4L) {
            throw new IllegalArgumentException("IllegalArgumentException--- 非法参数异常");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException---- 该ID没有对应记录，空指针异常");
        }
        return result;
    }

}
