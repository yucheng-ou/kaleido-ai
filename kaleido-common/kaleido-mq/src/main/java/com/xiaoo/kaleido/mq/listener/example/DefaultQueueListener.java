package com.xiaoo.kaleido.mq.listener.example;

import com.xiaoo.kaleido.mq.listener.BaseRabbitMqListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * 默认队列监听器示例
 * <p>
 * 展示如何使用BaseRabbitMqListener基类
 *
 * @author ouyucheng
 * @date 2026/1/14
 */
@Component
public class DefaultQueueListener extends BaseRabbitMqListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultQueueListener.class);

    /**
     * 处理消息的具体实现
     *
     * @param message 消息
     * @throws Exception 处理异常
     */
    @Override
    protected void processMessage(Message message) throws Exception {
        // 获取消息体
        String messageBody = getMessageBodyAsString(message);
        
        // 获取消息ID
        String messageId = message.getMessageProperties().getMessageId();
        
        // 获取队列名称
        String queueName = message.getMessageProperties().getConsumerQueue();
        
        logger.info("处理消息 - 队列：{}，消息ID：{}，消息体：{}", 
                   queueName, messageId, messageBody);
        
        // 这里可以添加具体的业务逻辑
        // 例如：解析JSON、调用服务、处理数据等
        
        // 示例：如果消息体包含"error"，则抛出异常测试重试机制
        if (messageBody.contains("error")) {
            throw new RuntimeException("测试异常：消息包含'error'关键字");
        }
        
        // 正常处理逻辑
        logger.info("消息处理完成：{}", messageBody);
    }

    /**
     * 获取监听器名称
     *
     * @return 监听器名称
     */
    @Override
    public String getListenerName() {
        return "DefaultQueueListener";
    }
}
