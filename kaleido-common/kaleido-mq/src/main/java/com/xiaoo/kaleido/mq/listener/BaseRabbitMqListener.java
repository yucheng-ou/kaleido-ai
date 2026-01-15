package com.xiaoo.kaleido.mq.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * RabbitMQ消息监听器基类
 * <p>
 * 提供通用的消息处理模板，支持自动ACK/NACK处理
 * 子类只需实现具体的消息处理逻辑
 *
 * @author ouyucheng
 * @version 1.0
 * @date 2026/1/14
 */
public abstract class BaseRabbitMqListener implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(BaseRabbitMqListener.class);

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * 处理消息的核心方法
     *
     * @param message AMQP消息
     * @param channel RabbitMQ通道
     * @throws Exception 处理异常
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String queueName = message.getMessageProperties().getConsumerQueue();
        
        try {
            logger.debug("收到消息，队列：{}，消息ID：{}", queueName, 
                        message.getMessageProperties().getMessageId());
            
            // 处理消息
            processMessage(message);
            
            // 处理成功，手动确认消息
            channel.basicAck(deliveryTag, false);
            logger.debug("消息处理成功，已确认，队列：{}，消息ID：{}", queueName, 
                        message.getMessageProperties().getMessageId());
            
        } catch (Exception e) {
            logger.error("消息处理失败，队列：{}，消息ID：{}", queueName, 
                        message.getMessageProperties().getMessageId(), e);
            
            // 处理失败，根据重试策略决定是否重新入队
            handleMessageFailure(message, channel, deliveryTag, e);
        }
    }

    /**
     * 处理消息失败的情况
     *
     * @param message     消息
     * @param channel     通道
     * @param deliveryTag 投递标签
     * @param exception   异常
     */
    protected void handleMessageFailure(Message message, Channel channel, 
                                       long deliveryTag, Exception exception) {
        try {
            // 获取重试次数
            Integer retryCount = getRetryCount(message);
            int maxRetries = getMaxRetries();
            
            if (retryCount < maxRetries) {
                // 重试次数未达到上限，重新入队
                channel.basicNack(deliveryTag, false, true);
                logger.warn("消息处理失败，重新入队，重试次数：{}，最大重试次数：{}", 
                           retryCount, maxRetries);
            } else {
                // 重试次数达到上限，拒绝消息并进入死信队列
                channel.basicNack(deliveryTag, false, false);
                logger.error("消息处理失败，重试次数已达上限，进入死信队列，重试次数：{}", retryCount);
                
                // 可以记录到死信日志或发送通知
                logDeadLetterMessage(message, exception);
            }
        } catch (IOException e) {
            logger.error("消息拒绝失败，队列：{}，消息ID：{}", 
                        message.getMessageProperties().getConsumerQueue(),
                        message.getMessageProperties().getMessageId(), e);
        }
    }

    /**
     * 获取消息的重试次数
     *
     * @param message 消息
     * @return 重试次数
     */
    protected Integer getRetryCount(Message message) {
        // 从消息头中获取重试次数
        Object retryCountObj = message.getMessageProperties().getHeader("retry-count");
        if (retryCountObj instanceof Integer) {
            return (Integer) retryCountObj;
        }
        return 0;
    }

    /**
     * 获取最大重试次数
     * 子类可以重写此方法以提供自定义的重试策略
     *
     * @return 最大重试次数
     */
    protected int getMaxRetries() {
        return 3; // 默认最大重试3次
    }

    /**
     * 记录死信消息
     *
     * @param message   消息
     * @param exception 异常
     */
    protected void logDeadLetterMessage(Message message, Exception exception) {
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        logger.error("死信消息记录 - 队列：{}，消息ID：{}，消息体：{}，异常：{}",
                    message.getMessageProperties().getConsumerQueue(),
                    message.getMessageProperties().getMessageId(),
                    messageBody,
                    exception.getMessage());
    }

    /**
     * 将消息体转换为指定类型
     *
     * @param message 消息
     * @param clazz   目标类型
     * @param <T>     类型参数
     * @return 转换后的对象
     * @throws IOException JSON解析异常
     */
    protected <T> T convertMessageBody(Message message, Class<T> clazz) throws IOException {
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        return objectMapper.readValue(messageBody, clazz);
    }

    /**
     * 获取消息体字符串
     *
     * @param message 消息
     * @return 消息体字符串
     */
    protected String getMessageBodyAsString(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }

    /**
     * 处理消息的抽象方法
     * 子类必须实现此方法来处理具体的业务逻辑
     *
     * @param message 消息
     * @throws Exception 处理异常
     */
    protected abstract void processMessage(Message message) throws Exception;

    /**
     * 获取监听器名称
     * 用于日志记录和监控
     *
     * @return 监听器名称
     */
    public abstract String getListenerName();
}
