package com.example.tk.mq.rabbitMq;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *消息失败后处理策略
 */
@Getter
@AllArgsConstructor
public enum ProcessTypeEnum {

    DIRECT_ACK(1,"直接ACK"),
    EXCEPTION_QUEUE(2, "直接放入异常队列"),
    REPUT_QUEUE_HEAD(3, "重回队列头部"),
    REPUT_QUEUE_TAIL(4, "重回队列尾部"),
    REPUT_QUEUE_COUNT_DISCARD(5, "放回队尾，消费三次失败后丢弃"),
    REPUT_QUEUE_COUNT_EXCEPTION_QUEUE(6, "放回队尾，消费三次失败后放入异常队列");

    private Integer code;
    private String remark;



}
