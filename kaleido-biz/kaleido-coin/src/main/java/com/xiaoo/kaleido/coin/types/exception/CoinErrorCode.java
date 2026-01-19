package com.xiaoo.kaleido.coin.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 金币服务错误码枚举
 * <p>
 * 定义金币服务中所有可能的错误码，包括参数校验、业务逻辑、状态异常等
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Getter
public enum CoinErrorCode implements ErrorCode {
    // ========== 通用错误码 ==========
    /**
     * 数据不存在：根据ID查询时未找到对应记录
     */
    DATA_NOT_FOUND("COIN_DATA_NOT_FOUND", "数据不存在"),

    /**
     * 数据已存在：尝试创建已存在的数据
     */
    DATA_ALREADY_EXISTS("COIN_DATA_ALREADY_EXISTS", "数据已存在"),

    /**
     * 数据状态异常：数据状态不符合操作要求
     */
    DATA_STATUS_ERROR("COIN_DATA_STATUS_ERROR", "数据状态异常"),

    /**
     * 操作失败：通用操作失败错误
     */
    OPERATE_FAILED("COIN_OPERATE_FAILED", "操作失败"),

    /**
     * 查询失败：查询操作失败
     */
    QUERY_FAIL("COIN_QUERY_FAIL", "查询失败"),

    /**
     * 参数不能为空：通用参数空值校验失败
     */
    PARAM_NOT_NULL("COIN_PARAM_NOT_NULL", "参数不能为空"),

    /**
     * 参数格式错误：参数不符合格式规范
     */
    PARAM_FORMAT_ERROR("COIN_PARAM_FORMAT_ERROR", "参数格式错误"),

    /**
     * 参数长度超限：参数超过最大长度限制
     */
    PARAM_LENGTH_ERROR("COIN_PARAM_LENGTH_ERROR", "参数长度超限"),

    /**
     * 批量操作参数错误：批量操作参数无效
     */
    BATCH_OPERATION_PARAM_ERROR("COIN_BATCH_OPERATION_PARAM_ERROR", "批量操作参数错误"),

    /**
     * 权限不足：用户没有操作权限
     */
    PERMISSION_DENIED("COIN_PERMISSION_DENIED", "权限不足"),

    /**
     * 数据不属于用户：尝试操作不属于当前用户的数据
     */
    DATA_NOT_BELONG_TO_USER("COIN_DATA_NOT_BELONG_TO_USER", "数据不属于当前用户"),

    // ========== 账户相关错误码 ==========
    /**
     * 账户不存在：根据用户ID查询账户时未找到对应记录
     */
    ACCOUNT_NOT_FOUND("COIN_ACCOUNT_NOT_FOUND", "账户不存在"),

    /**
     * 账户已存在：用户账户已存在
     */
    ACCOUNT_ALREADY_EXISTS("COIN_ACCOUNT_ALREADY_EXISTS", "账户已存在"),

    /**
     * 账户余额不足：账户余额不足以完成操作
     */
    ACCOUNT_BALANCE_INSUFFICIENT("COIN_ACCOUNT_BALANCE_INSUFFICIENT", "账户余额不足"),

    /**
     * 账户状态异常：账户状态不符合操作要求
     */
    ACCOUNT_STATUS_ERROR("COIN_ACCOUNT_STATUS_ERROR", "账户状态异常"),

    // ========== 流水相关错误码 ==========
    /**
     * 流水记录不存在：根据ID查询流水记录时未找到对应记录
     */
    STREAM_NOT_FOUND("COIN_STREAM_NOT_FOUND", "流水记录不存在"),

    /**
     * 流水类型无效：流水类型不存在或无效
     */
    STREAM_TYPE_INVALID("COIN_STREAM_TYPE_INVALID", "流水类型无效"),

    /**
     * 业务类型无效：业务类型不存在或无效
     */
    STREAM_BIZ_TYPE_INVALID("COIN_STREAM_BIZ_TYPE_INVALID", "业务类型无效"),

    /**
     * 流水金额无效：流水金额不符合要求
     */
    STREAM_AMOUNT_INVALID("COIN_STREAM_AMOUNT_INVALID", "流水金额无效"),

    // ========== 业务操作错误码 ==========
    /**
     * 邀请奖励处理失败：处理邀请奖励时发生错误
     */
    INVITE_REWARD_FAILED("COIN_INVITE_REWARD_FAILED", "邀请奖励处理失败"),

    /**
     * 位置创建扣费失败：处理位置创建扣费时发生错误
     */
    LOCATION_COST_FAILED("COIN_LOCATION_COST_FAILED", "位置创建扣费失败"),

    /**
     * 搭配创建扣费失败：处理搭配创建扣费时发生错误
     */
    OUTFIT_COST_FAILED("COIN_OUTFIT_COST_FAILED", "搭配创建扣费失败"),

    /**
     * 账户初始化失败：初始化用户账户时发生错误
     */
    ACCOUNT_INIT_FAILED("COIN_ACCOUNT_INIT_FAILED", "账户初始化失败"),

    /**
     * 金币扣减失败：扣减金币时发生错误
     */
    COIN_DEDUCT_FAILED("COIN_COIN_DEDUCT_FAILED", "金币扣减失败"),

    /**
     * 金币增加失败：增加金币时发生错误
     */
    COIN_ADD_FAILED("COIN_COIN_ADD_FAILED", "金币增加失败"),

    // ========== 配置相关错误码 ==========
    /**
     * 配置不存在：根据配置键查询配置时未找到对应记录
     */
    CONFIG_NOT_FOUND("COIN_CONFIG_NOT_FOUND", "配置不存在"),

    /**
     * 配置无效：配置值无效或不合法
     */
    CONFIG_INVALID("COIN_CONFIG_INVALID", "配置无效"),

    /**
     * 初始余额配置无效：初始余额配置值无效
     */
    INITIAL_BALANCE_INVALID("COIN_INITIAL_BALANCE_INVALID", "初始余额配置无效"),

    /**
     * 邀请奖励配置无效：邀请奖励配置值无效
     */
    INVITE_REWARD_INVALID("COIN_INVITE_REWARD_INVALID", "邀请奖励配置无效"),

    /**
     * 位置创建消耗配置无效：位置创建消耗配置值无效
     */
    LOCATION_COST_INVALID("COIN_LOCATION_COST_INVALID", "位置创建消耗配置无效"),

    /**
     * 搭配创建消耗配置无效：搭配创建消耗配置值无效
     */
    OUTFIT_COST_INVALID("COIN_OUTFIT_COST_INVALID", "搭配创建消耗配置无效");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    CoinErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
