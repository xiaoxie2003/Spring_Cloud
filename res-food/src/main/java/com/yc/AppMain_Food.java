package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.yc.dao")
@EnableSwagger2 //Swagger
@EnableDiscoveryClient //启用服务注册发现的客户端:httpclient/postman
@EnableOpenApi //开启Swagger
public class AppMain_Food {
    public static void main(String[] args) {
        SpringApplication.run(AppMain_Food.class,args);
    }
}
