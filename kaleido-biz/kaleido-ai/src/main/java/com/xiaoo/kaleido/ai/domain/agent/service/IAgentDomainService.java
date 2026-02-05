package com.xiaoo.kaleido.ai.domain.agent.service;

import com.xiaoo.kaleido.ai.domain.agent.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.domain.agent.model.entity.AgentTool;
import com.xiaoo.kaleido.api.ai.enums.ToolType;

import java.math.BigDecimal;

/**
 * Agent领域服务接口
 * <p>
 * 定义Agent领域服务的核心业务逻辑，处理Agent的创建、更新、删除、工具管理等操作
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface IAgentDomainService {

    /**
     * 创建Agent
     *
     * @param code         Agent编码，不能为空
     * @param name         Agent名称，不能为空
     * @param description  Agent描述，可为空
     * @param systemPrompt 系统提示词，不能为空
     * @param modelName    AI模型名称，可为空
     * @param temperature  温度参数，可为空
     * @param maxTokens    最大token数，可为空
     * @return Agent聚合根
     */
    AgentAggregate createAgent(
            String code,
            String name,
            String description,
            String systemPrompt,
            String modelName,
            BigDecimal temperature,
            Integer maxTokens);

    /**
     * 根据ID查找Agent
     *
     * @param agentId Agent ID，不能为空
     * @return Agent聚合根
     */
    AgentAggregate findByIdOrThrow(String agentId);

    /**
     * 根据编码查找Agent
     *
     * @param code Agent编码，不能为空
     * @return Agent聚合根
     */
    AgentAggregate findByCodeOrThrow(String code);

    /**
     * 更新Agent信息
     *
     * @param agentId      Agent ID，不能为空
     * @param name         新Agent名称，不能为空
     * @param description  新Agent描述，可为空
     * @param systemPrompt 新系统提示词，不能为空
     * @param modelName    新AI模型名称，可为空
     * @param temperature  新温度参数，可为空
     * @param maxTokens    新最大token数，可为空
     * @return 更新后的Agent聚合根
     */
    AgentAggregate updateAgent(
            String agentId,
            String name,
            String description,
            String systemPrompt,
            String modelName,
            BigDecimal temperature,
            Integer maxTokens);

    /**
     * 启用Agent
     *
     * @param agentId Agent ID，不能为空
     * @return 启用后的Agent聚合根
     */
    AgentAggregate enableAgent(String agentId);

    /**
     * 禁用Agent
     *
     * @param agentId Agent ID，不能为空
     * @return 禁用后的Agent聚合根
     */
    AgentAggregate disableAgent(String agentId);

    /**
     * 添加工具到Agent
     *
     * @param agentId    Agent ID，不能为空
     * @param toolCode   工具编码，不能为空
     * @param toolName   工具名称，不能为空
     * @param toolType   工具类型，不能为空
     * @param toolConfig 工具配置，可为空
     * @return 创建的AgentTool对象
     */
    AgentTool addTool(
            String agentId,
            String toolCode,
            String toolName,
            ToolType toolType,
            String toolConfig);

    /**
     * 从Agent移除工具
     *
     * @param agentId  Agent ID，不能为空
     * @param toolCode 工具编码，不能为空
     * @return 更新后的Agent聚合根
     */
    AgentAggregate removeTool(String agentId, String toolCode);

    /**
     * 检查Agent编码是否唯一
     *
     * @param code Agent编码，不能为空
     * @return 如果Agent编码唯一返回true，否则返回false
     */
    boolean isAgentCodeUnique(String code);

}
