package com.example.tk.mq.rabbitmq.util;

import com.google.common.collect.Maps;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.util.Map;

/**
 * context工具类
 */
public final class ContextUtil {

    private static volatile Map<String, SimpleMessageListenerContainer> realListenerBeanMap = Maps.newConcurrentMap();

    public static void addRealListenerBean(String listenerName, SimpleMessageListenerContainer realListenerBean) {
        realListenerBeanMap.put(listenerName, realListenerBean);
    }

    public static SimpleMessageListenerContainer getRealListenerBean(String listenerName) {
        return realListenerBeanMap.get(listenerName);
    }

    public static Map<String, SimpleMessageListenerContainer> getRealListenerBeanMap() {
        return realListenerBeanMap;
    }





}
