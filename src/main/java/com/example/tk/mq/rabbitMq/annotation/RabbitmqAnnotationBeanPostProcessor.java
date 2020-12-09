package com.example.tk.mq.rabbitMq.annotation;

import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqAnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {
    private ApplicationContext applicationContext;

    public RabbitmqAnnotationBeanPostProcessor(){

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        RabbitmqAnnotationAdvisor advisor = new RabbitmqAnnotationAdvisor(beanFactory);
        advisor.setBeanFactory(beanFactory);
        this.advisor = advisor;
    }

}
