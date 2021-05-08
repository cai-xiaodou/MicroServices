package cn.com.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/sentinel")
public class RedisSentinelController {
    
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/write/{key}/{value}")
    public void redisWrite(@PathVariable("key") String key,@PathVariable("value") String value){
        redisTemplate.opsForValue().set(key,value);
    }

    @GetMapping("/read/{key}")
    public String redisRead(@PathVariable("key") String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        log.info("获取到redis值value: "+value);
        return value;
    }

    /**
     * 分布式锁,此种情况下缺乏原子性
     */
    @GetMapping("/testLock")
    public void testLock(){
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid,3, TimeUnit.SECONDS);
        if (lock) {
            String numValue = (String)redisTemplate.opsForValue().get("num");
            if (StringUtils.isEmpty(numValue)) {
                return;
            }
            Integer num = Integer.valueOf(numValue);
            redisTemplate.opsForValue().set("num",++num);
            if (((String)redisTemplate.opsForValue().get("lock")).equals(uuid)) {
                redisTemplate.delete("lock");
            }
        } else {
            try {
                Thread.sleep(100);
                testLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * lua脚本解决原子性
     */
    @GetMapping("/testLock")
    public void testLockLua(){
        String uuid = UUID.randomUUID().toString();
        String locKey = "lock";
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(locKey, uuid,3, TimeUnit.SECONDS);
        if (lock) {
            String numValue = (String)redisTemplate.opsForValue().get("num");
            if (StringUtils.isEmpty(numValue)) {
                return;
            }
            Integer num = Integer.valueOf(numValue);
            redisTemplate.opsForValue().set("num",++num);
            // 定义LUA脚本
            String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 0 end";
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setScriptText(script);
            // 设置返回类型
            redisScript.setResultType(Long.class);
            redisTemplate.execute(redisScript, Arrays.asList(locKey),uuid);
        } else {
            try {
                Thread.sleep(100);
                testLockLua();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
