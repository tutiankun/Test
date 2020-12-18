package com.example.tk.mq.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.example.tk.mq.rabbitmq.exception.MQBaseException;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class AbstractListener<T> implements MQListener<T> {
    private AtomicInteger errorCount = new AtomicInteger(0);
    private String exceptionQueue;//异常队列
    private ProcessTypeEnum typeEnum;//异常处理策略,默认重回队尾
    private String queue;//消费队列
    private ConnectionFactory connectionFactory;//连接工厂
    private String beanName;//便于定位问题


    public AbstractListener() {
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String msgStr = null;
        try {
            msgStr = this.messageBody(message);
            if (StringUtils.isEmpty(msgStr)) {
                throw new MQBaseException("message_blank", "消息为空");
            }
            T obj = this.convertMessage(msgStr);
            this.listener(obj);
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


    @Override
    public void onErrorHandler(Exception e, Message message, Channel channel) {
        this.processExceptionMsg(message, channel);
        this.sleepForError();
    }

    private void processExceptionMsg(Message message, Channel channel) {
        try {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            BasicProperties properties = this.cloneAMQPProperties(message.getMessageProperties());
            this.processExceptionMsgVersion(message, channel, deliveryTag, properties);
        } catch (Exception e) {
            log.error("exceptionQueue", e);
        }
    }

    private void processExceptionMsgVersion(Message message, Channel channel,long deliveryTag,BasicProperties properties)throws IOException {
        switch(this.typeEnum){
            case DIRECT_ACK:
                channel.basicAck(deliveryTag, false);
                break;
            case EXCEPTION_QUEUE:
                this.putMsg2ExceptionQueue(message, channel, deliveryTag, properties, this.exceptionQueue);
                break;
            case REPUT_QUEUE_HEAD:
                this.reputMsg2QueueHead(channel, deliveryTag);
                break;
            case REPUT_QUEUE_TAIL:
                this.reputMsg2QueueEnd(message, channel, deliveryTag, properties);
                break;
            case REPUT_QUEUE_COUNT_DISCARD:
                this.processExceptionMsg4ReputQueueCountDiscard(message, channel, deliveryTag, properties);
                break;
            case REPUT_QUEUE_COUNT_EXCEPTION_QUEUE:
                this.processExceptionMsg4ReputQueueCountExceptionQueue(message, channel, deliveryTag, properties);
                break;
            default:
                log.error("warn：{}异常处理类型不正确", this.beanName);

        }
    }

    private void processExceptionMsg4ReputQueueCountExceptionQueue(Message message, Channel channel, long deliveryTag, BasicProperties properties)throws IOException{
        if (StringUtils.isEmpty(this.exceptionQueue)){
            throw new MQBaseException("info_missing",this.beanName+"处理类型为 REPUT_QUEUE_COUNT_EXCEPTION_QUEUE 的异常队列为空");
        }
        Integer failCount = this.getFailCount(properties);
        if (failCount >= 3) {
            log.error("消费失败{}次放入异常队列, 消息体: {}", failCount, this.getMessageStr(message));
            this.putMsg2ExceptionQueue(message, channel, deliveryTag, properties, exceptionQueue);
        } else {
            Map<String, Object> msgHeaderMap = properties.getHeaders();
            if (null != msgHeaderMap) {
                msgHeaderMap.put("Msg-Consumed-Fail-Count", failCount);
            }
            this.reputMsg2QueueEnd(message, channel, deliveryTag, properties);
        }
    }

    private void processExceptionMsg4ReputQueueCountDiscard(Message message, Channel channel, long deliveryTag, BasicProperties properties)throws IOException{
        Integer failCount = this.getFailCount(properties);
        if (failCount >= 3) {
            log.error("消费失败{}次丢弃消息, 消息体: {}", failCount, this.getMessageStr(message));
            this.discardMsg(channel, deliveryTag);
        } else {
            Map<String, Object> msgHeaderMap = properties.getHeaders();
            if (null != msgHeaderMap) {
                msgHeaderMap.put("Msg-Consumed-Fail-Count", failCount);
            }
            this.reputMsg2QueueEnd(message, channel, deliveryTag, properties);
        }
    }

    private String getMessageStr(Message message) {
        String msgStr = null;
        try {
            msgStr = this.messageBody(message);
        } catch (Exception var4) {
            log.error("warn：消息转换异常", var4);
            msgStr = "(消息转换异常)";
        }
        return msgStr;
    }

    private void discardMsg(Channel channel, long deliveryTag) throws IOException {
        channel.basicReject(deliveryTag, false);
    }

    private Integer getFailCount(BasicProperties properties) {
        Integer failCount = null;
        Map<String, Object> msgHeaderMap = properties.getHeaders();
        if (null != msgHeaderMap) {
            failCount = (Integer)msgHeaderMap.get("Msg-Consumed-Fail-Count");
        }
        if (null == failCount) {
            failCount = 0;
        }
        return failCount + 1;
    }

    private void reputMsg2QueueEnd(Message message, Channel channel, long deliveryTag, BasicProperties properties) throws IOException {
        channel.basicReject(deliveryTag, false);
        channel.basicPublish("", this.queue, false, false, properties, message.getBody());
    }

    private void reputMsg2QueueHead(Channel channel, long deliveryTag) throws IOException {
        channel.basicReject(deliveryTag, true);
    }

    private void putMsg2ExceptionQueue(Message message, Channel channel, long deliveryTag, BasicProperties properties, String exceptionQueue)throws IOException {
        if (StringUtils.isEmpty(exceptionQueue)){
            throw new MQBaseException("info_missing",this.beanName+"处理类型为 EXCEPTION_QUEUE 的异常队列为空");
        }
        channel.basicReject(deliveryTag, false);
        channel.basicPublish("", exceptionQueue, false, false, properties, message.getBody());
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



    public final void setBeanName(String name) {
        this.beanName=name;
    }


}
