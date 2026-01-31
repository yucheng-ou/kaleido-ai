package com.xiaoo.kaleido.api.ai.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Agent信息响应
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentInfoResponse extends BaseResp {

    /**
     * Agent ID
     */
    private String agentId;

    /**
     * Agent编码（唯一）
     */
    private String code;

    /**
     * Agent名称
     */
    private String name;

    /**
     * Agent描述
     */
    private String description;

    /**
     * 系统提示词
     */
    private String systemPrompt;

    /**
     * AI模型名称
     */
    private String modelName;

    /**
     * 温度参数
     */
    private BigDecimal temperature;

    /**
     * 最大token数
     */
    private Integer maxTokens;

    /**
     * Agent状态
     */
    private String status;

    /**
     * 工具列表
     */
    private List<AgentToolResponse> tools;
}

