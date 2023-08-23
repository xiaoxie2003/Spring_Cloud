package com.yc.api;

import com.yc.api.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "res-order",path = "cart",configuration = FeignConfig.class)
public interface CartApi {

    @RequestMapping(value = "clearAll", method = RequestMethod.GET)
    public Map<String, Object> clearAll(@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(value = "getCartInfo", method = RequestMethod.GET)
    public Map<String, Object> getCartInfo(@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(value = "addCart", method = RequestMethod.GET)
    public Map<String, Object> addCart(@RequestParam Integer fid,
                                       @RequestParam Integer num,
                                       @RequestHeader("Authorization") String bearerToken);

}