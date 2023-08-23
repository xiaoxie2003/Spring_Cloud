package com.yc.resfood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.yc.resfood.dao")
@EnableDiscoveryClient
@EnableSwagger2
@EnableOpenApi
public class SecurityApp {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApp.class,args);

    }
}
