package cn.com.springcloud.controller;

import cn.com.springcloud.domain.CommonResult;
import cn.com.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    @GetMapping("/storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId,
                                 @RequestParam("count") Integer count){
        storageService.decrease(productId,count);
        return new CommonResult(200,"扣减库存成功");
    }
}
