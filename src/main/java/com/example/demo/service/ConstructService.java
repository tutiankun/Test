package com.example.demo.service;

import com.example.demo.Interface.Hello;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConstructService implements InitializingBean {
    private HashMap<String,Hello> map=new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public ConstructService(List<Hello> hellos){
        for (Hello hello : hellos) {
            map.put(hello.type(),hello);
        }
    }


    @Override
    public void afterPropertiesSet()  {
        Map<String, Hello> beansOfType = applicationContext.getBeansOfType(Hello.class);
        for (Map.Entry<String, Hello> entry : beansOfType.entrySet()) {
            String key = entry.getKey();
            System.out.println("key:"+key);
            Hello hello = entry.getValue();
            String type = hello.type();
            System.out.println("type:"+type);
            hello.say();
        }
    }

    public void test(){
        for (String s : map.keySet()) {
            System.out.println("s:"+s);
            Hello hello = map.get(s);
            hello.say();
        }
    }



}
