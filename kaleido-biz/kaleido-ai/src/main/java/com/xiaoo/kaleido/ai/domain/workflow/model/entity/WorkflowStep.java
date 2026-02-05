package com.xiaoo.kaleido.ai.domain.workflow.model.entity;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工作流步骤定义
 * <p>
 * 定义工作流中的一个执行步骤
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStep {

    /**
     * 步骤唯一标识
     */
    private String id;

    /**
     * 步骤名称
     */
    private String name;

    /**
     * 要调用的Agent ID
     */
    private String agentId;

    /**
     * 步骤执行顺序（从1开始）
     */
    private Integer order;

    /**
     * 验证步骤配置是否有效
     *
     * @return 如果步骤配置有效返回true，否则返回false
     */
    public boolean isValid() {
        if (StrUtil.isBlank(id)) {
            return false;
        }
        return !StrUtil.isBlank(agentId);
    }
}
