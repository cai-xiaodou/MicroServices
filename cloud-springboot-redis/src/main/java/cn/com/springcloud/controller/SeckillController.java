package cn.com.springcloud.controller;

import cn.com.springcloud.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Resource
    private SeckillService seckillService;

    @GetMapping("/doSeckill")
    public void doSeckill(){
        String userId = new Random().nextInt(50000) + "";
        String productId = "Macbook";
        boolean b = seckillService.seckill(productId, userId);
        if (b) {
            log.info("用户"+userId+" 秒杀成功");
        }
    }
}
