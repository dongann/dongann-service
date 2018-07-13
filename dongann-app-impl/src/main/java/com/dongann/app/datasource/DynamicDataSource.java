package com.dongann.app.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @FileName: DynamicDataSource
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/7/11 下午5:29
 * @Version: v1.0
 * @description: 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 数据源路由，此方用于产生要选取的数据源逻辑名称
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        if (DataSourceHolder.getDataSource() != null) {
            return DataSourceHolder.getDataSource();
        }
        return DataSourceType.MASTER.getType();
    }
}
