package cn.com.springcloud.service;

import cn.com.springcloud.dao.StorageDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService{

    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("seata-storage-service 减去库存");
        storageDao.decrease(productId,count);
    }
}
