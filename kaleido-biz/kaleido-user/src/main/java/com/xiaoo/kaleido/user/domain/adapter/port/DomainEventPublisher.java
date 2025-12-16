package com.xiaoo.kaleido.user.domain.adapter.port;

import com.xiaoo.kaleido.user.domain.event.BaseDomainEvent;

/**
 * 领域事件发布器接口（领域层）
 * 定义领域事件的发布能力
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public interface DomainEventPublisher {

    /**
     * 发布领域事件
     *
     * @param event 领域事件
     */
    void publish(BaseDomainEvent event);

    /**
     * 发布领域事件（异步）
     *
     * @param event 领域事件
     */
    void publishAsync(BaseDomainEvent event);
}
