package com.xiaoo.kaleido.mq.constants;

/**
 * RabbitMQ常量类
 * <p>
 * 定义RabbitMQ相关的常量，包括交换机类型、队列名称、路由键等
 *
 * @author ouyucheng
 * @version 1.0
 * @date 2026/1/14
 */
public class RabbitMqConstants {

    private RabbitMqConstants() {
        // 防止实例化
    }

    /**
     * 交换机类型常量
     */
    public static class ExchangeType {
        /**
         * 直连交换机
         */
        public static final String DIRECT = "direct";

        /**
         * 主题交换机
         */
        public static final String TOPIC = "topic";

        /**
         * 扇出交换机
         */
        public static final String FANOUT = "fanout";

        /**
         * 头部交换机
         */
        public static final String HEADERS = "headers";

        private ExchangeType() {
            // 防止实例化
        }
    }

    /**
     * 默认队列名称
     */
    public static class QueueName {
        /**
         * 默认队列
         */
        public static final String DEFAULT = "kaleido.default.queue";

        /**
         * 工作队列
         */
        public static final String WORK_QUEUE = "kaleido.work.queue";

        /**
         * 发布订阅队列1
         */
        public static final String PUB_SUB_QUEUE_1 = "kaleido.pubsub.queue1";

        /**
         * 发布订阅队列2
         */
        public static final String PUB_SUB_QUEUE_2 = "kaleido.pubsub.queue2";

        /**
         * 路由队列1
         */
        public static final String ROUTING_QUEUE_1 = "kaleido.routing.queue1";

        /**
         * 路由队列2
         */
        public static final String ROUTING_QUEUE_2 = "kaleido.routing.queue2";

        /**
         * 主题队列1
         */
        public static final String TOPIC_QUEUE_1 = "kaleido.topic.queue1";

        /**
         * 主题队列2
         */
        public static final String TOPIC_QUEUE_2 = "kaleido.topic.queue2";

        /**
         * 死信队列
         */
        public static final String DEAD_LETTER_QUEUE = "kaleido.dlx.queue";

        private QueueName() {
            // 防止实例化
        }
    }

    /**
     * 默认交换机名称
     */
    public static class ExchangeName {
        /**
         * 默认直连交换机
         */
        public static final String DEFAULT_DIRECT = "kaleido.direct.exchange";

        /**
         * 默认主题交换机
         */
        public static final String DEFAULT_TOPIC = "kaleido.topic.exchange";

        /**
         * 默认扇出交换机
         */
        public static final String DEFAULT_FANOUT = "kaleido.fanout.exchange";

        /**
         * 默认头部交换机
         */
        public static final String DEFAULT_HEADERS = "kaleido.headers.exchange";

        /**
         * 死信交换机
         */
        public static final String DEAD_LETTER_EXCHANGE = "kaleido.dlx.exchange";

        private ExchangeName() {
            // 防止实例化
        }
    }

    /**
     * 路由键常量
     */
    public static class RoutingKey {
        /**
         * 默认路由键
         */
        public static final String DEFAULT = "kaleido.default.routing.key";

        /**
         * 错误路由键
         */
        public static final String ERROR = "kaleido.error.routing.key";

        /**
         * 信息路由键
         */
        public static final String INFO = "kaleido.info.routing.key";

        /**
         * 警告路由键
         */
        public static final String WARN = "kaleido.warn.routing.key";

        /**
         * 调试路由键
         */
        public static final String DEBUG = "kaleido.debug.routing.key";

        /**
         * 死信路由键
         */
        public static final String DEAD_LETTER = "kaleido.dlx.routing.key";

        /**
         * 通配符路由键 - 所有
         */
        public static final String WILDCARD_ALL = "#";

        /**
         * 通配符路由键 - 单个单词
         */
        public static final String WILDCARD_SINGLE = "*";

        private RoutingKey() {
            // 防止实例化
        }
    }

    /**
     * 消息头常量
     */
    public static class Header {
        /**
         * 消息ID
         */
        public static final String MESSAGE_ID = "message-id";

        /**
         * 消息类型
         */
        public static final String MESSAGE_TYPE = "message-type";

        /**
         * 消息时间戳
         */
        public static final String TIMESTAMP = "timestamp";

        /**
         * 重试次数
         */
        public static final String RETRY_COUNT = "retry-count";

        /**
         * 来源服务
         */
        public static final String SOURCE_SERVICE = "source-service";

        /**
         * 目标服务
         */
        public static final String TARGET_SERVICE = "target-service";

        private Header() {
            // 防止实例化
        }
    }

    /**
     * 消息属性常量
     */
    public static class MessageProperty {
        /**
         * 内容类型 - JSON
         */
        public static final String CONTENT_TYPE_JSON = "application/json";

        /**
         * 内容编码 - UTF-8
         */
        public static final String CONTENT_ENCODING_UTF8 = "UTF-8";

        /**
         * 消息优先级 - 默认
         */
        public static final int PRIORITY_DEFAULT = 0;

        /**
         * 消息优先级 - 高
         */
        public static final int PRIORITY_HIGH = 10;

        /**
         * 消息优先级 - 低
         */
        public static final int PRIORITY_LOW = 1;

        private MessageProperty() {
            // 防止实例化
        }
    }
}
