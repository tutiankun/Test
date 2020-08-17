package com.example.tk.myAbstract;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitBeanB implements InitializingBean {
    @Autowired
    private InitBeanA initBeanA;


    public void testb(){
        System.out.println("测试初始化b....");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        initBeanA.testa();
    }
}
