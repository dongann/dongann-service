package com.dongann.app.datasource;

import java.lang.annotation.*;

/**
 * @FileName: DataSource
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/7/11 下午5:40
 * @Version: v1.0
 * @description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface DataSource {
    DataSourceType value() default DataSourceType.MASTER;
}
