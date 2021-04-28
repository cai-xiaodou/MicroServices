package cn.com.springcloud.controller;

import cn.com.springcloud.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@RestController
@RequestMapping("/seckill")
public class SeckillController {


    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static int i = 0;

    private static Lock lock = new ReentrantLock();

    @Resource
    private SeckillService seckillService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private DefaultRedisScript<List> defaultRedisScript;

    @GetMapping("/doSeckill")
    public void doSeckill(){
        String userId = new Random().nextInt(500000000) + "";
        String productId = "MACBOOK";
//        boolean b = seckillService.seckill(productId, userId);
//        if (b) {
//            log.info("用户"+userId+" 秒杀成功");
//        }
        doKill(productId,userId);
    }

    @GetMapping("/test")
    public void test() {
        atomicInteger.getAndIncrement();
        String count = (String)redisTemplate.opsForValue().get("MACBOOK_COUNT");
        log.info(Thread.currentThread().getName()+" count: "+count+"  i: " + atomicInteger.get());
//        try {
//            lock.lock();
//            String count = (String)redisTemplate.opsForValue().get("MACBOOK_COUNT");
//            i++;
//            log.info(Thread.currentThread().getName()+" count: "+count+"  i: " + i);
//        } finally {
//            lock.unlock();
//        }
//        synchronized (this) {
//            String count = (String)redisTemplate.opsForValue().get("MACBOOK_COUNT");
//            i++;
//            log.info(Thread.currentThread().getName()+" count: "+count+"  i: " + i);
//        }
    }

    /**
     * 执行lua脚本，解决库存遗留问题
     * @param productId
     * @param userId
     */
    private void doKill(String productId,String userId) {
        List<String> keyList = new ArrayList<>();
        keyList.add(userId);
        keyList.add(productId);
        List<Long> result = redisTemplate.execute(defaultRedisScript, keyList);
        if (result !=null && !result.isEmpty()) {
            Long code = result.get(0);
            if (code == 0L) {
                log.info("商品已抢空！！！");
            } else if (code == 1L) {
                log.info("用户"+userId+"抢购成功！！！");
            } else if (code == 2L) {
                log.info("用户"+userId+"已抢购！！！");
            } else {
                log.info("商品抢购异常！！！");
            }
        } else {
            log.info("执行脚本返回数据为空，result: "+result);
        }
    }
}
