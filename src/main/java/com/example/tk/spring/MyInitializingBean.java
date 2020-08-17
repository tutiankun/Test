package com.example.tk.spring;

import com.example.tk.Interface.Hello;
import com.example.tk.myAbstract.MyAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MyInitializingBean implements InitializingBean {
    @Autowired
    @Qualifier(value = "chineseHelloImpl")
    private Hello hello;

    @Autowired
    private MyAware myAware;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean方法执行了===========");
        ApplicationContext applicationContext = myAware.getApplicationContext();
        System.out.println(hello);
    }
}
