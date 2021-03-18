package cn.com.springcloud.controller;

import cn.com.springcloud.entities.CommonResult;
import cn.com.springcloud.entities.Payment;
import cn.com.springcloud.service.PaymentService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult createPayment(HttpServletRequest request, @RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("数据插入结果："+result);
        if (result > 0) {
            return new CommonResult(200,"插入数据成功，serverPort："+serverPort,result);
        } else {
            return new CommonResult(444,"插入数据失败，serverPort："+serverPort,result);
        }
    }

    @GetMapping(value = "/payment/getPaymentById/{id}")
    public CommonResult<Payment> getPaymentById(HttpServletRequest request,@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult(200,"查找数据成功，serverPort："+serverPort,payment);
        } else {
            return new CommonResult(445,"查找失败，id="+id,null);
        }
    }
    @GetMapping("/payment/discovery")
    public JSONObject getDiscoveryClient(){
        JSONObject json = new JSONObject();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            JSONObject serviceJson = new JSONObject();
            List<ServiceInstance> instances = discoveryClient.getInstances(service.toUpperCase());
            for (ServiceInstance instance : instances) {
                JSONObject instanceJson = new JSONObject();
                String serviceId = instance.getServiceId();
                String host = instance.getHost();
                int port = instance.getPort();
                URI uri = instance.getUri();
                instanceJson.put("serviceId",serviceId);
                instanceJson.put("host",host);
                instanceJson.put("port",port);
                instanceJson.put("uri",uri);
                serviceJson.put(instance.getInstanceId(),instanceJson);
            }
            json.put(service,serviceJson);
        }
        return json;
    }
}
