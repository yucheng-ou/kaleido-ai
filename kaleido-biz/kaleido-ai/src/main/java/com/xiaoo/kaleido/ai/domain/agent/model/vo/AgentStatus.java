package com.xiaoo.kaleido.ai.domain.agent.model.vo;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;

/**
 * Agent状态值对象
 * <p>
 * 表示Agent的状态，是不可变的值对象
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public enum AgentStatus {

    /**
     * 正常状态
     */
    NORMAL("NORMAL", "正常"),

    /**
     * 禁用状态
     */
    DISABLED("DISABLED", "禁用");

    private final String code;
    private final String description;

    AgentStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取状态编码
     *
     * @return 状态编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取状态描述
     *
     * @return 状态描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 根据编码获取状态
     *
     * @param code 状态编码
     * @return Agent状态
     */
    public static AgentStatus fromCode(String code) {
        if (StrUtil.isBlank(code)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "Agent状态编码不能为空");
        }
        
        for (AgentStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw AiException.of(AiErrorCode.VALIDATION_ERROR, "无效的Agent状态编码: " + code);
    }

    /**
     * 检查编码是否有效
     *
     * @param code 状态编码
     * @return 如果编码有效返回true，否则返回false
     */
    public static boolean isValid(String code) {
        if (StrUtil.isBlank(code)) {
            return false;
        }
        
        for (AgentStatus status : values()) {
            if (status.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否为正常状态
     *
     * @return 如果是正常状态返回true，否则返回false
     */
    public boolean isNormal() {
        return this == NORMAL;
    }

    /**
     * 检查是否为禁用状态
     *
     * @return 如果是禁用状态返回true，否则返回false
     */
    public boolean isDisabled() {
        return this == DISABLED;
    }

    @Override
    public String toString() {
        return code;
    }
}
