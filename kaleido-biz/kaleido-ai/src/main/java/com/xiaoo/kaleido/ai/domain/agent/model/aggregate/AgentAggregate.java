package com.xiaoo.kaleido.ai.domain.agent.model.aggregate;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.ai.domain.agent.model.entity.AgentTool;
import com.xiaoo.kaleido.ai.domain.agent.model.vo.AgentStatus;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * AI Agent聚合根
 * <p>
 * AI Agent领域模型的核心聚合根，封装Agent实体及其工具配置，确保业务规则的一致性
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AgentAggregate extends BaseEntity {

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
    private AgentStatus status;

    /**
     * 工具列表（聚合根内的实体）
     * 记录Agent的所有工具配置
     */
    @Builder.Default
    private List<AgentTool> tools = new ArrayList<>();

    /**
     * 创建新Agent聚合根
     * <p>
     * 用于创建新Agent时构建聚合根
     *
     * @param code          Agent编码，不能为空
     * @param name          Agent名称，不能为空
     * @param description   Agent描述，可为空
     * @param systemPrompt  系统提示词，不能为空
     * @param modelName     AI模型名称，可为空（默认deepseek-v3）
     * @param temperature   温度参数，可为空（默认0.70）
     * @param maxTokens     最大token数，可为空（默认2000）
     * @return Agent聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    public static AgentAggregate create(
            String code,
            String name,
            String description,
            String systemPrompt,
            String modelName,
            BigDecimal temperature,
            Integer maxTokens) {
        
        if (StrUtil.isBlank(code)) {
            throw AiException.of(AiErrorCode.AGENT_CODE_EMPTY, "Agent编码不能为空");
        }
        if (StrUtil.isBlank(name)) {
            throw AiException.of(AiErrorCode.AGENT_NAME_EMPTY, "Agent名称不能为空");
        }
        if (StrUtil.isBlank(systemPrompt)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "系统提示词不能为空");
        }

        return AgentAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .code(code.trim())
                .name(name.trim())
                .description(description != null ? description.trim() : null)
                .systemPrompt(systemPrompt.trim())
                .modelName(modelName != null ? modelName.trim() : "deepseek-v3")
                .temperature(temperature != null ? temperature : new BigDecimal("0.70"))
                .maxTokens(maxTokens != null ? maxTokens : 2000)
                .status(AgentStatus.NORMAL)
                .build();
    }

    /**
     * 更新Agent信息
     * <p>
     * 更新Agent的名称、描述、系统提示词等配置
     * 如果参数为空，则不更新对应的字段
     *
     * @param name          新Agent名称，不能为空
     * @param description   新Agent描述，可为空（如果为空则不更新）
     * @param systemPrompt  新系统提示词，不能为空
     * @param modelName     新AI模型名称，可为空（如果为空则不更新）
     * @param temperature   新温度参数，可为空（如果为空则不更新）
     * @param maxTokens     新最大token数，可为空（如果为空则不更新）
     * @throws IllegalStateException 如果Agent状态不允许修改
     */
    public void updateInfo(
            String name,
            String description,
            String systemPrompt,
            String modelName,
            BigDecimal temperature,
            Integer maxTokens) {
        
        if (StrUtil.isBlank(name)) {
            throw AiException.of(AiErrorCode.AGENT_NAME_EMPTY, "Agent名称不能为空");
        }
        if (StrUtil.isBlank(systemPrompt)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "系统提示词不能为空");
        }

        this.name = name.trim();
        this.systemPrompt = systemPrompt.trim();
        
        // 只有参数不为空时才更新对应字段
        if (StrUtil.isNotBlank(description)) {
            this.description = description.trim();
        }
        if (StrUtil.isNotBlank(modelName)) {
            this.modelName = modelName.trim();
        }
        if (temperature != null) {
            this.temperature = temperature;
        }
        if (maxTokens != null) {
            this.maxTokens = maxTokens;
        }
    }

    /**
     * 启用Agent
     * <p>
     * 将Agent状态设置为NORMAL
     */
    public void enable() {
        this.status = AgentStatus.NORMAL;
    }

    /**
     * 禁用Agent
     * <p>
     * 将Agent状态设置为DISABLED
     */
    public void disable() {
        this.status = AgentStatus.DISABLED;
    }

    /**
     * 添加工具到Agent
     *
     * @param tool 工具实体，不能为空
     * @throws IllegalStateException 如果Agent状态不允许添加工具
     * @throws IllegalArgumentException 如果参数无效或工具已存在
     */
    public void addTool(AgentTool tool) {
        if (tool == null) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工具不能为空");
        }

        // 检查工具是否已存在
        if (isToolExists(tool.getToolCode())) {
            throw AiException.of(AiErrorCode.TOOL_CODE_EXISTS, "工具编码已存在: " + tool.getToolCode());
        }

        this.tools.add(tool);
    }

    /**
     * 从Agent移除工具
     *
     * @param toolCode 工具编码，不能为空
     * @throws IllegalStateException 如果Agent状态不允许移除工具
     * @throws IllegalArgumentException 如果参数无效或工具不存在
     */
    public void removeTool(String toolCode) {
        if (StrUtil.isBlank(toolCode)) {
            throw AiException.of(AiErrorCode.TOOL_CODE_EMPTY, "工具编码不能为空");
        }

        boolean removed = this.tools.removeIf(tool -> tool.getToolCode().equals(toolCode.trim()));
        if (!removed) {
            throw AiException.of(AiErrorCode.TOOL_NOT_FOUND, "工具不存在: " + toolCode);
        }
    }

    /**
     * 检查工具是否存在
     *
     * @param toolCode 工具编码
     * @return 如果工具存在返回true，否则返回false
     */
    public boolean isToolExists(String toolCode) {
        return this.tools.stream()
                .anyMatch(tool -> tool.getToolCode().equals(toolCode));
    }
    /**
     * 获取并清空工具列表（用于持久化后清理）
     *
     * @return 工具列表
     */
    public List<AgentTool> getAndClearTools() {
        List<AgentTool> toolsCopy = new ArrayList<>(this.tools);
        this.tools.clear();
        return toolsCopy;
    }
}
