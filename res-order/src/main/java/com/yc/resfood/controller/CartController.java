package com.yc.resfood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.ResFood;
import com.yc.api.ResfoodApi;
import com.yc.resfood.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 购物车控制器
 */
@RestController
@Slf4j
@RequestMapping("cart")
public class CartController {

    //redis模板
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    //Rest客户端：它利用http请求 ，请求其他服务
    //restTemplate： HttpUrlConnection
    @Autowired
    private RestTemplate restTemplate;

    //openfeign
    @Resource
    private ResfoodApi resfoodApi;

    /**
     * 取出token 根据键“cart_”+token到redis中进行购物车项删除
     *   注意：并不能删除这个键值 只删除这个键对应的值（购物项）即可
     * @param bearerToken
     * @return
     */
    @RequestMapping(value = "clearAll",method = RequestMethod.GET)
    public Map<String,Object> clearAll(@RequestHeader("Authorization")String bearerToken){
        Map<String,Object> result = new HashMap<>();
        if(this.redisTemplate.hasKey("cart_"+bearerToken)){
            //取出此人 bearerToken 所有的购物项（数据清空了 容器还在）
            Set<Object> keys = this.redisTemplate.opsForHash().keys("cart_"+bearerToken);
            //keys:1,2 ==> “1,2”
            this.redisTemplate.opsForHash().delete("cart_"+bearerToken,keys.toArray());
            result.put("code",1);
            result.put("obj",keys);
        }else {
            result.put("code",0);
        }
        return result;
    }

    @RequestMapping(value = "getCartInfo",method = RequestMethod.GET)
    public Map<String ,Object> getCartInfo(@RequestHeader("Authorization")String bearerToken){
        Map<String,Object> result = new HashMap<>();
        if(this.redisTemplate.hasKey("cart_"+bearerToken)){
            Map<Object,Object> cart = this.redisTemplate.opsForHash().entries("cart_"+bearerToken);
            log.info("token为：" + bearerToken+",的购物车为："+cart);
            result.put("code",1);
            result.put("obj",cart.values());
        }else {
            result.put("code",0);
        }
        return result;
    }

    /**
     * 添加购物车
     * @param fid 商品编号
     * @param num 数量
     * @param bearerToken 用户Token
     * @return
     */
    @RequestMapping(value = "addCart",method = RequestMethod.GET)
    public Map<String,Object> addCart(@RequestParam Integer fid,@RequestParam Integer num,@RequestHeader("Authorization")String bearerToken){
        //返回结果：{“code”：1/0，data：{数据对象}}
        Map<String,Object> result = new HashMap<>();
        //TODO:到nacos中查找res-food服务中的findById 要得到菜品对象resfood => 此技术叫服务发现 且调用服务得到结果
        ResFood rf = null;
        //方案一： 利用url地址直接访问服务
        //String url = "http://localhost:9001/resfood/findById/" + fid;
        //目标： 发一个http请求 url如上
        //socket -> URLConnection -> HttpClient -> restTemplate(针对rest 是spring的封装)

        //方案二：利用服务名 通过服务发现功能自动找到url
        //String url = "http://res-food/resfood/findById/" + fid;  //fegin -> openfergin
        //简单的http请求
        //Map<String,Object> resultMap = this.restTemplate.getForObject(url,Map.class);

        //方案三：openFeign
        Map<String,Object> resultMap = this.resfoodApi.findById(fid);

        if("1".equalsIgnoreCase(resultMap.get("code").toString())){
            Map m = (Map) resultMap.get("obj");
            //如何将一个map转为一个 ResFood对象 -> 反射
            //spring的底层对json的处理使用jackson框架(json处理框架) 这个框架有将mao转为对象的方法
            ObjectMapper mapper = new ObjectMapper();
            rf = mapper.convertValue(m, ResFood.class); //将map转化为resfood对象
        }else {
            result.put("code",0);
            result.put("msg","查无此商品" + fid);
            return  result;
        }
        //从redis中查询当前用户是否有此商品的购买 如果买过 则加数量 没有买过 则创建一个CartItem 存数据 获取购物车项的信息
        CartItem ci = (CartItem) this.redisTemplate.opsForHash().get("cart_"+bearerToken,fid+"");
        //计算fid购物项的数量
        if(ci == null){
            //没有买过
            ci = new CartItem();
            ci.setFood(rf);
            ci.setNum(num);
        }else {
            int newNum = ci.getNum() + num;
            ci.setNum(newNum);
        }
        //计算金额
        if(ci.getNum() <= 0){
            this.redisTemplate.opsForHash().delete("cart_" + bearerToken,fid+"");
        }else {
            ci.getSmallCount(); //计算小计
            this.redisTemplate.opsForHash().put("cart_" + bearerToken,fid+"",ci);
        }
        result.put("code",1);
        Map m = redisTemplate.opsForHash().entries("cart_" + bearerToken); //取出此人 bearerToken所有的购物车信息
        result.put("data",m.values());
        return result;

    }
}
