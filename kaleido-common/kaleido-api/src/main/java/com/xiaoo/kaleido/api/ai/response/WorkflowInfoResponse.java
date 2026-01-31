package com.xiaoo.kaleido.api.ai.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 工作流信息响应
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowInfoResponse extends BaseResp {

    /**
     * 工作流ID
     */
    private String workflowId;

    /**
     * 工作流编码
     */
    private String code;

    /**
     * 工作流名称
     */
    private String name;

    /**
     * 工作流描述
     */
    private String description;

    /**
     * 工作流定义
     */
    private String definition;

    /**
     * 工作流状态
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
