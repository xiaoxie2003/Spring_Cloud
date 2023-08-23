package com.yc.resfood.controller;

import com.yc.ResOrder;
import com.yc.ResUser;
import com.yc.resfood.biz.ResorderBiz;
import com.yc.resfood.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequestMapping("order")
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private RedisTemplate redisTemplate; //从它里面取当前这个token代表的用户的购物车信息

    @Autowired
    private ResorderBiz resorderBiz; //业务

    @Autowired
    private RestTemplate restTemplate; //http客户端

    @PostMapping("orderFood")
    public Map<String,Object> orderFood(ResOrder resOrder,@RequestHeader("Authorization")String bearerToken){
        Map<String,Object> map = new HashMap<>();
        if( !this.redisTemplate.hasKey("cart_"+bearerToken) || this.redisTemplate.opsForHash().entries("cart_"+bearerToken).size()<=0){
            map.put("code",0);
            return map;
        }
        //有购物车数据
        Map<String, CartItem> cart = this.redisTemplate.opsForHash().entries("cart_"+bearerToken);
        Collection<CartItem> cis = (Collection<CartItem>) cart.values(); //CartItem（resfood对象 num smallcount）
        //TODO:调用业务层完成订单记录操作

        ResUser user = new ResUser();
        //将来这个useid也可以从登录信息中取出
        //String url = "http://localhost:8001/resfood/hello";
        String url = "http://res-security/resfood/hello";
        //复杂的http请求
        HttpHeaders headers = new HttpHeaders(){{
            set("Authorization",bearerToken);
            set("User-Agent","yc IE");
        }};
        ResponseEntity<Map> re = restTemplate.exchange(url, HttpMethod.GET,new HttpEntity<Map>(headers), Map.class);
        Map m = re.getBody(); // m就是那个 claims
        int userid = Integer.parseInt(m.get("userid").toString());
        user.setUserid(userid);

        //处理下订时间
        //创建LocalDateTime对象 表示当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        //创建DateTimeForMatter对象 指定格式为yyyy-MM-dd HH：mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //调用format方法 将LocalDateTIme对象转换为字符串
        resOrder.setOrdertime(formatter.format(now));
        //发货时间：
        LocalDateTime deleveryTime = now.plusHours(5);
        resOrder.setDeliverytime(formatter.format(deleveryTime));
        //订单状态 0表示已下单
        resOrder.setStatus(0);

        //                  订单      订单项         用户（userid）
        resorderBiz.order(resOrder,new HashSet<>(cis),user);
        double total = 0;
        for(CartItem ci:cis){
            total += ci.getSmallCount();
        }
        this.redisTemplate.delete("cart_"+bearerToken);
        map.put("code",1);
        return map;

    }
}
