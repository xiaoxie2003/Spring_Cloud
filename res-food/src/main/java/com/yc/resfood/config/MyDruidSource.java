package com.yc.resfood.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

//将原来的springboot的自动IOC DruidDatasource方案改为手工编程
@Configuration
@Slf4j
@RefreshScope //刷新nacos配置中心 更改了配置中心之后立马可以获取到
public class MyDruidSource {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Primary
    @RefreshScope
    public DataSource druid(){
        log.info("使用的编程式的数据源创建");
        DruidDataSource ds = new DruidDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(this.driverClassName);
        ds.setUrl(url);
        return ds;
    }
}
