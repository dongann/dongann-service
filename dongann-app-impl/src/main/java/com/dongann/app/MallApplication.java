package com.dongann.app;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableDubboConfiguration
@EnableAspectJAutoProxy(exposeProxy=true)
public class MallApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MallApplication.class, args);
	}
}
