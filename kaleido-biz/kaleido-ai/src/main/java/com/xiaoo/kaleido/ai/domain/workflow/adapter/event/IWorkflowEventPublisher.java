package com.xiaoo.kaleido.ai.domain.workflow.adapter.event;

import com.xiaoo.kaleido.api.ai.enums.WorkflowExecutionStatusEnum;

/**
 * 穿搭推荐事件发布器接口
 * 领域层接口，定义穿搭推荐相关事件的发布能力
 *
 * @author ouyucheng
 * @date 2026/2/5
 */
public interface IWorkflowEventPublisher {

    /**
     * 发布穿搭推荐完成事件
     *
     * @param executionId 执行记录ID
     * @param workflowId 工作流ID
     * @param userId 用户ID
     * @param status 执行状态
     * @param result 执行结果（JSON字符串，服装ID列表）
     * @param errorMessage 错误信息
     */
    void publishOutfitRecommendCompletedEvent(
            String executionId,
            String workflowId,
            String userId,
            WorkflowExecutionStatusEnum status,
            String result,
            String errorMessage
    );
}
