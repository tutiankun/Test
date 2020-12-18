package com.example.tk.mq.rabbitmq;

import java.lang.annotation.*;


/**
 * 消息属性
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RabbitListenerEx {
    /**
     * 连接工厂
     */
    String connectionFactory() default "";

    /**
     * 消费队列
     */
    String queue() default "";

    /**
     * 消费策略,默认重回队尾
     */
    ProcessTypeEnum typeEnum() default ProcessTypeEnum.REPUT_QUEUE_TAIL;

    /**
     *并发消费者数量
     */
    int consumers() default 1;

    /**
     *异常队列
     */
    String exceptionQueue() default "";

    /**
     *预抓取数量
     */
    int prefetchCount() default 50;


}
