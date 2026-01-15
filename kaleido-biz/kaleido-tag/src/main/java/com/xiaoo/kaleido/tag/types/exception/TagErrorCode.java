package com.xiaoo.kaleido.tag.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 标签服务错误码枚举
 * 
 * 定义标签服务中所有可能的错误码，包括参数校验、业务逻辑、状态异常等
 * 
 * @author ouyucheng
 * @date 2026/1/15
 */
@Getter
public enum TagErrorCode implements ErrorCode {

    /** 标签不存在：根据ID查询标签时未找到对应记录 */
    TAG_NOT_FOUND("TAG_NOT_FOUND", "标签不存在"),
    
    /** 标签名称已存在：同用户同类型下标签名称已存在 */
    TAG_NAME_EXISTS("TAG_NAME_EXISTS", "标签名称在用户下已存在（同类型）"),
    
    /** 标签已禁用：尝试操作已被禁用的标签 */
    TAG_DISABLED("TAG_DISABLED", "标签已禁用"),
    
    /** 标签已是启用状态：尝试启用已启用的标签 */
    TAG_ALREADY_ENABLED("TAG_ALREADY_ENABLED", "标签已是启用状态"),
    
    /** 标签已是禁用状态：尝试禁用已禁用的标签 */
    TAG_ALREADY_DISABLED("TAG_ALREADY_DISABLED", "标签已是禁用状态"),
    
    /** 标签状态异常：标签状态不符合操作要求 */
    TAG_STATUS_ERROR("TAG_STATUS_ERROR", "标签状态异常"),
    
    /** 标签操作失败：通用标签操作失败错误 */
    TAG_OPERATE_FAILED("TAG_OPERATE_FAILED", "标签操作失败"),
    
    /** 标签查询失败：标签查询操作失败 */
    TAG_QUERY_FAIL("TAG_QUERY_FAIL", "标签查询失败"),
    
    /** 标签ID不能为空：标签ID参数为空 */
    TAG_ID_NOT_NULL("TAG_ID_NOT_NULL", "标签ID不能为空"),
    
    /** 标签ID格式错误：标签ID不符合格式规范 */
    TAG_ID_FORMAT_ERROR("TAG_ID_FORMAT_ERROR", "标签ID格式错误"),
    
    /** 用户ID不能为空：用户ID参数为空 */
    USER_ID_NOT_NULL("USER_ID_NOT_NULL", "用户ID不能为空"),
    
    /** 标签名称不能为空：标签名称参数为空 */
    TAG_NAME_EMPTY("TAG_NAME_EMPTY", "标签名称不能为空"),
    
    /** 标签名称长度超限：标签名称超过最大长度限制 */
    TAG_NAME_LENGTH_ERROR("TAG_NAME_LENGTH_ERROR", "标签名称长度不能超过50个字符"),
    
    /** 标签类型编码不能为空：标签类型编码参数为空 */
    TAG_TYPE_CODE_EMPTY("TAG_TYPE_CODE_EMPTY", "标签类型编码不能为空"),
    
    /** 标签类型编码无效：标签类型编码不存在或无效 */
    TAG_TYPE_CODE_INVALID("TAG_TYPE_CODE_INVALID", "标签类型编码无效"),
    
    /** 标签描述长度超限：标签描述超过最大长度限制 */
    TAG_DESCRIPTION_LENGTH_ERROR("TAG_DESCRIPTION_LENGTH_ERROR", "标签描述长度不能超过200个字符"),
    
    /** 标签颜色长度超限：标签颜色超过最大长度限制 */
    TAG_COLOR_LENGTH_ERROR("TAG_COLOR_LENGTH_ERROR", "标签颜色长度不能超过20个字符"),
    
    /** 标签颜色格式错误：标签颜色不符合格式规范 */
    TAG_COLOR_FORMAT_ERROR("TAG_COLOR_FORMAT_ERROR", "标签颜色格式错误"),
    
    /** 实体ID不能为空：实体ID参数为空 */
    ENTITY_ID_NOT_NULL("ENTITY_ID_NOT_NULL", "实体ID不能为空"),
    
    /** 实体类型编码不能为空：实体类型编码参数为空 */
    ENTITY_TYPE_CODE_NOT_NULL("ENTITY_TYPE_CODE_NOT_NULL", "实体类型编码不能为空"),
    
    /** 标签类型与实体类型不匹配：标签不能关联到不匹配类型的实体 */
    TAG_TYPE_MISMATCH("TAG_TYPE_MISMATCH", "标签类型与实体类型不匹配"),
    
    /** 实体已关联标签：实体已关联该标签，不能重复关联 */
    ENTITY_ALREADY_ASSOCIATED("ENTITY_ALREADY_ASSOCIATED", "实体已关联该标签"),
    
    /** 标签不属于用户：尝试操作不属于当前用户的标签 */
    TAG_NOT_BELONG_TO_USER("TAG_NOT_BELONG_TO_USER", "标签不属于当前用户"),
    
    /** 关联数量超限：关联数量超过业务规则限制 */
    ASSOCIATION_LIMIT_EXCEEDED("ASSOCIATION_LIMIT_EXCEEDED", "关联数量超过限制"),
    
    /** 用户标签数量超限：用户创建的标签数量超过限制 */
    USER_TAG_LIMIT_EXCEEDED("USER_TAG_LIMIT_EXCEEDED", "用户标签数量超过限制"),
    
    /** 实体标签数量超限：实体关联的标签数量超过限制 */
    ENTITY_TAG_LIMIT_EXCEEDED("ENTITY_TAG_LIMIT_EXCEEDED", "实体标签数量超过限制"),
    
    /** 批量操作参数错误：批量操作参数无效 */
    BATCH_OPERATION_PARAM_ERROR("BATCH_OPERATION_PARAM_ERROR", "批量操作参数错误"),
    
    /** 标签关联不存在：标签与实体的关联关系不存在 */
    TAG_ASSOCIATION_NOT_FOUND("TAG_ASSOCIATION_NOT_FOUND", "标签关联不存在"),
    
    /** 标签使用次数统计错误：使用次数统计异常 */
    TAG_USAGE_COUNT_ERROR("TAG_USAGE_COUNT_ERROR", "标签使用次数统计错误"),
    
    /** 标签数据同步失败：标签数据同步操作失败 */
    TAG_DATA_SYNC_FAILED("TAG_DATA_SYNC_FAILED", "标签数据同步失败"),
    
    /** 标签缓存操作失败：标签缓存操作异常 */
    TAG_CACHE_OPERATION_FAILED("TAG_CACHE_OPERATION_FAILED", "标签缓存操作失败"),
    
    /** 标签RPC调用失败：标签RPC服务调用异常 */
    TAG_RPC_CALL_FAILED("TAG_RPC_CALL_FAILED", "标签RPC调用失败"),
    
    /** 标签消息处理失败：标签消息队列处理异常 */
    TAG_MESSAGE_PROCESS_FAILED("TAG_MESSAGE_PROCESS_FAILED", "标签消息处理失败");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    TagErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
