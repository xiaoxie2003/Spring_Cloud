package com.yc.resfood.config;


import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * sentinel全局异常处理
 */
//adaptor将sentinel的异常 抛出异常 这个异常被handler捕捉
@Component
public class MySentinelExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        String msg = null;
        if(e instanceof FlowException){
            msg = "访问频繁。请稍后再试";
        } else if (e instanceof DegradeException) {
            msg = "系统降级";
        }else if (e instanceof ParamFlowException) {
            msg = "热点参数异常:" + e.getMessage()+","+((ParamFlowException)e).getResourceName()+","+e.getRule();
        }else if (e instanceof SystemBlockException) {
            msg = "系统规则限流或降级";
        }else if (e instanceof AuthorityException) {
            msg = "授权规则不通过";
        }else {
            msg = "位置限流降级";
        }
        //http状态码
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type","application/json;charset=utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg",msg);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(map);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(json);
        writer.flush();
    }
}
