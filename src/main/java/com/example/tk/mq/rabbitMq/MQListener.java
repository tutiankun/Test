package com.example.tk.mq.rabbitMq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * mq消费抽象接口
 * @param <T>
 */
public interface MQListener<T> extends ChannelAwareMessageListener {

    /**
     * Message中取消息并utf8编码
     * @param message
     * @return
     * @throws Exception
     */
    String messageBody(Message message) throws Exception;

    /**
     * 消息json转为实体类
     * @param message
     * @return
     * @throws Exception
     */
    T convertMessage(String message) throws Exception;

    /**
     * 业务处理
     * @param t
     * @throws Exception
     */
    void listener(T t) throws Exception;

    /**
     * 消费异常处理
     * @param e
     * @param message
     * @param channel
     */
    void onErrorHandler(Exception e, Message message, Channel channel);

    /**
     *
     * @param typeEnum
     */
    void setTypeEnum(ProcessTypeEnum typeEnum);

    /**
     * 异常队列
     *
     * @param exceptionQueue
     */
    default void setExceptionQueue(String exceptionQueue) {}


}
