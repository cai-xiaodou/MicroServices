package cn.com.springcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean seckill(String productId, String userId) {
        if (productId == null || userId == null) return false;
        String kcKey = productId + "_COUNT";
        String userKey = "SECKILL_USERID";
        redisTemplate.watch(kcKey);
        String kc = (String) redisTemplate.opsForValue().get(kcKey);
        if (kc == null) {
            log.info("秒杀活动还未开始，请等待");
            return false;
        }
        Boolean isExitUserId = redisTemplate.opsForSet().isMember(userKey, userId);
        if (isExitUserId) {
            log.info("用户"+userId+"已成功秒杀，请勿重复操作");
            return false;
        }
        if (Integer.valueOf(kc) <= 0) {
            log.info("秒杀活动已经结束");
            return false;
        }
        // 秒杀成功
        // 事务
        redisTemplate.multi();
        redisTemplate.opsForValue().decrement(kcKey);
        redisTemplate.opsForSet().add(userKey,userKey);
        List<Object> exec = redisTemplate.exec();
        if (exec == null || exec.isEmpty()) {
            return false;
        }
        return true;
    }
}
