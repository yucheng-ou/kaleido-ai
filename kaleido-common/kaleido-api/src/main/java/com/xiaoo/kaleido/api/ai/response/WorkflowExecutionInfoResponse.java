package com.xiaoo.kaleido.api.ai.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 工作流执行信息响应
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowExecutionInfoResponse extends BaseResp {

    /**
     * 执行ID
     */
    private String executionId;

    /**
     * 工作流ID
     */
    private String workflowId;

    /**
     * 输入数据
     */
    private String inputData;

    /**
     * 输出数据
     */
    private String outputData;

    /**
     * 执行状态
     */
    private String status;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 开始时间
     */
    private Date startAt;

    /**
     * 结束时间
     */
    private Date endAt;
}
