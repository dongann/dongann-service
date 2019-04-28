package com.dongann.app.kafka;

import com.dongann.app.TestBase;
import com.dongann.app.kafka.kafkaConsumer.KafkaConsumer;
import com.dongann.app.kafka.kafkaProducer.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @FileName: KafkaTest
 * @Author: <a href="dongchangan@rrslj.com">dongchang'an</a>.
 * @CreateTime: 2019-04-23 11:15
 * @Version: v1.0
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class KafkaTest extends TestBase {
    @Resource
    private KafkaProducer kafkaProducer;
    @Resource
    private KafkaConsumer kafkaConsumer;

    @Test
    public void testSend(){

        kafkaProducer.send("{\n" +
                "  \"supplierId\": 11,\n" +
                "  \"rejectReason\": \"\",\n" +
                "  \"method\": \"AuditMatOrder\",\n" +
                "  \"orderId\": 45,\n" +
                "  \"supplierUserId\": 1,\n" +
                "  \"appFrom\": 5,\n" +
                "  \"nativeVersion\": \"weixin-android-\",\n" +
                "  \"version\": \"v1.0.0\",\n" +
                "  \"status\": 1\n" +
                "}");
    }

}
