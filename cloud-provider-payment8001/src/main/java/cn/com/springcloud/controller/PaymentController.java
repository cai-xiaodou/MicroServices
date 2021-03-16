package cn.com.springcloud.controller;

import cn.com.springcloud.entities.CommonResult;
import cn.com.springcloud.entities.Payment;
import cn.com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult createPayment(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("数据插入结果："+result);
        if (result > 0) {
            return new CommonResult(200,"success",result);
        } else {
            return new CommonResult(444,"failed",result);
        }

    }
}
