package com.example.tk.mq.rabbitMq.annotation;

import com.abmau.service.business.storage.mqListener.AbstractListener;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Objects;

public class AnnotationRabbitmqExecutionInterceptor implements MethodInterceptor {

    private BeanFactory beanFactory;

    public AnnotationRabbitmqExecutionInterceptor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object invocationThis = invocation.getThis();
        if (Objects.isNull(invocationThis)){
            throw new Exception("rabbitMq当前对象为空");
        }
        Class<?> targetClass = AopUtils.getTargetClass(invocation.getThis());
        Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        final Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        RabbitListenerEx rabbitListenerEx = AnnotationUtils.findAnnotation(userDeclaredMethod, RabbitListenerEx.class);
        if (Objects.isNull(rabbitListenerEx)){
            throw new Exception("未获取到RabbitListenerEx注解");
        }
        String exceptionQueue = rabbitListenerEx.exceptionQueue();
        Object obj = beanFactory.getBean(targetClass);
        if (obj instanceof AbstractListener){
            AbstractListener abstractTask = (AbstractListener) obj;
            abstractTask.setExceptionQueue(exceptionQueue);
        }
        return invocation.proceed();
    }
}
