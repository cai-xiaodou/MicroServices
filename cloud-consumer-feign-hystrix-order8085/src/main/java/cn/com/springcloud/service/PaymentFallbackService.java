package cn.com.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements OrderHystrixService{
    @Override
    public String paymentInfo_ok(Integer id) {
        return "------PaymentFallbackService Fall back-paymentInfo_ok：异常";
    }

    @Override
    public String paymentInfo_timeOut(Integer id) {
        return "------PaymentFallbackService Fall back-paymentInfo_timeOut：异常";
    }
}
