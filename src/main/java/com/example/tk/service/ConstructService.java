package com.example.tk.service;

import com.example.tk.Interface.Hello;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConstructService {
    private HashMap<String,Hello> map=new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public ConstructService(List<Hello> hellos){
        for (Hello hello : hellos) {
            map.put(hello.type(),hello);
        }
    }

    public void test(){
        for (String s : map.keySet()) {
            System.out.println("s:"+s);
            Hello hello = map.get(s);
            hello.say();
        }
    }

    public Integer testTry(){
       try {
           int a=1/0;
           return 1;

       }catch (Exception e){
           return 2;
       }finally {
           return 0;
       }

    }




}
