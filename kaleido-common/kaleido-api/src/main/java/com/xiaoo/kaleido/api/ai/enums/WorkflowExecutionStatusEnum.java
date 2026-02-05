package com.xiaoo.kaleido.api.ai.enums;

import lombok.Getter;

/**
 * 工作流执行状态枚举
 *
 * @author ouyucheng
 * @date 2026/2/4
 */
@Getter
public enum WorkflowExecutionStatusEnum {

    /**
     * 成功 - 工作流执行成功
     */
    SUCCESS(0, "成功"),

    /**
     * 失败 - 工作流执行失败
     */
    FAILED(1, "失败");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态描述
     */
    private final String description;

    WorkflowExecutionStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据状态码获取枚举
     *
     * @param code 状态码
     * @return 工作流执行状态枚举
     */
    public static WorkflowExecutionStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (WorkflowExecutionStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
