package com.dongann.app.kafka.kafkaConsumer;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dongann.app.kafka.kafkaProducer.KafkaProducer;

/**
 * @FileName: KafkaConsumer
 * @Author: <a href="dongchangan@rrslj.com">dongchang'an</a>.
 * @CreateTime: 2019-04-23 10:45
 * @Version: v1.0
 * @description:
 */
@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = KafkaProducer.TOPIC_ONE, groupId = KafkaProducer.TOPIC_ONE)
    public void topic_one(ConsumerRecord<?, ?> record) {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            logger.info("被" + KafkaProducer.TOPIC_ONE + "消费了： +++++++++++++++ Topic:" + record.topic() + ",Record:" + record + ",Message:" + msg);
        }
    }
}
