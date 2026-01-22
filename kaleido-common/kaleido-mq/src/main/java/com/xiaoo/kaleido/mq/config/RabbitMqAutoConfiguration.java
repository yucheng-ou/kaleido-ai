package com.xiaoo.kaleido.mq.config;

import com.xiaoo.kaleido.mq.event.EventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


/**
 * RabbitMQ自动配置类
 *
 * @author ouyucheng
 * @date 2026/1/14
 */
@AutoConfiguration
@Import(EventPublisher.class)
public class RabbitMqAutoConfiguration {

    @Bean
    public EventPublisher eventPublisher(RabbitTemplate rabbitTemplate) {
        return new EventPublisher(rabbitTemplate);
    }
}
