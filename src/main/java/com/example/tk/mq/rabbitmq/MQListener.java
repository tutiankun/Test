package com.example.tk.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
     * 消费异常处理
     * @param e
     * @param message
     * @param channel
     */
    void onErrorHandler(Exception e, Message message, Channel channel);

    /**
     * 消费异常枚举
     * @param typeEnum
     */
    void setTypeEnum(ProcessTypeEnum typeEnum);

    /**
     * 异常队列
     *
     * @param exceptionQueue
     */
    default void setExceptionQueue(String exceptionQueue) {}

    /**
     * 获取消息连接工厂
     * @return
     */
    ConnectionFactory getConnectionFactory();

    /**
     * 设置连接工厂
     * @param connectionFactory
     */
    void setConnectionFactory(ConnectionFactory connectionFactory);

    /**
     * 获取消费队列
     * @return
     */
    String getQueue();

    /**
     * 设置连接工厂
     * @param queue
     */
    void setQueue(String queue);


    /**
     * 设置bean名称便于排查问题
     * @param beanName
     */
    void setBeanName(String beanName);





}
