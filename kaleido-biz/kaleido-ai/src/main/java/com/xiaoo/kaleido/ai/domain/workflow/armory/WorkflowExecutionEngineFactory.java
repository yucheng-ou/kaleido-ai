package com.xiaoo.kaleido.ai.domain.workflow.armory;

import com.xiaoo.kaleido.ai.domain.agent.armory.AgentFactory;
import com.xiaoo.kaleido.ai.domain.workflow.model.entity.WorkflowDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 工作流执行引擎工厂
 * <p>
 * 负责创建工作流执行引擎实例
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Slf4j
@Component
public class WorkflowExecutionEngineFactory {

    /**
     * 创建工作流执行引擎
     *
     * @param definition 工作流定义
     * @param agentFactory Agent工厂
     * @return 工作流执行引擎
     */
    public WorkflowExecutionEngine createEngine(WorkflowDefinition definition, AgentFactory agentFactory) {
        log.info("创建工作流执行引擎，工作流名称: {}, 步骤数: {}", 
                definition.getName(), definition.getStepCount());
        
        WorkflowExecutionEngine engine = new WorkflowExecutionEngine(definition, agentFactory);
        
        log.debug("工作流执行引擎创建成功，工作流名称: {}", definition.getName());
        return engine;
    }

    /**
     * 创建工作流执行引擎（简化版本）
     *
     * @param definition 工作流定义
     * @param agentFactory Agent工厂
     * @return 工作流执行引擎
     */
    public WorkflowExecutionEngine createSimpleEngine(WorkflowDefinition definition, AgentFactory agentFactory) {
        return createEngine(definition, agentFactory);
    }
}
