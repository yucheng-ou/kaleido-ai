package com.xiaoo.kaleido.ai.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.ai.domain.agent.adapter.repository.IAgentRepository;
import com.xiaoo.kaleido.ai.domain.agent.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.domain.agent.model.entity.AgentTool;
import com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor.AgentInfraConvertor;
import com.xiaoo.kaleido.ai.infrastructure.adapter.repository.convertor.AgentToolInfraConvertor;
import com.xiaoo.kaleido.ai.infrastructure.dao.AgentDao;
import com.xiaoo.kaleido.ai.infrastructure.dao.AgentToolDao;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.AgentPO;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.AgentToolPO;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Agent仓储实现（基础设施层）
 * <p>
 * Agent仓储接口的具体实现，负责Agent聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class AgentRepositoryImpl implements IAgentRepository {

    private final AgentDao agentDao;
    private final AgentToolDao agentToolDao;

    @Override
    public void save(AgentAggregate agentAggregate) {
        // 1.转换AgentAggregate为AgentPO
        AgentPO agentPO = AgentInfraConvertor.INSTANCE.toPO(agentAggregate);

        // 2.保存Agent基本信息
        agentDao.insert(agentPO);

        log.info("Agent保存成功，Agent ID: {}, Agent编码: {}, Agent名称: {}",
                agentAggregate.getId(), agentAggregate.getCode(), agentAggregate.getName());
    }

    @Override
    public void update(AgentAggregate agentAggregate) {
        // 1.转换AgentAggregate为AgentPO
        AgentPO agentPO = AgentInfraConvertor.INSTANCE.toPO(agentAggregate);

        // 2.更新Agent基本信息
        agentDao.updateById(agentPO);

        log.info("Agent更新成功，Agent ID: {}, Agent编码: {}, Agent名称: {}",
                agentAggregate.getId(), agentAggregate.getCode(), agentAggregate.getName());
    }

    @Override
    public AgentAggregate findById(String agentId) {
        try {
            // 1.查询Agent基本信息
            AgentPO agentPO = agentDao.findById(agentId);
            if (agentPO == null) {
                return null;
            }

            // 2.转换为AgentAggregate
            AgentAggregate agentAggregate = AgentInfraConvertor.INSTANCE.toAggregate(agentPO);

            // 3.加载工具配置
            loadTools(agentAggregate);

            return agentAggregate;
        } catch (Exception e) {
            log.error("查询Agent失败，Agent ID: {}, 原因: {}", agentId, e.getMessage(), e);
            throw AiException.of(AiErrorCode.AGENT_QUERY_FAIL);
        }
    }

    @Override
    public AgentAggregate findByCode(String code) {
        try {
            // 1.查询Agent基本信息
            AgentPO agentPO = agentDao.findByCode(code);
            if (agentPO == null) {
                return null;
            }

            // 2.转换为AgentAggregate
            AgentAggregate agentAggregate = AgentInfraConvertor.INSTANCE.toAggregate(agentPO);

            // 3.加载工具配置
            loadTools(agentAggregate);

            return agentAggregate;
        } catch (Exception e) {
            log.error("查询Agent失败，Agent编码: {}, 原因: {}", code, e.getMessage(), e);
            throw AiException.of(AiErrorCode.AGENT_QUERY_FAIL);
        }
    }

    @Override
    public AgentAggregate findByIdOrThrow(String agentId) {
        AgentAggregate agent = findById(agentId);
        if (agent == null) {
            throw AiException.of(BizErrorCode.DATA_NOT_EXIST);
        }
        return agent;
    }

    @Override
    public AgentAggregate findByCodeOrThrow(String code) {
        AgentAggregate agent = findByCode(code);
        if (agent == null) {
            throw AiException.of(BizErrorCode.DATA_NOT_EXIST);
        }
        return agent;
    }

    @Override
    public boolean existsByCode(String code) {
        try {
            return agentDao.existsByCode(code);
        } catch (Exception e) {
            log.error("检查Agent编码唯一性失败，Agent编码: {}, 原因: {}", code, e.getMessage(), e);
            throw AiException.of(AiErrorCode.AGENT_QUERY_FAIL);
        }
    }

    @Override
    public List<AgentAggregate> findAllEnabled() {
        try {
            // 1.查询所有启用的Agent基本信息
            List<AgentPO> agentPOs = agentDao.findAllEnabled();

            // 2.转换为AgentAggregate列表
            return agentPOs.stream()
                    .map(AgentInfraConvertor.INSTANCE::toAggregate)
                    .peek(this::loadTools)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询所有启用的Agent失败，原因: {}", e.getMessage(), e);
            throw AiException.of(AiErrorCode.AGENT_QUERY_FAIL);
        }
    }

    @Override
    public List<AgentAggregate> findAllNotDeleted() {
        try {
            // 1.查询所有未被删除的Agent基本信息
            List<AgentPO> agentPOs = agentDao.findAllNotDeleted();

            // 2.转换为AgentAggregate列表
            return agentPOs.stream()
                    .map(AgentInfraConvertor.INSTANCE::toAggregate)
                    .peek(this::loadTools)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询所有未被删除的Agent失败，原因: {}", e.getMessage(), e);
            throw AiException.of(AiErrorCode.AGENT_QUERY_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTool(AgentTool tool) {
        // 1.转换AgentTool为AgentToolPO
        AgentToolPO toolPO = AgentToolInfraConvertor.INSTANCE.toPO(tool);

        // 2.插入单个工具
        agentToolDao.insert(toolPO);

        log.info("添加工具成功，Agent ID: {}, 工具编码: {}", tool.getAgentId(), tool.getToolCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeTool(String agentId, String toolCode) {
        // 1.删除指定工具
        int deletedCount = agentToolDao.deleteByAgentIdAndToolCode(agentId, toolCode);

        if (deletedCount == 0) {
            throw AiException.of(AiErrorCode.TOOL_NOT_FOUND,
                    "工具不存在，Agent ID: " + agentId + ", 工具编码: " + toolCode);
        }

        log.info("移除工具成功，Agent ID: {}, 工具编码: {}", agentId, toolCode);
    }

    @Override
    public List<AgentTool> findToolsByAgentId(String agentId) {
        try {
            // 1.查询工具列表
            List<AgentToolPO> toolPOs = agentToolDao.findByAgentId(agentId);

            // 2.转换为AgentTool列表
            return AgentToolInfraConvertor.INSTANCE.toEntityList(toolPOs);
        } catch (Exception e) {
            log.error("查询Agent工具失败，Agent ID: {}, 原因: {}", agentId, e.getMessage(), e);
            throw AiException.of(AiErrorCode.TOOL_QUERY_FAIL);
        }
    }

    /**
     * 加载Agent的工具配置
     *
     * @param agentAggregate Agent聚合根
     */
    private void loadTools(AgentAggregate agentAggregate) {
        List<AgentTool> tools = findToolsByAgentId(agentAggregate.getId());
        agentAggregate.getTools().addAll(tools);
    }
}
