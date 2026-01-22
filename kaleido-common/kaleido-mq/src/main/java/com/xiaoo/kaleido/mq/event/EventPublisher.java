package com.xiaoo.kaleido.mq.event;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * RabbitMQ事件发布
 *
 * @author ouyucheng
 * @date 2026/1/14
 */
@Slf4j
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage) {
        try {
            String messageJson = JSONUtil.toJsonStr(eventMessage);
            rabbitTemplate.convertAndSend(topic, messageJson);
            log.info("发送MQ消息 topic:{} message:{}", topic, messageJson);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSONUtil.toJsonStr(eventMessage), e);
            throw e;
        }
    }

    public void publish(String topic, String eventMessageJSON) {
        try {
            rabbitTemplate.convertAndSend(topic, eventMessageJSON);
            log.info("发送MQ消息 topic:{} message:{}", topic, eventMessageJSON);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, eventMessageJSON, e);
            throw e;
        }
    }

}
