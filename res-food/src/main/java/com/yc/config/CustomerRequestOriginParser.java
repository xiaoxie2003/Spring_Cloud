package com.yc.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Component
@Slf4j
public class CustomerRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        log.info("解析请求头中的origin");
        String sorce = httpServletRequest.getHeader("source");
        log.info("请求来源为：" + sorce);
        return sorce;
    }
}
