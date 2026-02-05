package com.xiaoo.kaleido.ai.infrastructure.adapter.event;

import com.xiaoo.kaleido.ai.domain.workflow.adapter.event.IWorkflowEventPublisher;
import com.xiaoo.kaleido.ai.domain.workflow.adapter.event.WorkflowExecutionCompletedEvent;
import com.xiaoo.kaleido.api.ai.enums.WorkflowExecutionStatusEnum;
import com.xiaoo.kaleido.api.ai.event.OutfitRecommendCompletedMessage;
import com.xiaoo.kaleido.mq.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 穿搭推荐事件发布器实现
 * 基础设施层实现，负责发布穿搭推荐相关事件到消息队列
 *
 * @author ouyucheng
 * @date 2026/2/5
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WorkflowEventPublisherImpl implements IWorkflowEventPublisher {

    private final EventPublisher eventPublisher;
    private final WorkflowExecutionCompletedEvent workflowExecutionCompletedEvent;

    @Override
    public void publishOutfitRecommendCompletedEvent(
            String executionId,
            String workflowId,
            String userId,
            WorkflowExecutionStatusEnum status,
            String result,
            String errorMessage
    ) {
        try {
            OutfitRecommendCompletedMessage apiMessage = OutfitRecommendCompletedMessage.builder()
                    .executionId(executionId)
                    .workflowId(workflowId)
                    .userId(userId)
                    .status(status)
                    .result(result)
                    .errorMessage(errorMessage)
                    .build();

            eventPublisher.publish(
                    workflowExecutionCompletedEvent.topic(),
                    workflowExecutionCompletedEvent.buildEventMessage(apiMessage)
            );

            log.info("穿搭推荐完成事件发布成功，执行记录ID: {}, 工作流ID: {}, 用户ID: {}, 状态: {}",
                    executionId, workflowId, userId, status);
        } catch (Exception e) {
            log.error("穿搭推荐完成事件发布失败，执行记录ID: {}, 工作流ID: {}, 用户ID: {}, 错误: {}",
                    executionId, workflowId, userId, e.getMessage(), e);
            throw e;
        }
    }
}
