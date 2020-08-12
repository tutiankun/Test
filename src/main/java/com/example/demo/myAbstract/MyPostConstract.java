package com.example.demo.myAbstract;

import com.example.demo.bean.Car;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyPostConstract {

    @PostConstruct
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println("PostConstract方法执行了-------");

    }
}
