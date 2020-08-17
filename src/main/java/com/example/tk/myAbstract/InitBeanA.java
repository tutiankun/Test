package com.example.tk.myAbstract;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitBeanA implements InitializingBean {
    @Autowired
    private InitBeanB initBeanB;

    public void testa(){
        System.out.println("测试初始化a....");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        initBeanB.testb();
    }
}
