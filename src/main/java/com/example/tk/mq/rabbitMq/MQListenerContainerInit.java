package com.example.tk.mq.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * 监听初始化
 */
@Slf4j
@Component
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
            MQListener listenerBean = entry.getValue();
            SimpleMessageListenerContainer realListenerBean = new SimpleMessageListenerContainer();
            realListenerBean.setConnectionFactory(listenerBean.getConnectionFactory());
            realListenerBean.setQueueNames(listenerBean.getQueue());
            realListenerBean.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            realListenerBean.setConcurrentConsumers(listenerBean.getConcurrentConsumers());
            realListenerBean.setMessageListener(listenerBean);
            realListenerBean.start();
        });
        log.info("info：--------------监听器初始化结束--------------");
    }


}
