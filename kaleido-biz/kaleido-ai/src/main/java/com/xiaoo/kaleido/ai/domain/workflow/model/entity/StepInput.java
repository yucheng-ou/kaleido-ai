package com.xiaoo.kaleido.ai.domain.workflow.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工作流步骤输入配置
 * <p>
 * 定义工作流步骤的输入来源和值
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepInput {

    /**
     * 输入类型
     */
    private InputType type;

    /**
     * 输入值（当type为STATIC时使用）
     */
    private String value;

    /**
     * 步骤ID（当type为PREVIOUS_OUTPUT时使用）
     */
    private String stepId;

    /**
     * 输入类型枚举
     */
    public enum InputType {
        /**
         * 静态值
         */
        STATIC,

        /**
         * 上一个步骤的输出
         */
        PREVIOUS_OUTPUT
    }
}
