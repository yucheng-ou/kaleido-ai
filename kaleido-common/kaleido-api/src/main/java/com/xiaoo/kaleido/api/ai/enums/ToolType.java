package com.xiaoo.kaleido.api.ai.enums;

/**
 * 工具类型值对象
 * <p>
 * 表示Agent工具的类型，是不可变的值对象
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public enum ToolType {

    /**
     * 记忆工具
     */
    MEMORY("MEMORY", "记忆"),

    /**
     * 向量存储工具
     */
    VECTOR_STORE("VECTOR_STORE", "向量存储"),

    /**
     * MCP工具
     */
    MCP("MCP", "MCP工具");

    private final String code;
    private final String description;

    ToolType(String code, String description) {
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
     * @return 工具类型
     * @throws IllegalArgumentException 当编码无效时抛出
     */
    public static ToolType fromCode(String code) {
        for (ToolType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的工具类型编码: " + code);
    }

    /**
     * 检查编码是否有效
     *
     * @param code 类型编码
     * @return 如果编码有效返回true，否则返回false
     */
    public static boolean isValid(String code) {
        for (ToolType type : values()) {
            if (type.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否为记忆工具
     *
     * @return 如果是记忆工具返回true，否则返回false
     */
    public boolean isMemory() {
        return this == MEMORY;
    }

    /**
     * 检查是否为向量存储工具
     *
     * @return 如果是向量存储工具返回true，否则返回false
     */
    public boolean isVectorStore() {
        return this == VECTOR_STORE;
    }

    /**
     * 检查是否为MCP工具
     *
     * @return 如果是MCP工具返回true，否则返回false
     */
    public boolean isMcp() {
        return this == MCP;
    }

    @Override
    public String toString() {
        return code;
    }
}
