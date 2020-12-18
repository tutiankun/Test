package com.example.tk.mq.rabbitmq.config;

import com.example.tk.mq.rabbitmq.MQListenerContainerClose;
import com.example.tk.mq.rabbitmq.MQListenerContainerInit;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mq组件自动装配
 */
@Configuration
public class MqListenerComponentAutoConfiguration {

    /**
     * mq监听初始化
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MQListenerContainerInit.class)
    public MQListenerContainerInit mqContainerInit(){
       return new MQListenerContainerInit();
    }

    /**
     * mq监听容器关闭
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MQListenerContainerClose.class)
    public MQListenerContainerClose mqContainerClose(){
        return new MQListenerContainerClose();
    }


}
