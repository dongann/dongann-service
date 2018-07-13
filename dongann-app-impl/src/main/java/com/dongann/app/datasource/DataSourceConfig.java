package com.dongann.app.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @FileName: DataSourceConfig
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/5/28 下午11:26
 * @Version: v1.0
 * @description: 数据源配置
 */
@Configuration
@MapperScan(basePackages = "com.dongann.app.dao",sqlSessionFactoryRef = "sqlSessionFactoryDS",sqlSessionTemplateRef = "dbSqlSessionTemplate")
public class DataSourceConfig {

    private static final String configLocation = "mybatis-config.xml";
    private static final String mapperLocations = "classpath:mapper/mall/**/**/*.xml";

    /**
     * 主库
     * @return
     */
    @Bean(name = "dbmaster")
    @ConfigurationProperties(prefix = "spring.datasource.dbmaster")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    /**
     * 从库
     * @return
     */
    @Bean(name = "dbslave")
    @ConfigurationProperties(prefix = "spring.datasource.dbslave")
    public DataSource slaveDataSource() {
        return new DruidDataSource();
    }

    /**
     * 动态数据源
     * @return
     */
    @DependsOn({ "dbmaster", "dbslave"})
    @Bean(name = "dynamicDataSource")
    @Primary
    public DataSource getDataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.getType(), masterDataSource());
        targetDataSources.put(DataSourceType.SLAVE.getType(), slaveDataSource());
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(masterDataSource());
        return dataSource;
    }

    @Bean(name = "sqlSessionFactoryDS")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "dbTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dbSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryDS") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * druid监控相关配置
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // IP白名单 (没有配置或者为空，则允许所有访问)
        registrationBean.addInitParameter("allow", "");
        // IP黑名单 (存在共同时，deny优先于allow)
        registrationBean.addInitParameter("deny", "");
        registrationBean.addInitParameter("loginUsername", "root");
        registrationBean.addInitParameter("loginPassword", "000000");
        registrationBean.addInitParameter("resetEnable", "false");
        return registrationBean;
    }

    /**
     * druid filter
     * @return
     */
    @Bean
    public FilterRegistrationBean druidWebStatViewFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new WebStatFilter());
        registrationBean.addInitParameter("urlPatterns", "/*");
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return registrationBean;
    }

}
