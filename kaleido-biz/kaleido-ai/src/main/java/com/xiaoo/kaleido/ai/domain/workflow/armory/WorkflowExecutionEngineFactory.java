package com.xiaoo.kaleido.ai.domain.workflow.armory;

import com.xiaoo.kaleido.ai.domain.chat.service.IChatService;
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
     * @param definition  工作流定义
     * @param chatService 聊天服务
     * @return 工作流执行引擎
     */
    public WorkflowExecutionEngine createEngine(WorkflowDefinition definition, IChatService chatService) {
        log.info("创建工作流执行引擎，工作流名称: {}, 步骤数: {}",
                definition.getName(), definition.getStepCount());

        return new WorkflowExecutionEngine(definition, chatService);
    }
}
