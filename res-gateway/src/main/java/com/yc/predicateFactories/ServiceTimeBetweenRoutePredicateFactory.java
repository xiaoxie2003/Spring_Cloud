package com.yc.predicateFactories;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
@Slf4j
public class ServiceTimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<ServiceTimeBetweenRoutePredicateFactory.CustomTimeBetweenConfig> {

    /**
     * 配置类
     */
    @Data
    public static class CustomTimeBetweenConfig {
        private LocalTime startTime;
        private LocalTime endTime;
    }

    public ServiceTimeBetweenRoutePredicateFactory() {
        super(CustomTimeBetweenConfig.class);
    }

    /**
     * 用于yml中的配置CustomTimeBetweenConfig=上午6.00 下午12.00
     *
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("startTime", "endTime");
    }

    /**
     * 业务逻辑判断
     *
     * @param config
     * @return
     */
    @Override
    public Predicate<ServerWebExchange> apply(CustomTimeBetweenConfig config) {
        //获取参数值
        LocalTime startTime = config.getStartTime();
        LocalTime endTime = config.getEndTime();
        //创建谓词对象
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                LocalTime now = LocalTime.now();
                //判断当前时间是否在配置的时间范围内
                return now.isAfter(startTime) && now.isBefore(endTime);
            }
        };
    }

}
