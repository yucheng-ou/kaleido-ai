package com.xiaoo.kaleido.ai.domain.workflow.adapter.event;

import com.xiaoo.kaleido.api.ai.event.OutfitRecommendCompletedMessage;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.mq.event.BaseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 穿搭推荐完成事件
 * 用于在穿搭推荐执行完成后通知其他服务进行相关操作
 * 
 * @author ouyucheng
 * @date 2026/2/5
 */
@Component
@RequiredArgsConstructor
public class WorkflowExecutionCompletedEvent extends BaseEvent<OutfitRecommendCompletedMessage> {

    @Value("${spring.rabbitmq.topic.outfit-recommend-completed}")
    private String topic;

    @Override
    public EventMessage<OutfitRecommendCompletedMessage> buildEventMessage(OutfitRecommendCompletedMessage data) {
        return EventMessage.<OutfitRecommendCompletedMessage>builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .timestamp(new Date())
                .data(data)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }
}
