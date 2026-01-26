package com.xiaoo.kaleido.recommend.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 推荐服务错误码枚举
 * <p>
 * 定义推荐服务中所有可能的错误码，包括参数校验、业务逻辑、状态异常等
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Getter
public enum RecommendErrorCode implements ErrorCode {

    // ========== 通用错误码 ==========
    /**
     * 数据不存在：根据ID查询时未找到对应记录
     */
    DATA_NOT_FOUND("RECOMMEND_DATA_NOT_FOUND", "数据不存在"),

    /**
     * 数据已存在：尝试创建已存在的数据
     */
    DATA_ALREADY_EXISTS("RECOMMEND_DATA_ALREADY_EXISTS", "数据已存在"),

    /**
     * 数据状态异常：数据状态不符合操作要求
     */
    DATA_STATUS_ERROR("RECOMMEND_DATA_STATUS_ERROR", "数据状态异常"),

    /**
     * 操作失败：通用操作失败错误
     */
    OPERATE_FAILED("RECOMMEND_OPERATE_FAILED", "操作失败"),

    /**
     * 查询失败：查询操作失败
     */
    QUERY_FAIL("RECOMMEND_QUERY_FAIL", "查询失败"),

    /**
     * 参数不能为空：通用参数空值校验失败
     */
    PARAM_NOT_NULL("RECOMMEND_PARAM_NOT_NULL", "参数不能为空"),

    /**
     * 参数格式错误：参数不符合格式规范
     */
    PARAM_FORMAT_ERROR("RECOMMEND_PARAM_FORMAT_ERROR", "参数格式错误"),

    /**
     * 参数长度超限：参数超过最大长度限制
     */
    PARAM_LENGTH_ERROR("RECOMMEND_PARAM_LENGTH_ERROR", "参数长度超限"),

    /**
     * 批量操作参数错误：批量操作参数无效
     */
    BATCH_OPERATION_PARAM_ERROR("RECOMMEND_BATCH_OPERATION_PARAM_ERROR", "批量操作参数错误"),

    /**
     * 权限不足：用户没有操作权限
     */
    PERMISSION_DENIED("RECOMMEND_PERMISSION_DENIED", "权限不足"),

    /**
     * 数据不属于用户：尝试操作不属于当前用户的数据
     */
    DATA_NOT_BELONG_TO_USER("RECOMMEND_DATA_NOT_BELONG_TO_USER", "数据不属于当前用户"),

    // ========== 推荐记录相关错误码 ==========
    /**
     * 推荐记录不存在：根据ID查询推荐记录时未找到对应记录
     */
    RECOMMEND_RECORD_NOT_FOUND("RECOMMEND_RECORD_NOT_FOUND", "推荐记录不存在"),

    /**
     * 推荐记录已有关联穿搭：推荐记录已有关联的穿搭，不能重复关联
     */
    RECOMMEND_RECORD_HAS_OUTFIT("RECOMMEND_RECORD_HAS_OUTFIT", "推荐记录已有关联穿搭"),

    /**
     * 推荐记录无关联穿搭：推荐记录没有关联的穿搭，不能进行相关操作
     */
    RECOMMEND_RECORD_NO_OUTFIT("RECOMMEND_RECORD_NO_OUTFIT", "推荐记录无关联穿搭"),

    /**
     * 提示词过长：用户输入的提示词超过最大长度限制
     */
    PROMPT_TOO_LONG("RECOMMEND_PROMPT_TOO_LONG", "提示词过长"),

    /**
     * 提示词为空：用户输入的提示词为空
     */
    PROMPT_EMPTY("RECOMMEND_PROMPT_EMPTY", "提示词不能为空"),

    // ========== AI服务相关错误码 ==========
    /**
     * AI服务调用失败：调用AI服务生成推荐时发生异常
     */
    AI_SERVICE_CALL_FAILED("RECOMMEND_AI_SERVICE_CALL_FAILED", "AI服务调用失败"),

    /**
     * AI服务错误：AI服务内部错误
     */
    AI_SERVICE_ERROR("RECOMMEND_AI_SERVICE_ERROR", "AI服务错误"),

    /**
     * AI服务不可用：AI服务未启用或无法访问
     */
    AI_SERVICE_UNAVAILABLE("RECOMMEND_AI_SERVICE_UNAVAILABLE", "AI服务不可用"),

    /**
     * AI生成结果解析失败：AI服务返回的结果无法解析
     */
    AI_RESULT_PARSE_FAILED("RECOMMEND_AI_RESULT_PARSE_FAILED", "AI生成结果解析失败"),

    /**
     * AI生成超时：AI服务响应超时
     */
    AI_GENERATION_TIMEOUT("RECOMMEND_AI_GENERATION_TIMEOUT", "AI生成超时"),

    // ========== 金币相关错误码 ==========
    /**
     * 金币不足：用户金币余额不足，无法生成推荐
     */
    COIN_INSUFFICIENT("RECOMMEND_COIN_INSUFFICIENT", "金币不足"),

    /**
     * 金币扣减失败：金币扣减操作失败
     */
    COIN_DEDUCTION_FAILED("RECOMMEND_COIN_DEDUCTION_FAILED", "金币扣减失败"),

    /**
     * 金币服务不可用：调用金币服务时发生异常
     */
    COIN_SERVICE_UNAVAILABLE("RECOMMEND_COIN_SERVICE_UNAVAILABLE", "金币服务不可用"),

    // ========== 穿搭相关错误码 ==========
    /**
     * 穿搭创建失败：创建穿搭时发生异常
     */
    OUTFIT_CREATION_FAILED("RECOMMEND_OUTFIT_CREATION_FAILED", "穿搭创建失败"),

    /**
     * 穿搭不存在：关联的穿搭不存在
     */
    OUTFIT_NOT_FOUND("RECOMMEND_OUTFIT_NOT_FOUND", "穿搭不存在"),

    /**
     * 穿搭保存失败：保存AI生成的穿搭时发生异常
     */
    OUTFIT_SAVE_FAILED("RECOMMEND_OUTFIT_SAVE_FAILED", "穿搭保存失败");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    RecommendErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
