package com.example.demo.Interface.Impl;

import com.example.demo.Interface.Hello;
import com.example.demo.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UsaHelloImpl implements Hello {
    @Override
    public void say() {
        System.out.println("你好,你是美帝吗");
    }

    @Override
    public String type() {
        return "usa";
    }
}
