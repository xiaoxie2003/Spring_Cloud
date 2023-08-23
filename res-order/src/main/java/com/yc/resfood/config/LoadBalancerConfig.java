package com.yc.resfood.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@LoadBalancerClients(
        value = {
                @LoadBalancerClient(value = "res-food", configuration = MyOnlyOnceConfig.class),
                @LoadBalancerClient(value = "res-security", configuration = RandomConfig.class),
        }, defaultConfiguration = LoadBalancerClientConfiguration.class
)
public class LoadBalancerConfig {
    @LoadBalanced // 开启RestTemplate对象的负载均衡功能
    @Bean
    public RestTemplate restTemplate(){
                return new RestTemplate();
        }
}
