package com.xiaoo.kaleido.user.infrastructure.adapter.port;

import com.xiaoo.kaleido.user.domain.adapter.port.DomainEventPublisher;
import com.xiaoo.kaleido.user.domain.event.BaseDomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 领域事件发布器实现（基础设施层）
 * 使用Spring ApplicationEventPublisher发布领域事件
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(BaseDomainEvent event) {
        log.info("发布领域事件: {} - {}", event.getEventType(), event.getDescription());
        applicationEventPublisher.publishEvent(event);
    }

    @Async
    @Override
    public void publishAsync(BaseDomainEvent event) {
        log.info("异步发布领域事件: {} - {}", event.getEventType(), event.getDescription());
        applicationEventPublisher.publishEvent(event);
    }
}
