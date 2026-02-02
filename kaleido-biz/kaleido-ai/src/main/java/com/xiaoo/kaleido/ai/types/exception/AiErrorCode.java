package com.xiaoo.kaleido.ai.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * AI服务错误码枚举
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Getter
public enum AiErrorCode implements ErrorCode {

    /**
     * Agent不存在：根据ID查询Agent时未找到对应记录
     */
    AGENT_NOT_FOUND("AGENT_NOT_FOUND", "Agent不存在"),

    /**
     * Agent已禁用：Agent状态为禁用
     */
    AGENT_DISABLED("AGENT_DISABLED", "Agent已禁用"),

    /**
     * Agent编码已存在：同用户下Agent编码已存在
     */
    AGENT_CODE_EXISTS("AGENT_CODE_EXISTS", "Agent编码已存在"),

    /**
     * Agent查询失败：查询Agent记录时发生错误
     */
    AGENT_QUERY_FAIL("AGENT_QUERY_FAIL", "Agent查询失败"),

    /**
     * 工具查询失败：查询工具记录时发生错误
     */
    TOOL_QUERY_FAIL("TOOL_QUERY_FAIL", "工具查询失败"),

    /**
     * 工具不存在：根据编码查询工具时未找到对应记录
     */
    TOOL_NOT_FOUND("TOOL_NOT_FOUND", "工具不存在"),

    /**
     * 工具编码已存在：同用户下工具编码已存在
     */
    TOOL_CODE_EXISTS("TOOL_CODE_EXISTS", "工具编码已存在"),

    /**
     * 会话不存在：根据ID查询会话时未找到对应记录
     */
    CONVERSATION_NOT_FOUND("CONVERSATION_NOT_FOUND", "会话不存在"),

    /**
     * 会话查询失败：查询会话记录时发生错误
     */
    CONVERSATION_QUERY_FAIL("CONVERSATION_QUERY_FAIL", "会话查询失败"),

    /**
     * 会话ID已存在：创建会话时ID已存在
     */
    CONVERSATION_ID_EXISTS("CONVERSATION_ID_EXISTS", "会话ID已存在"),

    /**
     * 会话标题不能为空：更新会话标题时标题为空
     */
    CONVERSATION_TITLE_EMPTY("CONVERSATION_TITLE_EMPTY", "会话标题不能为空"),

    /**
     * 会话访问被拒绝：用户无权访问该会话
     */
    CONVERSATION_ACCESS_DENIED("CONVERSATION_ACCESS_DENIED", "会话访问被拒绝"),

    /**
     * 工作流不存在：根据ID查询工作流时未找到对应记录
     */
    WORKFLOW_NOT_FOUND("WORKFLOW_NOT_FOUND", "工作流不存在"),

    /**
     * 工作流编码已存在：同用户下工作流编码已存在
     */
    WORKFLOW_CODE_EXISTS("WORKFLOW_CODE_EXISTS", "工作流编码已存在"),

    /**
     * 工作流查询失败：查询工作流记录时发生错误
     */
    WORKFLOW_QUERY_FAIL("WORKFLOW_QUERY_FAIL", "工作流查询失败"),

    /**
     * 工作流执行记录不存在：根据ID查询工作流执行记录时未找到对应记录
     */
    EXECUTION_NOT_FOUND("EXECUTION_NOT_FOUND", "工作流执行记录不存在"),

    /**
     * 工作流执行查询失败：查询工作流执行记录时发生错误
     */
    WORKFLOW_EXECUTION_QUERY_FAIL("WORKFLOW_EXECUTION_QUERY_FAIL", "工作流执行查询失败"),

    /**
     * 工作流执行删除失败：删除工作流执行记录时发生错误
     */
    WORKFLOW_EXECUTION_DELETE_FAIL("WORKFLOW_EXECUTION_DELETE_FAIL", "工作流执行删除失败"),

    /**
     * AI模型调用失败：调用AI模型服务时发生错误
     */
    AI_MODEL_ERROR("AI_MODEL_ERROR", "AI模型调用失败"),

    /**
     * 工具执行失败：执行工具时发生错误
     */
    TOOL_EXECUTION_ERROR("TOOL_EXECUTION_ERROR", "工具执行失败"),

    /**
     * 工作流执行失败：执行工作流时发生错误
     */
    WORKFLOW_EXECUTION_ERROR("WORKFLOW_EXECUTION_ERROR", "工作流执行失败"),

    /**
     * 参数校验失败：请求参数不符合要求
     */
    VALIDATION_ERROR("VALIDATION_ERROR", "参数校验失败"),

    /**
     * 数据库操作失败：数据库操作时发生错误
     */
    DATABASE_ERROR("DATABASE_ERROR", "数据库操作失败"),

    /**
     * 未知错误：未预期的系统错误
     */
    UNKNOWN_ERROR("UNKNOWN_ERROR", "未知错误"),

    /**
     * Agent ID不能为空：Agent ID参数为空
     */
    AGENT_ID_NOT_NULL("AGENT_ID_NOT_NULL", "Agent ID不能为空"),

    /**
     * Agent编码不能为空：Agent编码参数为空
     */
    AGENT_CODE_EMPTY("AGENT_CODE_EMPTY", "Agent编码不能为空"),

