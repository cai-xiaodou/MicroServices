package cn.com.springcloud.dao;

import cn.com.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PaymentDao {

    public int create(Payment payment);

    public Payment getPaymentById(Map<String,Object> map);
}
