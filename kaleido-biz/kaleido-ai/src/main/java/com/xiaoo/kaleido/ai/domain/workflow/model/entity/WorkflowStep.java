package com.xiaoo.kaleido.ai.domain.workflow.model.entity;

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
     * 步骤输入配置
     */
    private StepInput input;

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
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        if (agentId == null || agentId.trim().isEmpty()) {
            return false;
        }
        if (input == null) {
            return false;
        }
        return true;
    }

    /**
     * 获取步骤输入类型
     *
     * @return 步骤输入类型
     */
    public StepInput.InputType getInputType() {
        return input != null ? input.getType() : null;
    }

    /**
     * 检查是否为静态输入
     *
     * @return 如果输入类型为STATIC返回true，否则返回false
     */
    public boolean isStaticInput() {
        return StepInput.InputType.STATIC.equals(getInputType());
    }

    /**
     * 检查是否为上一个步骤的输出
     *
     * @return 如果输入类型为PREVIOUS_OUTPUT返回true，否则返回false
     */
    public boolean isPreviousOutputInput() {
        return StepInput.InputType.PREVIOUS_OUTPUT.equals(getInputType());
    }
}
