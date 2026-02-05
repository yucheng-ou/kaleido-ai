package com.xiaoo.kaleido.api.ai.event;

import com.xiaoo.kaleido.api.ai.enums.WorkflowExecutionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 穿搭推荐完成事件消息
 *
 * @author ouyucheng
 * @date 2026/2/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutfitRecommendCompletedMessage {
    
    /**
     * 执行记录ID
     */
    private String executionId;
    
    /**
     * 工作流ID（固定为2）
     */
    private String workflowId;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 状态（SUCCESS/FAILED）
     */
    private WorkflowExecutionStatusEnum status;
    
    /**
     * 执行结果（JSON字符串，服装ID列表，如["2018941480068091904", "2018943237280141312"]）
     */
    private String result;
    
    /**
     * 错误信息
     */
    private String errorMessage;
}
