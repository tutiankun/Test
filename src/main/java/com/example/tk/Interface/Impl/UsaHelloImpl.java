package com.example.tk.Interface.Impl;

import com.example.tk.Interface.Hello;
import org.springframework.stereotype.Service;

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
