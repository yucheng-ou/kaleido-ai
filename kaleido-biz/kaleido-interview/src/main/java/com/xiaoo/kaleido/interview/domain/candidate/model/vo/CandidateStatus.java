package com.xiaoo.kaleido.interview.domain.candidate.model.vo;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.interview.types.exception.InterviewErrorCode;
import com.xiaoo.kaleido.interview.types.exception.InterviewException;

/**
 * 候选人状态值对象
 * <p>
 * 表示候选人的状态，是不可变的值对象
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
public enum CandidateStatus {

    /**
     * 新候选人
     */
    NEW("NEW", "新候选人"),

    /**
     * 面试中
     */
    INTERVIEWING("INTERVIEWING", "面试中"),

    /**
     * 已录用
     */
    HIRED("HIRED", "已录用");

    private final String code;
    private final String description;

    CandidateStatus(String code, String description) {
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
     * @return 候选人状态
     */
    public static CandidateStatus fromCode(String code) {
        if (StrUtil.isBlank(code)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人状态编码不能为空");
        }

        for (CandidateStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "无效的候选人状态编码: " + code);
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

        for (CandidateStatus status : values()) {
            if (status.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否为新候选人状态
     *
     * @return 如果是新候选人状态返回true，否则返回false
     */
    public boolean isNew() {
        return this == NEW;
    }

    /**
     * 检查是否为面试中状态
     *
     * @return 如果是面试中状态返回true，否则返回false
     */
    public boolean isInterviewing() {
        return this == INTERVIEWING;
    }

    /**
     * 检查是否为已录用状态
     *
     * @return 如果是已录用状态返回true，否则返回false
     */
    public boolean isHired() {
        return this == HIRED;
    }

    @Override
    public String toString() {
        return code;
    }
}
