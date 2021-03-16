package cn.com.springcloud.controller;

import cn.com.springcloud.entities.CommonResult;
import cn.com.springcloud.entities.Payment;
import cn.com.springcloud.service.PaymentService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult createPayment(HttpServletRequest request, @RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("数据插入结果："+result);
        if (result > 0) {
            return new CommonResult(200,"success",result);
        } else {
            return new CommonResult(444,"failed",result);
        }
    }

    @PostMapping(value = "/payment/getPaymentById")
    public CommonResult getPaymentById(HttpServletRequest request, @RequestBody JSONObject jsonParams){
        Payment payment = paymentService.getPaymentById(jsonParams.getLong("id"));
        if (payment != null) {
            return new CommonResult(200,"查找数据成功",payment);
        } else {
            return new CommonResult(445,"查找失败",null);
        }
    }
}
