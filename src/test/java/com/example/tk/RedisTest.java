package com.example.tk;

import com.example.tk.service.RedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisTest extends BaseTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void test(){
        String key="tk";
        Integer value=888;
        redisService.set(key,value,1000L);
        Object o = redisService.get(key);
        System.out.println(o);

    }

}
