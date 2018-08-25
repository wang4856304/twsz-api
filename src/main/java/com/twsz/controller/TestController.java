package com.twsz.controller;

import com.alibaba.fastjson.JSONObject;
import com.twsz.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @date 18-2-9 下午3:33
 * @description
 * @modified by
 */

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/test")
    public Object test(@RequestBody JSONObject jsonObject) {
        return null;
    }

    @RequestMapping("/hello")
    public Object hello(@RequestParam String name) {
        return null;
    }
}
