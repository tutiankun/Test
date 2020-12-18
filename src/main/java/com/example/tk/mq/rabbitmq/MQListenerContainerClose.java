package com.example.tk.mq.rabbitmq;

import com.example.tk.mq.rabbitmq.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 容器关闭关闭监听
 */
@Slf4j
public class MQListenerContainerClose implements ApplicationListener<ContextClosedEvent> {

    private final AtomicBoolean shutdowned = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        if (this.shutdowned.compareAndSet(false, true)) {
            log.error("info：--------------监听器关闭开始--------------");
            this.shutdown();
            log.error("info：--------------监听器关闭结束--------------");
        }
    }

    private void shutdown(){
        Map<String, SimpleMessageListenerContainer> listenerBeanMap = ContextUtil.getRealListenerBeanMap();
        if (ObjectUtils.isEmpty(listenerBeanMap)){
            log.error("info：监听器不存在，不进行关闭");
            return;
        }
        listenerBeanMap.entrySet().forEach(entry->{
            SimpleMessageListenerContainer listenerContainer = entry.getValue();
            listenerContainer.stop();
            listenerContainer.shutdown();
        });


    }



}
