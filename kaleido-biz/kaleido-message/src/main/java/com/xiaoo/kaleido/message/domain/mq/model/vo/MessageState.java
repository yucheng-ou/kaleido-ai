package com.xiaoo.kaleido.message.domain.mq.model.vo;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.message.types.exception.MessageException;
import com.xiaoo.kaleido.message.types.exception.MessageErrorCode;

/**
 * 消息状态值对象
 * <p>
 * 表示MQ消息的状态，是不可变的值对象
 * 状态包括：create-创建、completed-完成、fail-失败
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
public enum MessageState {

    /**
     * 创建状态
     */
    CREATE("CREATE", "创建"),

    /**
     * 完成状态
     */
    COMPLETED("COMPLETED", "完成"),

    /**
     * 失败状态
     */
    FAIL("FAIL", "失败");

    private final String code;
    private final String description;

    MessageState(String code, String description) {
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
     * @return 消息状态
     */
    public static MessageState fromCode(String code) {
        if (StrUtil.isBlank(code)) {
            throw MessageException.of(MessageErrorCode.PARAM_NOT_NULL, "消息状态编码不能为空");
        }
        
        for (MessageState state : values()) {
            if (state.code.equals(code)) {
                return state;
            }
        }
        throw MessageException.of(MessageErrorCode.PARAM_FORMAT_ERROR, "无效的消息状态编码: " + code);
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
        
        for (MessageState state : values()) {
            if (state.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否为创建状态
     *
     * @return 如果是创建状态返回true，否则返回false
     */
    public boolean isCreate() {
        return this == CREATE;
    }

    /**
     * 检查是否为完成状态
     *
     * @return 如果是完成状态返回true，否则返回false
     */
    public boolean isCompleted() {
        return this == COMPLETED;
    }

    /**
     * 检查是否为失败状态
     *
     * @return 如果是失败状态返回true，否则返回false
     */
    public boolean isFail() {
        return this == FAIL;
    }

    /**
     * 检查是否为终态（已完成或失败）
     *
     * @return 如果是终态返回true，否则返回false
     */
    public boolean isFinalState() {
        return isCompleted() || isFail();
    }

    /**
     * 检查是否为处理中状态（创建状态）
     *
     * @return 如果是处理中状态返回true，否则返回false
     */
    public boolean isProcessing() {
        return isCreate();
    }

    @Override
    public String toString() {
        return code;
    }
}