    /**
     * Agent名称不能为空：Agent名称参数为空
     */
    AGENT_NAME_EMPTY("AGENT_NAME_EMPTY", "Agent名称不能为空"),

    /**
     * Agent名称长度超限：Agent名称超过最大长度限制
     */
    AGENT_NAME_LENGTH_ERROR("AGENT_NAME_LENGTH_ERROR", "Agent名称长度不能超过100个字符"),

    /**
     * Agent描述长度超限：Agent描述超过最大长度限制
     */
    AGENT_DESCRIPTION_LENGTH_ERROR("AGENT_DESCRIPTION_LENGTH_ERROR", "Agent描述长度不能超过500个字符"),

    /**
     * 工具编码不能为空：工具编码参数为空
     */
    TOOL_CODE_EMPTY("TOOL_CODE_EMPTY", "工具编码不能为空"),

    /**
     * 工具名称不能为空：工具名称参数为空
     */
    TOOL_NAME_EMPTY("TOOL_NAME_EMPTY", "工具名称不能为空"),

    /**
     * 工具类型不能为空：工具类型参数为空
     */
    TOOL_TYPE_EMPTY("TOOL_TYPE_EMPTY", "工具类型不能为空"),

    /**
     * 工具类型无效：工具类型不存在或无效
     */
    TOOL_TYPE_INVALID("TOOL_TYPE_INVALID", "工具类型无效"),

    /**
     * 工作流ID不能为空：工作流ID参数为空
     */
    WORKFLOW_ID_NOT_NULL("WORKFLOW_ID_NOT_NULL", "工作流ID不能为空"),

    /**
     * 工作流编码不能为空：工作流编码参数为空
     */
    WORKFLOW_CODE_EMPTY("WORKFLOW_CODE_EMPTY", "工作流编码不能为空"),

    /**
     * 工作流名称不能为空：工作流名称参数为空
     */
    WORKFLOW_NAME_EMPTY("WORKFLOW_NAME_EMPTY", "工作流名称不能为空"),

    /**
     * 会话ID不能为空：会话ID参数为空
     */
    CONVERSATION_ID_NOT_NULL("CONVERSATION_ID_NOT_NULL", "会话ID不能为空"),

    /**
     * 消息内容不能为空：消息内容参数为空
     */
    MESSAGE_CONTENT_EMPTY("MESSAGE_CONTENT_EMPTY", "消息内容不能为空"),

    /**
     * 消息类型无效：消息类型不存在或无效
     */
    MESSAGE_TYPE_INVALID("MESSAGE_TYPE_INVALID", "消息类型无效"),

    /**
     * 执行ID不能为空：执行ID参数为空
     */
    EXECUTION_ID_NOT_NULL("EXECUTION_ID_NOT_NULL", "执行ID不能为空"),

    /**
     * 用户ID不能为空：用户ID参数为空
     */
    USER_ID_NOT_NULL("USER_ID_NOT_NULL", "用户ID不能为空"),

    /**
     * AI模型配置错误：AI模型配置无效或缺失
     */
    AI_MODEL_CONFIG_ERROR("AI_MODEL_CONFIG_ERROR", "AI模型配置错误"),

    /**
     * 工具配置错误：工具配置无效或缺失
     */
    TOOL_CONFIG_ERROR("TOOL_CONFIG_ERROR", "工具配置错误"),

    /**
     * 工作流配置错误：工作流配置无效或缺失
     */
    WORKFLOW_CONFIG_ERROR("WORKFLOW_CONFIG_ERROR", "工作流配置错误"),

    /**
     * 权限不足：用户没有执行操作的权限
     */
    PERMISSION_DENIED("PERMISSION_DENIED", "权限不足"),

    /**
     * 资源限制：达到资源使用限制
     */
    RESOURCE_LIMIT_EXCEEDED("RESOURCE_LIMIT_EXCEEDED", "资源使用超过限制"),

    /**
     * 操作超时：操作执行超时
     */
    OPERATION_TIMEOUT("OPERATION_TIMEOUT", "操作执行超时"),

    /**
     * 网络错误：网络连接或通信错误
     */
    NETWORK_ERROR("NETWORK_ERROR", "网络连接错误"),

    /**
     * 外部服务错误：调用外部服务时发生错误
     */
    EXTERNAL_SERVICE_ERROR("EXTERNAL_SERVICE_ERROR", "外部服务调用失败"),

    /**
     * 向量存储保存失败：保存文档到向量存储时发生错误
     */
    VECTOR_STORE_SAVE_FAIL("VECTOR_STORE_SAVE_FAIL", "向量存储保存失败"),

    /**
     * 向量存储搜索失败：在向量存储中搜索文档时发生错误
     */
    VECTOR_STORE_SEARCH_FAIL("VECTOR_STORE_SEARCH_FAIL", "向量存储搜索失败"),

    /**
     * 向量存储删除失败：从向量存储中删除文档时发生错误
     */
    VECTOR_STORE_DELETE_FAIL("VECTOR_STORE_DELETE_FAIL", "向量存储删除失败"),

    /**
     * 向量存储查询失败：查询向量存储时发生错误
     */
    VECTOR_STORE_QUERY_FAIL("VECTOR_STORE_QUERY_FAIL", "向量存储查询失败");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    AiErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
