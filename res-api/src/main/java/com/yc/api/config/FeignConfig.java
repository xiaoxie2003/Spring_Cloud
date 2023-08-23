package com.yc.api.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    // 设置 feign的   网络请求的日志级别
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;    // NONE, BASIC,HEADERS,FULL
    }
}
