package com.xiaoo.kaleido.message.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 消息服务错误码枚举
 * <p>
 * 定义消息服务中所有可能的错误码，包括参数校验、业务逻辑、状态异常等
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
@Getter
public enum MessageErrorCode implements ErrorCode {

    // ========== 通用错误码 ==========
    /**
     * 数据不存在：根据ID查询时未找到对应记录
     */
    DATA_NOT_FOUND("MESSAGE_DATA_NOT_FOUND", "数据不存在"),

    /**
     * 数据已存在：尝试创建已存在的数据
     */
    DATA_ALREADY_EXISTS("MESSAGE_DATA_ALREADY_EXISTS", "数据已存在"),

    /**
     * 数据状态异常：数据状态不符合操作要求
     */
    DATA_STATUS_ERROR("MESSAGE_DATA_STATUS_ERROR", "数据状态异常"),

    /**
     * 操作失败：通用操作失败错误
     */
    OPERATE_FAILED("MESSAGE_OPERATE_FAILED", "操作失败"),

    /**
     * 查询失败：查询操作失败
     */
    QUERY_FAIL("MESSAGE_QUERY_FAIL", "查询失败"),

    /**
     * 参数不能为空：通用参数空值校验失败
     */
    PARAM_NOT_NULL("MESSAGE_PARAM_NOT_NULL", "参数不能为空"),

    /**
     * 参数格式错误：参数不符合格式规范
     */
    PARAM_FORMAT_ERROR("MESSAGE_PARAM_FORMAT_ERROR", "参数格式错误"),

    /**
     * 参数长度超限：参数超过最大长度限制
     */
    PARAM_LENGTH_ERROR("MESSAGE_PARAM_LENGTH_ERROR", "参数长度超限"),

    /**
     * 批量操作参数错误：批量操作参数无效
     */
    BATCH_OPERATION_PARAM_ERROR("MESSAGE_BATCH_OPERATION_PARAM_ERROR", "批量操作参数错误"),

    /**
     * 权限不足：用户没有操作权限
     */
    PERMISSION_DENIED("MESSAGE_PERMISSION_DENIED", "权限不足"),

    /**
     * 数据不属于用户：尝试操作不属于当前用户的数据
     */
    DATA_NOT_BELONG_TO_USER("MESSAGE_DATA_NOT_BELONG_TO_USER", "数据不属于当前用户"),

    // ========== 消息相关错误码 ==========
    /**
     * 消息不存在：根据ID查询消息时未找到对应记录
     */
    MESSAGE_NOT_FOUND("MESSAGE_MESSAGE_NOT_FOUND", "消息不存在"),

    /**
     * 消息状态错误：消息状态不符合操作要求
     */
    MESSAGE_STATE_ERROR("MESSAGE_MESSAGE_STATE_ERROR", "消息状态错误"),

    /**
     * 消息长度超限：消息内容超过最大长度限制
     */
    MESSAGE_LENGTH_EXCEEDED("MESSAGE_MESSAGE_LENGTH_EXCEEDED", "消息长度超限"),

    /**
     * 主题长度超限：消息主题超过最大长度限制
     */
    TOPIC_LENGTH_EXCEEDED("MESSAGE_TOPIC_LENGTH_EXCEEDED", "主题长度超限"),

    /**
     * 终态消息不能修改：消息状态已是终态，无法进行修改操作
     */
    FINAL_STATE_CANNOT_MODIFY("MESSAGE_FINAL_STATE_CANNOT_MODIFY", "终态消息不能修改"),

    /**
     * 消息发送失败：消息发送操作失败
     */
    MESSAGE_SEND_FAILED("MESSAGE_MESSAGE_SEND_FAILED", "消息发送失败"),

    /**
     * 消息消费失败：消息消费操作失败
     */
    MESSAGE_CONSUME_FAILED("MESSAGE_MESSAGE_CONSUME_FAILED", "消息消费失败"),

    /**
     * 消息队列异常：消息队列操作异常
     */
    MESSAGE_QUEUE_ERROR("MESSAGE_MESSAGE_QUEUE_ERROR", "消息队列异常"),

    /**
     * 消息序列化失败：消息序列化操作失败
     */
    MESSAGE_SERIALIZE_FAILED("MESSAGE_MESSAGE_SERIALIZE_FAILED", "消息序列化失败"),

    /**
     * 消息反序列化失败：消息反序列化操作失败
     */
    MESSAGE_DESERIALIZE_FAILED("MESSAGE_MESSAGE_DESERIALIZE_FAILED", "消息反序列化失败"),

    // ========== 系统错误码 ==========
    /**
     * 系统错误：系统内部异常
     */
    SYSTEM_ERROR("MESSAGE_SYSTEM_ERROR", "系统异常"),

    /**
     * 系统繁忙：系统限流或过载
     */
    SYSTEM_BUSY("MESSAGE_SYSTEM_BUSY", "系统繁忙，请稍后再试"),

    /**
     * 系统降级：系统触发降级保护
     */
    SYSTEM_DEGRADED("MESSAGE_SYSTEM_DEGRADED", "系统暂时不可用，请稍后重试");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    MessageErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
