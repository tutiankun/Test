package com.example.tk.Interface.Impl;

import com.example.tk.Interface.Hello;
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
