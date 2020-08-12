package com.example.demo.Interface.Impl;

import com.example.demo.Interface.Hello;
import com.example.demo.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ChineseHelloImpl implements Hello {
    @Override
    public void say() {
        System.out.println("你好,我是中国人");
    }

    @Override
    public String type() {
        return "chinese";
    }

}
