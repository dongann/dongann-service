package com.dongann.app.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @FileName: DataSourceConfig
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/5/28 下午11:26
 * @Version: v1.0
 * @description: 数据源1配置
 */
@Configuration
@MapperScan(basePackages = "com.dongann.app.dao.mall", sqlSessionTemplateRef = "dbDongannSqlSessionTemplate")
public class DataSourceConfig {

	@Bean(name = "dbdongann")
	@ConfigurationProperties(prefix = "spring.datasource.dbdongann")
	@Primary // 设置主库
	public DataSource testDataSource() {
		return new DruidDataSource();
	}

	@Bean(name = "dbDongannSqlSessionFactory")
	@Primary
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("dbdongann") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/mall/**/**/*.xml"));
		bean.setConfigLocation(new DefaultResourceLoader().getResource("mybatis-config.xml"));
		return bean.getObject();
	}

	@Bean(name = "dbDongannTransactionManager")
	@Primary
	public DataSourceTransactionManager testTransactionManager(@Qualifier("dbdongann") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "dbDongannSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("dbDongannSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
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

	@Bean
	public FilterRegistrationBean druidWebStatViewFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new WebStatFilter());
		registrationBean.addInitParameter("urlPatterns", "/*");
		registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		return registrationBean;
	}

}
