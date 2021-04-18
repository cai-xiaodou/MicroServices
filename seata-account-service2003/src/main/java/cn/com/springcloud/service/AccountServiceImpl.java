package cn.com.springcloud.service;

import cn.com.springcloud.dao.AccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{

    @Resource
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("seata-account-service 账户开始扣钱");
        accountDao.decrease(userId,money);
    }
}
