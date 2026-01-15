package com.xiaoo.kaleido.mq.sender;

import com.xiaoo.kaleido.mq.constants.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * RabbitMQ消息发送服务
 * <p>
 * 提供多种消息发送方法，支持不同的消息模式
 *
 * @author ouyucheng
 * @date 2026/1/14
 */
@Component
@Slf4j
public class RabbitMqSender {

    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate, MessageConverter messageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
    }

    /**
     * 发送消息到默认队列（简单队列模式）
     *
     * @param message 消息内容
     */
    public void sendToDefaultQueue(Object message) {
        sendToQueue(RabbitMqConstants.QueueName.DEFAULT, message);
    }

    /**
     * 发送消息到指定队列（简单队列模式）
     *
     * @param queueName 队列名称
     * @param message   消息内容
     */
    public void sendToQueue(String queueName, Object message) {
        try {
            rabbitTemplate.convertAndSend(queueName, message);
            log.debug("消息发送成功，队列：{}，消息：{}", queueName, message);
        } catch (AmqpException e) {
            log.error("消息发送失败，队列：{}，消息：{}", queueName, message, e);
            throw e;
        }
    }

    /**
     * 发送消息到直连交换机（路由模式）
     *
     * @param exchange   交换机名称
     * @param routingKey 路由键
     * @param message    消息内容
     */
    public void sendToDirectExchange(String exchange, String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.debug("消息发送成功，交换机：{}，路由键：{}，消息：{}", exchange, routingKey, message);
        } catch (AmqpException e) {
            log.error("消息发送失败，交换机：{}，路由键：{}，消息：{}", exchange, routingKey, message, e);
            throw e;
        }
    }

    /**
     * 发送消息到主题交换机（主题模式）
     *
     * @param exchange   交换机名称
     * @param routingKey 路由键（支持通配符）
     * @param message    消息内容
     */
    public void sendToTopicExchange(String exchange, String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.debug("消息发送成功，主题交换机：{}，路由键：{}，消息：{}", exchange, routingKey, message);
        } catch (AmqpException e) {
            log.error("消息发送失败，主题交换机：{}，路由键：{}，消息：{}", exchange, routingKey, message, e);
            throw e;
        }
    }

    /**
     * 发送消息到扇出交换机（发布订阅模式）
     *
     * @param exchange 交换机名称
     * @param message  消息内容
     */
    public void sendToFanoutExchange(String exchange, Object message) {
        try {
            // 扇出交换机不需要路由键，使用空字符串
            rabbitTemplate.convertAndSend(exchange, "", message);
            log.debug("消息发送成功，扇出交换机：{}，消息：{}", exchange, message);
        } catch (AmqpException e) {
            log.error("消息发送失败，扇出交换机：{}，消息：{}", exchange, message, e);
            throw e;
        }
    }

    /**
     * 发送消息到头部交换机（头部模式）
     *
     * @param exchange 交换机名称
     * @param message  消息内容
     * @param headers  头部信息
     */
    public void sendToHeadersExchange(String exchange, Object message, Map<String, Object> headers) {
        try {
            Message amqpMessage = createMessageWithHeaders(message, headers);
            rabbitTemplate.send(exchange, "", amqpMessage);
            log.debug("消息发送成功，头部交换机：{}，消息：{}，头部：{}", exchange, message, headers);
        } catch (AmqpException e) {
            log.error("消息发送失败，头部交换机：{}，消息：{}，头部：{}", exchange, message, headers, e);
            throw e;
        }
    }

    /**
     * 发送消息并等待确认（可靠消息）
     *
     * @param exchange        交换机名称
     * @param routingKey      路由键
     * @param message         消息内容
     * @param confirmCallback 确认回调
     */
    public void sendWithConfirm(String exchange, String routingKey, Object message,
                                RabbitTemplate.ConfirmCallback confirmCallback) {
        try {
            rabbitTemplate.setConfirmCallback(confirmCallback);
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.debug("可靠消息发送成功，交换机：{}，路由键：{}，消息：{}", exchange, routingKey, message);
        } catch (AmqpException e) {
            log.error("可靠消息发送失败，交换机：{}，路由键：{}，消息：{}", exchange, routingKey, message, e);
            throw e;
        }
    }

    /**
     * 发送消息并设置消息属性
     *
     * @param exchange          交换机名称
     * @param routingKey        路由键
     * @param message           消息内容
     * @param messageProperties 消息属性
     */
    public void sendWithProperties(String exchange, String routingKey, Object message,
                                   MessageProperties messageProperties) {
        try {
            Message amqpMessage = messageConverter.toMessage(message, messageProperties);
            rabbitTemplate.send(exchange, routingKey, amqpMessage);
            log.debug("带属性消息发送成功，交换机：{}，路由键：{}，消息：{}", exchange, routingKey, message);
        } catch (AmqpException e) {
            log.error("带属性消息发送失败，交换机：{}，路由键：{}，消息：{}", exchange, routingKey, message, e);
            throw e;
        }
    }

    /**
     * 发送持久化消息
     *
     * @param exchange   交换机名称
     * @param routingKey 路由键
     * @param message    消息内容
     */
    public void sendPersistentMessage(String exchange, String routingKey, Object message) {
        MessageProperties properties = new MessageProperties();
        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        properties.setMessageId(UUID.randomUUID().toString());
        properties.setTimestamp(new Date());

        sendWithProperties(exchange, routingKey, message, properties);
    }

    /**
     * 发送带优先级的消息
     *
     * @param exchange   交换机名称
     * @param routingKey 路由键
     * @param message    消息内容
     * @param priority   优先级（0-255）
     */
    public void sendWithPriority(String exchange, String routingKey, Object message, int priority) {
        MessageProperties properties = new MessageProperties();
        properties.setPriority(priority);
        properties.setMessageId(UUID.randomUUID().toString());
        properties.setTimestamp(new Date());

        sendWithProperties(exchange, routingKey, message, properties);
    }

    /**
     * 发送带过期时间的消息
     *
     * @param exchange   交换机名称
     * @param routingKey 路由键
     * @param message    消息内容
     * @param expiration 过期时间（毫秒）
     */
    public void sendWithExpiration(String exchange, String routingKey, Object message, String expiration) {
        MessageProperties properties = new MessageProperties();
        properties.setExpiration(expiration);
        properties.setMessageId(UUID.randomUUID().toString());
        properties.setTimestamp(new Date());

        sendWithProperties(exchange, routingKey, message, properties);
    }

    /**
     * 创建带头部的消息
     *
     * @param message 消息内容
     * @param headers 头部信息
     * @return AMQP消息
     */
    private Message createMessageWithHeaders(Object message, Map<String, Object> headers) {
        Message amqpMessage = messageConverter.toMessage(message, new MessageProperties());

        MessageBuilder messageBuilder = MessageBuilder.fromMessage(amqpMessage);
        if (headers != null) {
            headers.forEach(messageBuilder::setHeader);
        }

        return messageBuilder.build();
    }
}
