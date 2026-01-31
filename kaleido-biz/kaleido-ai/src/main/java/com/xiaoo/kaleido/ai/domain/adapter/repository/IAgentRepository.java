package com.xiaoo.kaleido.ai.domain.adapter.repository;

import com.xiaoo.kaleido.ai.domain.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.domain.model.entity.AgentTool;

import java.util.List;

/**
 * Agent仓储接口
 * <p>
 * 定义Agent聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface IAgentRepository {

    /**
     * 保存Agent聚合根
     * <p>
     * 保存Agent聚合根到数据库，如果是新Agent则插入，如果是已存在Agent则更新
     * 注意：只保存Agent的基本信息，不处理工具配置
     *
     * @param agentAggregate Agent聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当保存失败时抛出
     */
    void save(AgentAggregate agentAggregate);

    /**
     * 更新Agent聚合根
     * <p>
     * 更新Agent聚合根信息到数据库，包括Agent基本信息和工具配置
     *
     * @param agentAggregate Agent聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当更新失败或Agent不存在时抛出
     */
    void update(AgentAggregate agentAggregate);

    /**
     * 根据ID查找Agent聚合根
     * <p>
     * 根据Agent ID查询Agent聚合根
     * 注意：需要同时加载Agent的基本信息和工具配置
     *
     * @param agentId Agent ID，不能为空
     * @return Agent聚合根，如果不存在则返回null
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    AgentAggregate findById(String agentId);

    /**
     * 根据编码查找Agent聚合根
     * <p>
     * 根据Agent编码查询Agent聚合根
     * 注意：需要同时加载Agent的基本信息和工具配置
     *
     * @param code Agent编码，不能为空
     * @return Agent聚合根，如果不存在则返回null
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    AgentAggregate findByCode(String code);

    /**
     * 根据ID查找Agent聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保Agent存在的场景，如果Agent不存在则抛出异常
     * 注意：需要同时加载Agent的基本信息和工具配置
     *
     * @param agentId Agent ID，不能为空
     * @return Agent聚合根
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当Agent不存在时抛出
     */
    AgentAggregate findByIdOrThrow(String agentId);

    /**
     * 根据编码查找Agent聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保Agent存在的场景，如果Agent不存在则抛出异常
     * 注意：需要同时加载Agent的基本信息和工具配置
     *
     * @param code Agent编码，不能为空
     * @return Agent聚合根
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当Agent不存在时抛出
     */
    AgentAggregate findByCodeOrThrow(String code);

    /**
     * 检查Agent编码是否唯一
     * <p>
     * 检查Agent编码是否唯一，用于Agent创建和更新时的业务规则校验
     * 对应数据库唯一索引：uk_code (code)
     *
     * @param code Agent编码，不能为空
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    boolean existsByCode(String code);

    /**
     * 查找所有启用的Agent
     * <p>
     * 查询所有状态为NORMAL的Agent
     *
     * @return Agent聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    List<AgentAggregate> findAllEnabled();

    /**
     * 根据工具类型查找Agent
     * <p>
     * 查询包含指定工具类型的Agent
     *
     * @param toolType 工具类型，不能为空
     * @return Agent聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    List<AgentAggregate> findByToolType(String toolType);

    /**
     * 查询所有未被删除的Agent
     * <p>
     * 查询所有未被删除的Agent列表
     *
     * @return Agent聚合根列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当查询失败时抛出
     */
    List<AgentAggregate> findAllNotDeleted();


    /**
     * 保存工具配置
     * <p>
     * 保存Agent的工具配置到数据库
     *
     * @param agent 包含工具配置的Agent聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当保存失败时抛出
     */
    void saveTools(AgentAggregate agent);

    /**
     * 删除工具配置
     * <p>
     * 删除Agent的工具配置
     *
     * @param agent 包含要删除工具配置的Agent聚合根，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当删除失败时抛出
     */
    void deleteTools(AgentAggregate agent);

    /**
     * 添加单个工具到Agent
     * <p>
     * 向指定Agent添加单个工具配置
     *
     * @param tool 工具实体，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当添加失败时抛出
     */
    void addTool(AgentTool tool);

    /**
     * 从Agent移除单个工具
     * <p>
     * 从指定Agent移除指定工具配置
     *
     * @param agentId  Agent ID，不能为空
     * @param toolCode 工具编码，不能为空
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当移除失败或工具不存在时抛出
     */
    void removeTool(String agentId, String toolCode);

    /**
     * 根据Agent ID查找工具列表
     * <p>
     * 查询指定Agent的所有工具配置
     *
     * @param agentId Agent ID，不能为空
     * @return 工具实体列表
     * @throws com.xiaoo.kaleido.ai.types.exception.AiException 当参数无效或查询失败时抛出
     */
    List<AgentTool> findToolsByAgentId(String agentId);
}
