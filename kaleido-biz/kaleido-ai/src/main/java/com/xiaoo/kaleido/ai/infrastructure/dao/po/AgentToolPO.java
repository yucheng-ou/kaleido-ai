package com.xiaoo.kaleido.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Agent工具持久化对象
 * <p>
 * 对应数据库表：t_ai_agent_tool
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_ai_agent_tool")
public class AgentToolPO extends BasePO {

    /**
     * Agent ID
     */
    @TableField("agent_id")
    private String agentId;

    /**
     * 工具编码
     */
    @TableField("tool_code")
    private String toolCode;

    /**
     * 工具名称
     */
    @TableField("tool_name")
    private String toolName;

    /**
     * 工具类型：MEMORY-记忆，VECTOR_STORE-向量存储，MCP-MCP工具
     */
    @TableField("tool_type")
    private String toolType;

    /**
     * 工具配置（JSON格式）
     */
    @TableField("tool_config")
    private String toolConfig;
}
