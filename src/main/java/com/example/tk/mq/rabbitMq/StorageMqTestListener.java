package com.example.tk.mq.rabbitMq;

import com.abmau.service.business.storage.amqp.global.Constant;
import com.abmau.service.business.storage.api.param.dto.transfer.LackInventoryOrderDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StorageMqTestListener extends AbstractListener<LackInventoryOrderDTO> {

    @RabbitListener(
            queues = Constant.LACK_INVENTORY_ORDER_QUEUE
            , containerFactory = "omsContainerFactory")
    @Override
    public void listener(LackInventoryOrderDTO lackInventoryOrderDTO) throws Exception {
        System.out.println("测试9999");
    }

    @Override
    public void setProcessTypeEnum() {
        super.setTypeEnum(ProcessTypeEnum.DIRECT_ACK);
    }


}
