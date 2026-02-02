package com.xiaoo.kaleido.ai.domain.workflow.armory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaoo.kaleido.ai.domain.agent.armory.AgentFactory;
import com.xiaoo.kaleido.ai.domain.workflow.model.aggregate.WorkflowAggregate;
import com.xiaoo.kaleido.ai.domain.workflow.model.entity.WorkflowDefinition;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工作流工厂
 * <p>
 * 负责管理工作流的装配和执行
 * 提供工作流定义的缓存和执行实例的管理
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Slf4j
@Component
public class WorkflowFactory {

    /**
     * 工作流定义缓存
     * key: 工作流ID
     * value: 工作流定义
     */
    private final Map<String, WorkflowDefinition> workflowDefinitionCache = new ConcurrentHashMap<>();

    /**
     * 工作流执行引擎缓存
     * key: 工作流ID
     * value: 工作流执行引擎
     */
    private final Map<String, WorkflowExecutionEngine> workflowEngineCache = new ConcurrentHashMap<>();

    private final AgentFactory agentFactory;
    private final WorkflowExecutionEngineFactory workflowExecutionEngineFactory;

    /**
     * 构造函数
     */
    public WorkflowFactory(AgentFactory agentFactory, WorkflowExecutionEngineFactory workflowExecutionEngineFactory) {
        this.agentFactory = agentFactory;
        this.workflowExecutionEngineFactory = workflowExecutionEngineFactory;
    }

    /**
     * 初始化方法
     */
    @PostConstruct
    public void init() {
        log.info("工作流工厂初始化完成");
    }

    /**
     * 注册工作流
     *
     * @param workflow 工作流聚合根
     */
    public void registerWorkflow(WorkflowAggregate workflow) {
        String workflowId = workflow.getId();
        if (isWorkflowRegistered(workflowId)) {
            log.debug("工作流已注册，跳过注册，工作流ID: {}", workflowId);
            return;
        }

        try {
            // 解析工作流定义
            WorkflowDefinition definition = parseWorkflowDefinition(workflow);
            
            // 验证工作流定义
            if (!definition.isValid()) {
                log.warn("工作流定义无效，无法注册，工作流ID: {}", workflowId);
                return;
            }

            // 创建工作流执行引擎
            WorkflowExecutionEngine engine = workflowExecutionEngineFactory.createEngine(definition, agentFactory);

            // 缓存工作流定义和执行引擎
            workflowDefinitionCache.put(workflowId, definition);
            workflowEngineCache.put(workflowId, engine);

            log.info("工作流注册成功，工作流ID: {}, 名称: {}, 步骤数: {}", 
                    workflowId, workflow.getName(), definition.getStepCount());
        } catch (Exception e) {
            log.error("工作流注册失败，工作流ID: {}, 错误: {}", workflowId, e.getMessage(), e);
        }
    }

    /**
     * 注销工作流
     *
     * @param workflowId 工作流ID
     */
    public void unregisterWorkflow(String workflowId) {
        boolean wasPresent = workflowDefinitionCache.containsKey(workflowId);
        
        workflowDefinitionCache.remove(workflowId);
        workflowEngineCache.remove(workflowId);

        if (wasPresent) {
            log.info("工作流注销成功，工作流ID: {}", workflowId);
        } else {
            log.debug("工作流未注册，无需注销，工作流ID: {}", workflowId);
        }
    }

    /**
     * 获取工作流执行引擎
     *
     * @param workflowId 工作流ID
     * @return 工作流执行引擎，如果不存在返回null
     */
    public WorkflowExecutionEngine getWorkflowEngine(String workflowId) {
        WorkflowExecutionEngine engine = workflowEngineCache.get(workflowId);
        if (engine != null) {
            return engine;
        }

        log.debug("工作流执行引擎未找到，工作流ID: {}", workflowId);
        return null;
    }

    /**
     * 获取工作流定义
     *
     * @param workflowId 工作流ID
     * @return 工作流定义，如果不存在返回null
     */
    public WorkflowDefinition getWorkflowDefinition(String workflowId) {
        return workflowDefinitionCache.get(workflowId);
    }

    /**
     * 执行工作流
     *
     * @param workflowId 工作流ID
     * @param inputData 输入数据
     * @return 执行结果
     */
    public String executeWorkflow(String workflowId, String inputData) {
        WorkflowExecutionEngine engine = getWorkflowEngine(workflowId);
        if (engine == null) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流执行引擎未找到，工作流ID: " + workflowId);
        }

        try {
            log.info("开始执行工作流，工作流ID: {}, 输入数据长度: {}", workflowId, 
                    inputData != null ? inputData.length() : 0);
            
            String result = engine.execute(inputData);
            
            log.info("工作流执行成功，工作流ID: {}, 输出数据长度: {}", workflowId, 
                    result != null ? result.length() : 0);
            
            return result;
        } catch (Exception e) {
            log.error("工作流执行失败，工作流ID: {}, 错误: {}", workflowId, e.getMessage(), e);
            throw AiException.of(AiErrorCode.WORKFLOW_EXECUTION_ERROR, "工作流执行失败: " + e.getMessage());
        }
    }

    /**
     * 刷新工作流
     *
     * @param workflowId 工作流ID
     */
    public void refreshWorkflow(String workflowId) {
        log.debug("开始刷新工作流，工作流ID: {}", workflowId);
        unregisterWorkflow(workflowId);
        
        // 注意：这里需要从数据库重新加载工作流并注册
        // 由于依赖外部服务，这里只做注销操作，重新注册由外部调用
        log.debug("工作流刷新完成，工作流ID: {}", workflowId);
    }

    /**
     * 检查工作流是否已注册
     *
     * @param workflowId 工作流ID
     * @return 如果已注册返回true，否则返回false
     */
    public boolean isWorkflowRegistered(String workflowId) {
        return workflowDefinitionCache.containsKey(workflowId);
    }

    /**
     * 获取已注册工作流数量
     *
     * @return 已注册工作流数量
     */
    public int getRegisteredWorkflowCount() {
        return workflowDefinitionCache.size();
    }

    /**
     * 清理所有工作流缓存
     */
    public void clearAll() {
        int count = workflowDefinitionCache.size();
        workflowDefinitionCache.clear();
        workflowEngineCache.clear();
        log.info("清理所有工作流缓存，清理数量: {}", count);
    }

    /**
     * 解析工作流定义
     */
    private WorkflowDefinition parseWorkflowDefinition(WorkflowAggregate workflow) throws JsonProcessingException {
        String definitionJson = workflow.getDefinition();
        if (definitionJson == null || definitionJson.trim().isEmpty()) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流定义不能为空");
        }

        return WorkflowDefinition.fromJson(definitionJson);
    }
}
