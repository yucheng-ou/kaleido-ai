package com.xiaoo.kaleido.api.ai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Agent工具响应
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentToolResponse {

    /**
     * 工具编码
     */
    private String toolCode;

    /**
     * 工具名称
     */
    private String toolName;

    /**
     * 工具类型
     */
    private String toolType;

    /**
     * 工具配置
     */
    private String toolConfig;
}

