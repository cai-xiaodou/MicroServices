package cn.com.springcloud.service.impl;

import cn.com.springcloud.dao.OrderDao;
import cn.com.springcloud.domain.Order;
import cn.com.springcloud.service.AccountService;
import cn.com.springcloud.service.OrderService;
import cn.com.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    @Override
    @GlobalTransactional
    public void create(Order order) {
        log.info("开始创建订单-------");
        orderDao.create(order);

        log.info("----> 订单微服务调用库存，做扣减count");
        storageService.decrease(order.getProductId(),order.getCount());

        log.info("----> 订单微服务调用账户，做扣减money");
        accountService.decrease(order.getUserId(),order.getMoney());

        log.info("-----> 修改订单状态");
        orderDao.update(order.getUserId(),0);
    }
}
