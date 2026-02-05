package com.xiaoo.kaleido.ai.application.command.executor;

import com.xiaoo.kaleido.api.ai.command.ExecuteWorkflowCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 工作流执行器工厂
 * 负责创建和管理工作流执行器实例
 * 
 * @author ouyucheng
 * @date 2026/2/5
 */
@Component
@RequiredArgsConstructor
public class WorkflowExecutorFactory {
    
    private final ApplicationContext applicationContext;
    
    /**
     * 创建通用工作流执行器
     * 
     * @param command 执行工作流命令
     * @param userId 用户ID
     * @return 通用工作流执行器实例
     */
    public GenericWorkflowExecutor createGenericExecutor(ExecuteWorkflowCommand command, String userId) {
        GenericWorkflowExecutor executor = applicationContext.getBean(GenericWorkflowExecutor.class);
        executor.setExecutionParams(command, userId);
        return executor;
    }
    
    /**
     * 创建服装推荐工作流执行器
     * 
     * @param prompt 用户输入的推荐需求提示词
     * @param userId 用户ID
     * @return 服装推荐工作流执行器实例
     */
    public OutfitRecommendWorkflowExecutor createOutfitRecommendExecutor(String prompt, String userId) {
        OutfitRecommendWorkflowExecutor executor = applicationContext.getBean(OutfitRecommendWorkflowExecutor.class);
        executor.setExecutionParams(prompt, userId);
        return executor;
    }
}
