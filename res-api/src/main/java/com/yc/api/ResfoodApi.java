package com.yc.api;

import com.yc.api.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 动态代理
 */
//          value：服务名       path：访问路径的前缀
@FeignClient(value = "res-food",path = "resfood",configuration = FeignConfig.class)
public interface ResfoodApi {

    //@RequestLine("GET/findByPage")
    @RequestMapping(value = "findByPage",method = {RequestMethod.GET})
    public Map<String, Object> findByPage(@RequestParam int pageno,@RequestParam int pagesize,
                                          @RequestParam String sortby,@RequestParam String sort);
    //形成url: http://res-food/resfood/findByPage?pageno=x&pagesize=x&sortby=fid&sort=desc

    //Openfeign支持MVC注解
    @GetMapping("findById/{fid}")
    public Map<String, Object> findById(@PathVariable Integer fid);
    //形成url: http://res-food/resfood/findById/1

    @GetMapping("findAll")
    public Map<String, Object> findAll();
}
