package com.yc.api.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class FeignConfig {

    // 设置 feign的   网络请求的日志级别
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;    // NONE, BASIC,HEADERS,FULL
    }

    /**
     * 请求拦截器 统一加入origin请求头信息
     * feign中的拦截机制也是一个 责任链模式
     */
    @Component
    public class CustomerRequestInterceptor implements RequestInterceptor{

        @Override
        public void apply(RequestTemplate requestTemplate) {
            //requestTemplate 请求对象
            requestTemplate.header("source","order-source");
        }
    }
}
