package cn.com.springcloud.controller;

import cn.com.springcloud.entities.CommonResult;
import cn.com.springcloud.entities.Payment;
import cn.com.springcloud.myhandle.CustomerBlockHandle;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sun.deploy.security.BlockedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RateLimitController {


    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200,"按资源名称限流测试OK",new Payment(2021L,"serial001"));
    }

    public CommonResult handleException(BlockedException exception) {
        return new CommonResult(444,exception.getClass().getCanonicalName()+"\t 服务不可用");
    }

    @GetMapping("rateLimit/customerBlockHandle")
    @SentinelResource(value = "customerBlockHandle",
            blockHandlerClass = CustomerBlockHandle.class,
            blockHandler = "handleException2")
    public CommonResult customerBlockHandle() {
        return new CommonResult(200,"按客户自定义限流测试OK",new Payment(2021L,"serial003"));
    }
}

