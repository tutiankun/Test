package com.example.tk.mq.rabbitMq;

import com.abmau.service.business.storage.amqp.global.Constant;
import com.abmau.service.business.storage.mqListener.annotation.RabbitListenerEx;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class StorageMqTestListener extends AbstractListener<String> {


    @Override
    @Autowired
    @Qualifier("omsConnectionFactory")
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        super.setConnectionFactory(connectionFactory);
    }

    @Value(Constant.LACK_INVENTORY_ORDER_QUEUE)
    public void setQueue(String queue) {
        super.setQueue(queue);
    }

    @Override
    @RabbitListenerEx(exceptionQueue = "tk")
    public void listener(String message) throws Exception {
        System.out.println("message:"+message);
        System.out.println("测试9999");
    }

    @Override
    public void setProcessTypeEnum() {
        super.setTypeEnum(ProcessTypeEnum.DIRECT_ACK);
    }

}
