package com.dongann.app.datasource;

/**
 * @FileName: DataSourceType
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/7/11 下午5:36
 * @Version: v1.0
 * @description: 数据源类型
 */
public enum DataSourceType {
    MASTER("master"),
    SLAVE("slave");

    private String type;

    DataSourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
