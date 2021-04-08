package cn.com.springcloud.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@EnableBinding(Source.class)
public class MessageProviderImpl implements MessageProvider {

    @Resource
    private MessageChannel messageChannel;

    @Override
    public String send() {
        JSONObject json = new JSONObject();
        UUID uuid = UUID.randomUUID();
        boolean b = messageChannel.send(MessageBuilder.withPayload(uuid).build());
        log.info("uuid: "+uuid);
        if (b) {
            json.put("code",1);
            json.put("result","消息发送成功");
        } else {
            json.put("code",-1);
            json.put("result","消息发送失败");
        }
        return json.toJSONString();
    }
}
