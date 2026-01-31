package com.xiaoo.kaleido.ai.domain.model.valobj;

import cn.hutool.core.util.StrUtil;

/**
 * 消息类型值对象
 * <p>
 * 表示对话消息的类型，是不可变的值对象
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public enum MessageType {

    /**
     * 用户消息
     */
    USER("USER", "用户消息"),

    /**
     * AI消息
     */
    AI("AI", "AI消息"),

    /**
     * 系统消息
     */
    SYSTEM("SYSTEM", "系统消息"),

    /**
     * 工具调用消息
     */
    TOOL_CALL("TOOL_CALL", "工具调用"),

    /**
     * 工具结果消息
     */
    TOOL_RESULT("TOOL_RESULT", "工具结果");

    private final String code;
    private final String description;

    MessageType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取类型编码
     *
     * @return 类型编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取类型描述
     *
     * @return 类型描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 根据编码获取类型
     *
     * @param code 类型编码
     * @return 消息类型
     * @throws IllegalArgumentException 当编码无效时抛出
     */
    public static MessageType fromCode(String code) {
        if (StrUtil.isBlank(code)) {
            throw new IllegalArgumentException("消息类型编码不能为空");
        }
        
        for (MessageType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的消息类型编码: " + code);
    }

    /**
     * 检查编码是否有效
     *
     * @param code 类型编码
     * @return 如果编码有效返回true，否则返回false
     */
    public static boolean isValid(String code) {
        if (StrUtil.isBlank(code)) {
            return false;
        }
        
        for (MessageType type : values()) {
            if (type.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否为用户消息
     *
     * @return 如果是用户消息返回true，否则返回false
     */
    public boolean isUser() {
        return this == USER;
    }

    /**
     * 检查是否为AI消息
     *
     * @return 如果是AI消息返回true，否则返回false
     */
    public boolean isAi() {
        return this == AI;
    }

    /**
     * 检查是否为系统消息
     *
     * @return 如果是系统消息返回true，否则返回false
     */
    public boolean isSystem() {
        return this == SYSTEM;
    }

    /**
     * 检查是否为工具调用消息
     *
     * @return 如果是工具调用消息返回true，否则返回false
     */
    public boolean isToolCall() {
        return this == TOOL_CALL;
    }

    /**
     * 检查是否为工具结果消息
     *
     * @return 如果是工具结果消息返回true，否则返回false
     */
    public boolean isToolResult() {
        return this == TOOL_RESULT;
    }

    @Override
    public String toString() {
        return code;
    }
}
