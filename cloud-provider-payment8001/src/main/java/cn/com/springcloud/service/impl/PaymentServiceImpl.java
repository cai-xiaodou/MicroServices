package cn.com.springcloud.service.impl;

import cn.com.springcloud.dao.PaymentDao;
import cn.com.springcloud.entities.Payment;
import cn.com.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return paymentDao.getPaymentById(map);
    }
}
