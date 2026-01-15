package com.xiaoo.kaleido.mq.config;

import com.xiaoo.kaleido.mq.support.QueueDeclarer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * RabbitMQ自动配置类
 * <p>
 * 配置RabbitMQ连接工厂、模板、消息转换器等核心组件
 * 使用Spring Boot的标准配置（spring.rabbitmq.*）
 *
 * @author ouyucheng
 * @version 1.0
 * @date 2026/1/14
 */
@AutoConfiguration
@Import({QueueDeclarer.class})
public class RabbitMqAutoConfiguration {

    /**
     * 创建RabbitAdmin
     *
     * @param connectionFactory 连接工厂（由Spring Boot自动配置）
     * @return RabbitAdmin
     */
    @Bean
    @ConditionalOnMissingBean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * 创建消息转换器（JSON格式）
     *
     * @return 消息转换器
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setCreateMessageIds(true); // 创建消息ID
        return converter;
    }

    /**
     * 创建RabbitTemplate
     *
     * @param connectionFactory 连接工厂
     * @param messageConverter 消息转换器
     * @return RabbitTemplate
     */
    @Bean
    @ConditionalOnMissingBean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        
        // 消息转换器
        rabbitTemplate.setMessageConverter(messageConverter);
        
        // 发布者确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                // 消息发送成功
            } else {
                // 消息发送失败
            }
        });
        
        // 返回消息回调（当消息无法路由到队列时）
        rabbitTemplate.setReturnsCallback(returned -> {
            // 处理无法路由的消息
        });
        
        // 强制启用发布者确认和返回
        rabbitTemplate.setMandatory(true);
        
        return rabbitTemplate;
    }

    /**
     * 创建RabbitListener容器工厂
     *
     * @param connectionFactory 连接工厂
     * @param messageConverter 消息转换器
     * @return 监听器容器工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        
        // 并发配置
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        
        // 预取数量
        factory.setPrefetchCount(10);
        
        // 确认模式：手动确认
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        
        return factory;
    }

    /**
     * 声明默认队列和交换机（示例）
     *
     * @param rabbitAdmin RabbitAdmin
     * @param queueDeclarer 队列声明工具
     * @return 绑定的队列
     */
    @Bean
    @ConditionalOnMissingBean(name = "defaultQueue")
    public Queue defaultQueue(RabbitAdmin rabbitAdmin, QueueDeclarer queueDeclarer) {
        // 声明默认队列
        Queue queue = queueDeclarer.declareDefaultQueue();
        rabbitAdmin.declareQueue(queue);
        
        // 声明默认直连交换机
        DirectExchange exchange = queueDeclarer.declareDefaultDirectExchange();
        rabbitAdmin.declareExchange(exchange);
        
        // 绑定队列到交换机
        Binding binding = queueDeclarer.bindQueueToDirectExchange(
                queue, exchange, "kaleido.default.routing.key");
        rabbitAdmin.declareBinding(binding);
        
        return queue;
    }

    /**
     * 声明死信队列和交换机
     *
     * @param rabbitAdmin RabbitAdmin
     * @param queueDeclarer 队列声明工具
     * @return 死信队列
     */
    @Bean
    @ConditionalOnMissingBean(name = "deadLetterQueue")
    public Queue deadLetterQueue(RabbitAdmin rabbitAdmin, 
                                 QueueDeclarer queueDeclarer) {
        
        // 声明死信队列
        Queue deadLetterQueue = queueDeclarer.declareDeadLetterQueue();
        rabbitAdmin.declareQueue(deadLetterQueue);
        
        // 声明死信交换机
        DirectExchange deadLetterExchange = queueDeclarer.declareDeadLetterExchange();
        rabbitAdmin.declareExchange(deadLetterExchange);
        
        // 绑定死信队列到死信交换机
        Binding binding = queueDeclarer.bindDeadLetterQueue(deadLetterQueue, deadLetterExchange);
        rabbitAdmin.declareBinding(binding);
        
        return deadLetterQueue;
    }

    /**
     * 声明工作队列（示例）
     *
     * @param rabbitAdmin RabbitAdmin
     * @param queueDeclarer 队列声明工具
     * @return 工作队列
     */
    @Bean
    @ConditionalOnMissingBean(name = "workQueue")
    public Queue workQueue(RabbitAdmin rabbitAdmin, QueueDeclarer queueDeclarer) {
        Queue queue = queueDeclarer.declareWorkQueue();
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    /**
     * 声明主题交换机和队列（示例）
     *
     * @param rabbitAdmin RabbitAdmin
     * @param queueDeclarer 队列声明工具
     * @return 主题队列1
     */
    @Bean
    @ConditionalOnMissingBean(name = "topicQueue1")
    public Queue topicQueue1(RabbitAdmin rabbitAdmin, QueueDeclarer queueDeclarer) {
        // 声明主题队列1
        Queue queue = queueDeclarer.declareTopicQueue1();
        rabbitAdmin.declareQueue(queue);
        
        // 声明主题交换机
        TopicExchange exchange = queueDeclarer.declareDefaultTopicExchange();
        rabbitAdmin.declareExchange(exchange);
        
        // 绑定队列到主题交换机
        Binding binding = queueDeclarer.bindQueueToTopicExchange(
                queue, exchange, "kaleido.topic.*");
        rabbitAdmin.declareBinding(binding);
        
        return queue;
    }
}
