package com.example.tk.mq.rabbitMq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class AbstractListener<T> implements MQListener<T> {
    private AtomicInteger errorCount = new AtomicInteger(0);
    private String exceptionQueue;//异常队列
    private ProcessTypeEnum typeEnum;//异常处理策略,默认重回队尾
    private String queue;//消费队列
    private ConnectionFactory connectionFactory;//连接工厂
    private Integer concurrentConsumers=1;//默认并发消费数量1,支持手动配置


    public AbstractListener() {
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String msgStr = null;
        try {
            msgStr = this.messageBody(message);
            if (StringUtils.isEmpty(msgStr)) {
                throw new Exception("消息为空");
            }
            T obj = this.convertMessage(msgStr);
            this.listener(obj);
            this.setProcessTypeEnum();
            this.errorCount.set(0);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("消费异常:",e);
            this.onErrorHandler(e, message, channel);
        }
    }

    @Override
    public String messageBody(Message message) throws Exception {
        return null != message.getBody() && message.getBody().length > 0 ? new String(message.getBody(), StandardCharsets.UTF_8) : null;
    }

    @Override
    public T convertMessage(String message) throws Exception {
        return JSON.parseObject(message, this.getType(), new Feature[0]);
    }

    private Type getType() {
        return ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 业务处理
     * @param t
     * @throws Exception
     */
    public abstract void listener(T t) throws Exception;

    /**
     * 异常处理策略
     * @return
     */
    public abstract void setProcessTypeEnum();

    @Override
    public void onErrorHandler(Exception e, Message message, Channel channel) {
        this.processExceptionMsg(message, channel);
        this.sleepForError();
    }

    private void processExceptionMsg(Message message, Channel channel) {
        try {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            BasicProperties properties = this.cloneAMQPProperties(message.getMessageProperties());
           // this.processExceptionMsgVersion(message, channel, deliveryTag, properties);
        } catch (Exception e) {
            log.error("exceptionQueue", e);
        }
    }

    private BasicProperties cloneAMQPProperties(MessageProperties messageProperties) throws Exception {
        BasicProperties basicProperties = new BasicProperties();
        BeanUtils.copyProperties(messageProperties, basicProperties);
        try {
            if (null == basicProperties.getHeaders()) {
                Field headersField = BasicProperties.class.getDeclaredField("headers");
                headersField.setAccessible(true);
                headersField.set(basicProperties, new HashMap());
            }
        } catch (Exception var4) {
            log.error("反射异常，请检查");
            throw new Exception("反射异常，请检查");
        }

        if (null != messageProperties.getHeaders()) {
            basicProperties.getHeaders().putAll(messageProperties.getHeaders());
        }
        return basicProperties;
    }



    public void setExceptionQueue(String exceptionQueue){
        this.exceptionQueue=exceptionQueue;
    }

    public void setTypeEnum(ProcessTypeEnum typeEnum){
        this.typeEnum=typeEnum;
    }

    private void sleepForError() {
        try {
            int num = this.errorCount.incrementAndGet();
            TimeUnit.MILLISECONDS.sleep(this.errorSleep(num));
        } catch (InterruptedException e) {
            log.error("onMessage sleep", e);
            Thread.currentThread().interrupt();
        }

    }

    private Long errorSleep(int errorCount) {
        if (errorCount < 10) {
            return 1000L;
        } else if (errorCount < 20) {
            return 2000L;
        } else {
            return errorCount < 50 ? 5000L : 10000L;
        }
    }


    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getQueue() {
        return this.queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public Integer getConcurrentConsumers() {
        return this.concurrentConsumers;
    }

    public void setConcurrentConsumers(Integer count) {
        this.concurrentConsumers = count;
    }




}
