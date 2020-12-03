package com.example.tk.annotation.Task;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;


public class AnnotationTaskExecutionInterceptor implements MethodInterceptor {

    private BeanFactory beanFactory;

    public AnnotationTaskExecutionInterceptor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null;
        Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        final Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        Task task = AnnotationUtils.findAnnotation(userDeclaredMethod, Task.class);

        if (null != task){
            Class[] classes = task.value();
            for (Class cls : classes) {
                Object obj = beanFactory.getBean(cls);
                if (obj instanceof AbstractTask){
                    AbstractTask abstractTask = (AbstractTask) obj;
                    abstractTask.doTask();
                }
            }
        }

        return invocation.proceed();
    }
}
