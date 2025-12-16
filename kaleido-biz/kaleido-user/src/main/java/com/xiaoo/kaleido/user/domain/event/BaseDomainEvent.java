package com.xiaoo.kaleido.user.domain.event;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 领域事件基类
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Getter
public abstract class BaseDomainEvent {

    /**
     * 事件ID
     */
    private final String eventId;

    /**
     * 事件发生时间
     */
    private final LocalDateTime occurredAt;

    /**
     * 聚合根ID
     */
    private final String aggregateId;

    /**
     * 聚合根类型
     */
    private final String aggregateType;

    /**
     * 事件版本
     */
    private final int version;

    protected BaseDomainEvent(String aggregateId, String aggregateType) {
        this.eventId = UUID.randomUUID().toString();
        this.occurredAt = LocalDateTime.now();
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.version = 1;
    }

    /**
     * 获取事件类型
     */
    public abstract String getEventType();

    /**
     * 获取事件描述
     */
    public abstract String getDescription();
}
