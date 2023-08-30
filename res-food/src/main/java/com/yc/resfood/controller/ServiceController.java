//package com.yc.resfood.controller;
//
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("service") //http:localhost:port/resfood
//@Slf4j
//@Api(tags = "服务")
//@RefreshScope
//public class ServiceController {
//
//    //利用DI将 res.pattern.dateformate注入
//    //res:
//    //  pattern:
//    //    dateformat
//    @Value("${res.pattern.dateformat}")
//    private String dateFormate;
//
//    @RequestMapping("nowTime")
//    public Map<String,Object> nowTime(){
//        Map<String,Object> map = new HashMap<>();
//
//        Date d = new Date();
//        DateFormat df = new SimpleDateFormat(dateFormate);
//
//        map.put("code",1);
//        map.put("obj",df.format(d));
//        return map;
//    }
//}
