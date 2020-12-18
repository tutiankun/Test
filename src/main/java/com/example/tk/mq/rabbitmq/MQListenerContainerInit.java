package com.example.tk.mq.rabbitmq;

import com.example.tk.mq.rabbitmq.exception.MQBaseException;
import com.example.tk.mq.rabbitmq.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 监听初始化
 */
@Slf4j
public class MQListenerContainerInit implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Override
    public void afterPropertiesSet(){
        log.info("info：--------------监听器初始化开始--------------");
        Map<String, MQListener> mqListenerMap = this.applicationContext.getBeansOfType(MQListener.class);
        if (ObjectUtils.isEmpty(mqListenerMap)){
            log.error("--------------项目中不存在mq listener, 不进行mq listener的处理--------------");
            return;
        }
        mqListenerMap.entrySet().forEach(entry->{
            String beanName = entry.getKey();
            MQListener listenerBean = entry.getValue();
            RabbitListenerEx rabbitListenerEx = AnnotationUtils.findAnnotation(listenerBean.getClass(), RabbitListenerEx.class);
            if (Objects.isNull(rabbitListenerEx)){
                throw new MQBaseException("info_missing",beanName+":未配置监听注解");
            }
            //设置监听属性
            listenerBean.setExceptionQueue(rabbitListenerEx.exceptionQueue());
            listenerBean.setTypeEnum(rabbitListenerEx.typeEnum());
            String queue = rabbitListenerEx.queue();
            String beanQueue = listenerBean.getQueue();
            //支持注解添加队列和set方法添加队列,并做校验
            if (StringUtils.isEmpty(queue) && StringUtils.isEmpty(beanQueue)){
                throw new MQBaseException("info_missing",beanName+":未配置队列名称");
            }
            if (!StringUtils.isEmpty(queue) && !StringUtils.isEmpty(beanQueue)
                    && !Objects.equals(queue,beanQueue)
            ){
                throw new MQBaseException("info_error",beanName+":队列名称配置不统一");
            }
            String connectionFactory = rabbitListenerEx.connectionFactory();
            if (StringUtils.isEmpty(connectionFactory)){
                throw new MQBaseException("info_missing",beanName+":未配置连接工厂");
            }
            ConnectionFactory conFactory;
            try {
                conFactory = (ConnectionFactory) applicationContext.getBean(connectionFactory);
            }catch (Exception e){
                throw new MQBaseException("info_missing",beanName+":连接工厂配置错误",e);
            }
            SimpleMessageListenerContainer realListenerBean = new SimpleMessageListenerContainer();
            realListenerBean.setPrefetchCount(rabbitListenerEx.prefetchCount());
            realListenerBean.setConnectionFactory(conFactory);
            realListenerBean.setQueueNames(StringUtils.isEmpty(queue)?beanQueue:queue);
            realListenerBean.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            realListenerBean.setConcurrentConsumers(rabbitListenerEx.consumers());
            realListenerBean.setMessageListener(listenerBean);
            //设置队列名称便于定位问题
            listenerBean.setBeanName(beanName);
            listenerBean.setQueue(realListenerBean.getQueueNames()[0]);
            ContextUtil.addRealListenerBean(beanName, realListenerBean);
            realListenerBean.start();
        });
        log.info("info：--------------监听器初始化结束--------------");
    }


}
