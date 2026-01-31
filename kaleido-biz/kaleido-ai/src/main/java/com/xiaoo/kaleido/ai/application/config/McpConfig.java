package com.xiaoo.kaleido.ai.application.config;

import lombok.Data;

/**
 * MCP工具配置
 * <p>
 * 用于配置SyncMcpToolCallbackProvider的参数
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
public class McpConfig {

    /**
     * MCP服务器基础URI
     * 例如："http://localhost:8101"
     */
    private String baseUri;

    /**
     * mcp endpoint
     * 例如 /sse
     */
    private String endpoint;

    /**
     * 连接超时时间（毫秒）
     * 默认值：5000
     */
    private Integer connectTimeout = 5000;

    /**
     * 读取超时时间（毫秒）
     * 默认值：30000
     */
    private Integer readTimeout = 30000;

    /**
     * 是否启用MCP工具
     * 默认值：true
     */
    private Boolean enabled = true;

    /**
     * 最大重试次数
     * 默认值：3
     */
    private Integer maxRetries = 3;
}
