package com.example.demo.myAbstract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class Teacher extends Person {
    @Autowired
    private Dog dog;

    @Override
    public void say() {
        System.out.println("抽象类中的抽象方法");
    }

    @Override
    public void smile() {
        System.out.println("笑了***");
        say();
    }

    public void test(){
        dog.fun();
    }

}
