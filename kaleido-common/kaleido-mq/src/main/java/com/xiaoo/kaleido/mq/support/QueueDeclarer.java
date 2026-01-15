package com.xiaoo.kaleido.mq.support;

import com.xiaoo.kaleido.mq.constants.RabbitMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列声明工具类
 * <p>
 * 用于声明RabbitMQ的队列、交换机和绑定关系
 * 支持死信队列配置
 *
 * @author ouyucheng
 * @date 2026/1/14
 */
@Component
public class QueueDeclarer {

    // 默认配置
    private static final boolean DEFAULT_DURABLE = true;
    private static final boolean DEFAULT_AUTO_DELETE = false;
    private static final boolean DEFAULT_DEAD_LETTER_ENABLED = true;
    private static final String DEFAULT_DEAD_LETTER_EXCHANGE = "kaleido.dlx.exchange";
    private static final String DEFAULT_DEAD_LETTER_ROUTING_KEY = "kaleido.dlx.routing.key";

    public QueueDeclarer() {
        // 无参构造函数
    }

    /**
     * 声明默认队列
     *
     * @return 默认队列
     */
    public Queue declareDefaultQueue() {
        return declareQueue(RabbitMqConstants.QueueName.DEFAULT);
    }

    /**
     * 声明工作队列
     *
     * @return 工作队列
     */
    public Queue declareWorkQueue() {
        return declareQueue(RabbitMqConstants.QueueName.WORK_QUEUE);
    }

    /**
     * 声明发布订阅队列1
     *
     * @return 发布订阅队列1
     */
    public Queue declarePubSubQueue1() {
        return declareQueue(RabbitMqConstants.QueueName.PUB_SUB_QUEUE_1);
    }

    /**
     * 声明发布订阅队列2
     *
     * @return 发布订阅队列2
     */
    public Queue declarePubSubQueue2() {
        return declareQueue(RabbitMqConstants.QueueName.PUB_SUB_QUEUE_2);
    }

    /**
     * 声明路由队列1
     *
     * @return 路由队列1
     */
    public Queue declareRoutingQueue1() {
        return declareQueue(RabbitMqConstants.QueueName.ROUTING_QUEUE_1);
    }

    /**
     * 声明路由队列2
     *
     * @return 路由队列2
     */
    public Queue declareRoutingQueue2() {
        return declareQueue(RabbitMqConstants.QueueName.ROUTING_QUEUE_2);
    }

    /**
     * 声明主题队列1
     *
     * @return 主题队列1
     */
    public Queue declareTopicQueue1() {
        return declareQueue(RabbitMqConstants.QueueName.TOPIC_QUEUE_1);
    }

    /**
     * 声明主题队列2
     *
     * @return 主题队列2
     */
    public Queue declareTopicQueue2() {
        return declareQueue(RabbitMqConstants.QueueName.TOPIC_QUEUE_2);
    }

    /**
     * 声明死信队列
     *
     * @return 死信队列
     */
    public Queue declareDeadLetterQueue() {
        return QueueBuilder.durable(RabbitMqConstants.QueueName.DEAD_LETTER_QUEUE)
                .build();
    }

    /**
     * 声明队列
     *
     * @param queueName 队列名称
     * @return 队列
     */
    public Queue declareQueue(String queueName) {
        return declareQueue(queueName, DEFAULT_DEAD_LETTER_ENABLED);
    }

    /**
     * 声明队列（可配置死信队列）
     *
     * @param queueName 队列名称
     * @param enableDeadLetter 是否启用死信队列
     * @return 队列
     */
    public Queue declareQueue(String queueName, boolean enableDeadLetter) {
        QueueBuilder queueBuilder = QueueBuilder.durable(queueName);

        // 配置死信队列
        if (enableDeadLetter) {
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("x-dead-letter-exchange", DEFAULT_DEAD_LETTER_EXCHANGE);
            arguments.put("x-dead-letter-routing-key", DEFAULT_DEAD_LETTER_ROUTING_KEY);
            queueBuilder.withArguments(arguments);
        }

        return queueBuilder.build();
    }

    /**
     * 声明默认直连交换机
     *
     * @return 直连交换机
     */
    public DirectExchange declareDefaultDirectExchange() {
        return new DirectExchange(RabbitMqConstants.ExchangeName.DEFAULT_DIRECT,
                DEFAULT_DURABLE,
                DEFAULT_AUTO_DELETE);
    }

    /**
     * 声明默认主题交换机
     *
     * @return 主题交换机
     */
    public TopicExchange declareDefaultTopicExchange() {
        return new TopicExchange(RabbitMqConstants.ExchangeName.DEFAULT_TOPIC,
                DEFAULT_DURABLE,
                DEFAULT_AUTO_DELETE);
    }

    /**
     * 声明默认扇出交换机
     *
     * @return 扇出交换机
     */
    public FanoutExchange declareDefaultFanoutExchange() {
        return new FanoutExchange(RabbitMqConstants.ExchangeName.DEFAULT_FANOUT,
                DEFAULT_DURABLE,
                DEFAULT_AUTO_DELETE);
    }

    /**
     * 声明默认头部交换机
     *
     * @return 头部交换机
     */
    public HeadersExchange declareDefaultHeadersExchange() {
        return new HeadersExchange(RabbitMqConstants.ExchangeName.DEFAULT_HEADERS,
                DEFAULT_DURABLE,
                DEFAULT_AUTO_DELETE);
    }

    /**
     * 声明死信交换机
     *
     * @return 死信交换机
     */
    public DirectExchange declareDeadLetterExchange() {
        return new DirectExchange(RabbitMqConstants.ExchangeName.DEAD_LETTER_EXCHANGE,
                DEFAULT_DURABLE,
                DEFAULT_AUTO_DELETE);
    }

    /**
     * 创建绑定关系 - 队列绑定到直连交换机
     *
     * @param queue    队列
     * @param exchange 交换机
     * @param routingKey 路由键
     * @return 绑定
     */
    public Binding bindQueueToDirectExchange(Queue queue, DirectExchange exchange, String routingKey) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    /**
     * 创建绑定关系 - 队列绑定到主题交换机
     *
     * @param queue    队列
     * @param exchange 交换机
     * @param routingKey 路由键
     * @return 绑定
     */
    public Binding bindQueueToTopicExchange(Queue queue, TopicExchange exchange, String routingKey) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    /**
     * 创建绑定关系 - 队列绑定到扇出交换机
     *
     * @param queue    队列
     * @param exchange 交换机
     * @return 绑定
     */
    public Binding bindQueueToFanoutExchange(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * 创建绑定关系 - 队列绑定到头部交换机
     *
     * @param queue    队列
     * @param exchange 交换机
     * @param headerKey 头部键
     * @param headerValue 头部值
     * @return 绑定
     */
    public Binding bindQueueToHeadersExchange(Queue queue, HeadersExchange exchange, String headerKey, Object headerValue) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(headerKey, headerValue);
        return BindingBuilder.bind(queue).to(exchange).whereAll(headers).match();
    }

    /**
     * 创建死信队列绑定
     *
     * @param deadLetterQueue 死信队列
     * @param deadLetterExchange 死信交换机
     * @return 绑定
     */
    public Binding bindDeadLetterQueue(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with(DEFAULT_DEAD_LETTER_ROUTING_KEY);
    }
}
