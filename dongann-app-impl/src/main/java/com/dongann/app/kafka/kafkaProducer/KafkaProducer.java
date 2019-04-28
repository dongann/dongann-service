package com.dongann.app.kafka.kafkaProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import com.alibaba.fastjson.*;

import javax.annotation.Resource;

/**
 * @FileName: KafkaProducer
 * @Author: <a href="dongchangan@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2019-04-23 10:46
 * @Version: v1.0
 * @description:
 */
@Component
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Resource
    private KafkaTemplate kafkaTemplate;

    //自定义topic
    public static final String TOPIC_ONE="topic.one";

    public void send(Object obj){
        logger.info("准备发送消息为：{}", JSON.toJSONString(obj));
        //发送消息
        ListenableFuture<SendResult<String,Object>> future=kafkaTemplate.send(TOPIC_ONE,obj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                logger.info(TOPIC_ONE+" - 生产者 发送消息失败："+throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                //成功的处理
                logger.info(TOPIC_ONE+" - 生产者 发送消息成功："+stringObjectSendResult.toString());
            }
        });


    }
}
