package com.xiaoo.kaleido.ai.domain.workflow.model.vo;

import lombok.Getter;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;

/**
 * 执行状态值对象
 * <p>
 * 表示工作流执行的状态，是不可变的值对象
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Getter
public enum ExecutionStatus {

    /**
     * 执行中状态
     */
    RUNNING("RUNNING", "执行中"),

    /**
     * 成功状态
     */
    SUCCESS("SUCCESS", "成功"),

    /**
     * 失败状态
     */
    FAILED("FAILED", "失败");

    /**
     * -- GETTER --
     *  获取状态编码
     *
     */
    private final String code;
    /**
     * -- GETTER --
     *  获取状态描述
     *
     */
    private final String description;

    ExecutionStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码获取状态
     *
     * @param code 状态编码
     * @return 执行状态
     */
    public static ExecutionStatus fromCode(String code) {
        for (ExecutionStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw AiException.of(AiErrorCode.VALIDATION_ERROR, "无效的执行状态编码: " + code);
    }

    /**
     * 检查编码是否有效
     *
     * @param code 状态编码
     * @return 如果编码有效返回true，否则返回false
     */
    public static boolean isValid(String code) {
        for (ExecutionStatus status : values()) {
            if (status.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否为执行中状态
     *
     * @return 如果是执行中状态返回true，否则返回false
     */
    public boolean isRunning() {
        return this == RUNNING;
    }

    /**
     * 检查是否为成功状态
     *
     * @return 如果是成功状态返回true，否则返回false
     */
    public boolean isSuccess() {
        return this == SUCCESS;
    }

    /**
     * 检查是否为失败状态
     *
     * @return 如果是失败状态返回true，否则返回false
     */
    public boolean isFailed() {
        return this == FAILED;
    }

    /**
     * 检查是否为完成状态（成功或失败）
     *
     * @return 如果是完成状态返回true，否则返回false
     */
    public boolean isCompleted() {
        return this == SUCCESS || this == FAILED;
    }

    @Override
    public String toString() {
        return code;
    }
}
