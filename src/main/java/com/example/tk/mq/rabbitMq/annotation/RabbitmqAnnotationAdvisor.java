package com.example.tk.mq.rabbitMq.annotation;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class RabbitmqAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private Advice advice;
    private Pointcut pointcut;

    public RabbitmqAnnotationAdvisor(BeanFactory beanFactory){
        Set<Class<? extends Annotation>> taskAnnotationTypes = new LinkedHashSet(1);
        taskAnnotationTypes.add(RabbitListenerEx.class);
        this.advice = this.buildAdvice(beanFactory);
        this.pointcut = this.buildPointcut(taskAnnotationTypes);
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware)this.advice).setBeanFactory(beanFactory);
        }
    }

    protected Advice buildAdvice(BeanFactory beanFactory) {
        return new AnnotationRabbitmqExecutionInterceptor(beanFactory);
    }

    protected Pointcut buildPointcut(Set<Class<? extends Annotation>> taskAnnotationTypes) {
        ComposablePointcut result = null;

        AnnotationMatchingPointcut mpc;
        for(Iterator var3 = taskAnnotationTypes.iterator(); var3.hasNext(); result = result.union(mpc)) {
            Class<? extends Annotation> asyncAnnotationType = (Class)var3.next();
            Pointcut cpc = new AnnotationMatchingPointcut(asyncAnnotationType, true);
            mpc = AnnotationMatchingPointcut.forMethodAnnotation(asyncAnnotationType);
            if (result == null) {
                result = new ComposablePointcut(cpc);
            } else {
                result.union(cpc);
            }
        }
        return result;
    }

}
