package cn.com.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class ApplicationContextConfig {

    private static final String SCRIPT_LUA = "local userid=KEYS[1];\r\n" +
            "local prodid=KEYS[2];\r\n" +
            "local qtkey='MACBOOK_COUNT';\r\n" +
            "local usersKey='SECKILL_USERID';\r\n" +
            "local userExists=redis.call(\"sismember\",usersKey,userid);\r\n"+
            "if tonumber(userExists)==1 then \r\n" +
            "   return 2;\r\n" +
            "end\r\n" +
            "local num= redis.call(\"get\",qtkey);\r\n" +
            "if tonumber(num)<=0 then \r\n" +
            "   return 0;\r\n" +
            "else \r\n" +
            "   redis.call(\"decr\",qtkey);\r\n" +
            "   redis.call(\"sadd\",usersKey,userid);\r\n" +
            "end\r\n" +
            "return 1";

    @Bean
    public ReentrantLock reentrantLock(){
        return new ReentrantLock();
    }

    @Bean
    public DefaultRedisScript<List> defaultRedisScript(){
        DefaultRedisScript<List> redisScript = new DefaultRedisScript();
        redisScript.setResultType(List.class);
        redisScript.setScriptText(SCRIPT_LUA);
        return redisScript;
    }

}
