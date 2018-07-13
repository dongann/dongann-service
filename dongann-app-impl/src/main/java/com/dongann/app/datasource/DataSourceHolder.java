package com.dongann.app.datasource;

/**
 * @FileName: DataSourceHolder
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/7/11 下午5:30
 * @Version: v1.0
 * @description:
 */
public class DataSourceHolder {
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void putDataSource(DataSourceType dataSourceType) {
        holder.set(dataSourceType.getType());
    }

    public static String getDataSource(){
        return holder.get();
    }

    public static void removeDataSource() {
        holder.remove();
    }

}
