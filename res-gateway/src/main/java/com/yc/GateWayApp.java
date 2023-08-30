package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient //打开服务发现客户端
@Slf4j
public class GateWayApp {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApp.class,args);
    }

//    //全局过滤器另一种方法 核心：创建全局过滤器GlobalFilter并托管
//    @Bean
//    @Order(-1)
//    public GlobalFilter hello(){
//
//        return (exchange, chain) -> {
//            log.info("hello");
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                log.info("bye");
//            }));
//        };
//    }
}
