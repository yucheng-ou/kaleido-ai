package com.xiaoo.kaleido.interview.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 面试服务错误码枚举
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Getter
public enum InterviewErrorCode implements ErrorCode {

    // ==================== 面试相关错误码 ====================
    /**
     * 面试不存在：根据ID查询面试时未找到对应记录
     */
    INTERVIEW_NOT_FOUND("INTERVIEW_001", "面试不存在"),

    /**
     * 面试ID不能为空：面试ID参数为空
     */
    INTERVIEW_ID_NOT_NULL("INTERVIEW_002", "面试ID不能为空"),

    /**
     * 面试状态无效：面试状态不存在或无效
     */
    INTERVIEW_STATUS_INVALID("INTERVIEW_003", "面试状态无效"),

    /**
     * 面试查询失败：查询面试记录时发生错误
     */
    INTERVIEW_QUERY_FAIL("INTERVIEW_004", "面试查询失败"),

    /**
     * 面试访问被拒绝：用户无权访问该面试
     */
    INTERVIEW_ACCESS_DENIED("INTERVIEW_005", "面试访问被拒绝"),

    /**
     * 面试已结束：面试已经完成或结束
     */
    INTERVIEW_ALREADY_FINISHED("INTERVIEW_006", "面试已结束"),

    /**
     * 面试尚未开始：面试还未开始
     */
    INTERVIEW_NOT_STARTED("INTERVIEW_007", "面试尚未开始"),

    // ==================== 面试会话相关错误码 ====================
    /**
     * 面试会话不存在：根据ID查询会话时未找到对应记录
     */
    SESSION_NOT_FOUND("SESSION_001", "面试会话不存在"),

    /**
     * 会话ID不能为空：会话ID参数为空
     */
    SESSION_ID_NOT_NULL("SESSION_002", "会话ID不能为空"),

    /**
     * 会话查询失败：查询会话记录时发生错误
     */
    SESSION_QUERY_FAIL("SESSION_003", "会话查询失败"),

    /**
     * 会话已过期：会话已超过有效期
     */
    SESSION_EXPIRED("SESSION_004", "会话已过期"),

    // ==================== 面试题目相关错误码 ====================
    /**
     * 面试题目不存在：根据ID查询题目时未找到对应记录
     */
    QUESTION_NOT_FOUND("QUESTION_001", "面试题目不存在"),

    /**
     * 题目ID不能为空：题目ID参数为空
     */
    QUESTION_ID_NOT_NULL("QUESTION_002", "题目ID不能为空"),

    /**
     * 题目查询失败：查询题目记录时发生错误
     */
    QUESTION_QUERY_FAIL("QUESTION_003", "题目查询失败"),

    /**
     * 题目类型无效：题目类型不存在或无效
     */
    QUESTION_TYPE_INVALID("QUESTION_004", "题目类型无效"),

    /**
     * 题目难度无效：题目难度不存在或无效
     */
    QUESTION_DIFFICULTY_INVALID("QUESTION_005", "题目难度无效"),

    // ==================== 面试回答相关错误码 ====================
    /**
     * 面试回答不存在：根据ID查询回答时未找到对应记录
     */
    ANSWER_NOT_FOUND("ANSWER_001", "面试回答不存在"),

    /**
     * 回答内容不能为空：回答内容参数为空
     */
    ANSWER_CONTENT_EMPTY("ANSWER_002", "回答内容不能为空"),

    /**
     * 回答查询失败：查询回答记录时发生错误
     */
    ANSWER_QUERY_FAIL("ANSWER_003", "回答查询失败"),

    // ==================== 面试评估相关错误码 ====================
    /**
     * 评估结果不存在：根据ID查询评估结果时未找到对应记录
     */
    EVALUATION_NOT_FOUND("EVALUATION_001", "评估结果不存在"),

    /**
     * 评估失败：执行评估时发生错误
     */
    EVALUATION_FAIL("EVALUATION_002", "评估失败"),

    /**
     * 评估分数无效：评估分数不在有效范围内
     */
    EVALUATION_SCORE_INVALID("EVALUATION_003", "评估分数无效"),

    // ==================== AI相关错误码 ====================
    /**
     * AI面试配置错误：AI面试配置无效或缺失
     */
    AI_INTERVIEW_CONFIG_ERROR("AI_INTERVIEW_001", "AI面试配置错误"),

    /**
     * AI面试执行错误：执行AI面试时发生错误
     */
    AI_INTERVIEW_EXECUTION_ERROR("AI_INTERVIEW_002", "AI面试执行错误"),

    /**
     * AI模型不可用：AI模型服务不可用或无法访问
     */
    AI_MODEL_NOT_AVAILABLE("AI_INTERVIEW_003", "AI模型不可用"),

    // ==================== 通用错误码 ====================
    /**
     * 参数校验失败：请求参数不符合要求
     */
    VALIDATION_ERROR("COMMON_001", "参数校验失败"),

    /**
     * 数据库操作失败：数据库操作时发生错误
     */
    DATABASE_ERROR("COMMON_002", "数据库操作失败"),

    /**
     * 网络错误：网络连接或通信错误
     */
    NETWORK_ERROR("COMMON_003", "网络错误"),

    /**
     * 未知错误：未预期的系统错误
     */
    UNKNOWN_ERROR("COMMON_004", "未知错误");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    InterviewErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}