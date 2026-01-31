package com.xiaoo.kaleido.ai.domain.model.entity;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.api.ai.enums.ToolType;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Agent工具实体
 * <p>
 * Agent工具领域模型的实体，表示Agent的工具配置
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AgentTool extends BaseEntity {

    /**
     * Agent ID
     */
    private String agentId;

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
    private ToolType toolType;

    /**
     * 工具配置（JSON格式）
     */
    private String toolConfig;

    /**
     * 创建新工具实体
     * <p>
     * 用于创建新工具时构建实体
     *
     * @param agentId   Agent ID，不能为空
     * @param toolCode  工具编码，不能为空
     * @param toolName  工具名称，不能为空
     * @param toolType  工具类型，不能为空
     * @param toolConfig 工具配置，可为空
     * @return 工具实体
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    public static AgentTool create(
            String agentId,
            String toolCode,
            String toolName,
            ToolType toolType,
            String toolConfig) {
        
        if (StrUtil.isBlank(agentId)) {
            throw AiException.of(AiErrorCode.AGENT_ID_NOT_NULL, "Agent ID不能为空");
        }
        if (StrUtil.isBlank(toolCode)) {
            throw AiException.of(AiErrorCode.TOOL_CODE_EMPTY, "工具编码不能为空");
        }
        if (StrUtil.isBlank(toolName)) {
            throw AiException.of(AiErrorCode.TOOL_NAME_EMPTY, "工具名称不能为空");
        }
        if (toolType == null) {
            throw AiException.of(AiErrorCode.TOOL_TYPE_INVALID, "工具类型不能为空");
        }

        return AgentTool.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .agentId(agentId.trim())
                .toolCode(toolCode.trim())
                .toolName(toolName.trim())
                .toolType(toolType)
                .toolConfig(toolConfig)
                .build();
    }

    /**
     * 更新工具信息
     * <p>
     * 更新工具的名称、类型和配置
     *
     * @param toolName   新工具名称，不能为空
     * @param toolType   新工具类型，不能为空
     * @param toolConfig 新工具配置，可为空
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    public void updateInfo(String toolName, ToolType toolType, String toolConfig) {
        if (StrUtil.isBlank(toolName)) {
            throw AiException.of(AiErrorCode.TOOL_NAME_EMPTY, "工具名称不能为空");
        }
        if (toolType == null) {
            throw AiException.of(AiErrorCode.TOOL_TYPE_INVALID, "工具类型不能为空");
        }

        this.toolName = toolName.trim();
        this.toolType = toolType;
        this.toolConfig = toolConfig;
    }

    /**
     * 检查是否是记忆工具
     *
     * @return 如果工具类型为MEMORY返回true，否则返回false
     */
    public boolean isMemoryTool() {
        return ToolType.MEMORY.equals(this.toolType);
    }

    /**
     * 检查是否是向量存储工具
     *
     * @return 如果工具类型为VECTOR_STORE返回true，否则返回false
     */
    public boolean isVectorStoreTool() {
        return ToolType.VECTOR_STORE.equals(this.toolType);
    }

    /**
     * 检查是否是MCP工具
     *
     * @return 如果工具类型为MCP返回true，否则返回false
     */
    public boolean isMcpTool() {
        return ToolType.MCP.equals(this.toolType);
    }
}
