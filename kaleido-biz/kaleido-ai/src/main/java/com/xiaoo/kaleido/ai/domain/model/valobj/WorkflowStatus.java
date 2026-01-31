package com.xiaoo.kaleido.ai.domain.model.valobj;

/**
 * 工作流状态值对象
 * <p>
 * 表示工作流的状态，是不可变的值对象
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public enum WorkflowStatus {

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

    WorkflowStatus(String code, String description) {
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
     * @return 工作流状态
     * @throws IllegalArgumentException 当编码无效时抛出
     */
    public static WorkflowStatus fromCode(String code) {
        for (WorkflowStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的工作流状态编码: " + code);
    }

    /**
     * 检查编码是否有效
     *
     * @param code 状态编码
     * @return 如果编码有效返回true，否则返回false
     */
    public static boolean isValid(String code) {
        for (WorkflowStatus status : values()) {
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
