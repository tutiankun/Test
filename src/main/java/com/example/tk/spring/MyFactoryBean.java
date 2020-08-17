package com.example.tk.spring;

import com.example.tk.vo.Car;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<Car> {
    @Override
    public Car getObject() throws Exception {
        return new Car();
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    private void init() {
        System.out.println("init方法启动了....");
    }

    private void destory() {
        System.out.println("destory方法启动了....");
    }
}
