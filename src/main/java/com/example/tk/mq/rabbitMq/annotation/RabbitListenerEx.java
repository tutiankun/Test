package com.example.tk.mq.rabbitMq.annotation;

import java.lang.annotation.*;


/**
 * 消息属性
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RabbitListenerEx {

    /**
     *异常队列
     */
    String exceptionQueue() default "";


}
