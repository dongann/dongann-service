package com.dongann.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;

import junit.framework.TestCase;

/**
 * @FileName: TestBase
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/04 10:39
 * @Version: v1.0
 * @description: 测试父类
 */
public abstract class TestBase extends TestCase {
    protected static Logger log = LoggerFactory.getLogger(TestBase.class);

    /**
     * 打印测试对象
     * 
     * @param obj
     */
    public void print(Object obj) {
        this.print(obj, true);
    }

    public void print(Object obj, boolean pretty) {
        GsonBuilder gb = new GsonBuilder();
        if (pretty)
            gb.setPrettyPrinting();
        gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info(gb.create().toJson(obj));
    }
}