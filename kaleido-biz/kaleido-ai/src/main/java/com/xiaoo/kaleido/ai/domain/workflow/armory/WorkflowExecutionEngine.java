package com.xiaoo.kaleido.ai.domain.workflow.armory;

import com.xiaoo.kaleido.ai.domain.agent.armory.AgentFactory;
import com.xiaoo.kaleido.ai.domain.workflow.model.entity.StepInput;
import com.xiaoo.kaleido.ai.domain.workflow.model.entity.WorkflowDefinition;
import com.xiaoo.kaleido.ai.domain.workflow.model.entity.WorkflowStep;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作流执行引擎
 * <p>
 * 负责解析工作流定义并执行工作流步骤
 * 支持步骤间的数据传递：上一个agent的输出作为下一个agent的输入
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Slf4j
public class WorkflowExecutionEngine {

    /**
     * 工作流定义
     * -- GETTER --
     *  获取工作流定义
     *
     */
    @Getter
    private final WorkflowDefinition definition;

    /**
     * Agent工厂
     */
    private final AgentFactory agentFactory;

    /**
     * 步骤执行结果缓存
     */
    private final Map<String, String> stepResults = new HashMap<>();

    /**
     * 构造函数
     *
     * @param definition 工作流定义
     * @param agentFactory Agent工厂
     */
    public WorkflowExecutionEngine(WorkflowDefinition definition, AgentFactory agentFactory) {
        this.definition = definition;
        this.agentFactory = agentFactory;
        
        if (!definition.isValid()) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流定义无效");
        }
        
        log.debug("工作流执行引擎初始化完成，工作流名称: {}, 步骤数: {}", 
                definition.getName(), definition.getStepCount());
    }

    /**
     * 执行工作流
     *
     * @param inputData 输入数据
     * @return 执行结果
     */
    public String execute(String inputData) {
        log.info("开始执行工作流，工作流名称: {}, 输入数据长度: {}", 
                definition.getName(), inputData != null ? inputData.length() : 0);
        
        // 清空之前的执行结果
        stepResults.clear();
        
        // 获取按顺序排序的步骤
        List<WorkflowStep> steps = definition.getSortedSteps();
        
        String finalResult = null;
        
        try {
            // 按顺序执行每个步骤
            for (int i = 0; i < steps.size(); i++) {
                WorkflowStep step = steps.get(i);
                finalResult = executeStep(step, inputData, i + 1, steps.size());
            }
            
            log.info("工作流执行完成，工作流名称: {}, 步骤数: {}, 最终结果长度: {}", 
                    definition.getName(), steps.size(), finalResult != null ? finalResult.length() : 0);
            
            return finalResult;
        } catch (Exception e) {
            log.error("工作流执行失败，工作流名称: {}, 错误: {}", definition.getName(), e.getMessage(), e);
            throw AiException.of(AiErrorCode.WORKFLOW_EXECUTION_ERROR, "工作流执行失败: " + e.getMessage());
        }
    }

    /**
     * 执行单个步骤
     *
     * @param step 步骤定义
     * @param initialInput 初始输入数据（用于第一个步骤）
     * @param stepIndex 步骤索引（从1开始）
     * @param totalSteps 总步骤数
     * @return 步骤执行结果
     */
    private String executeStep(WorkflowStep step, String initialInput, int stepIndex, int totalSteps) {
        log.debug("开始执行步骤，步骤ID: {}, 名称: {}, 步骤 {}/{}", 
                step.getId(), step.getName(), stepIndex, totalSteps);
        
        // 获取步骤输入
        String stepInput = getStepInput(step, initialInput);
        
        // 获取Agent ChatClient
        ChatClient chatClient = agentFactory.getChatClient(step.getAgentId());
        if (chatClient == null) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, 
                    "Agent未找到或未启用，Agent ID: " + step.getAgentId());
        }
        
        // 执行Agent
        String stepResult = executeAgent(chatClient, stepInput, step);
        
        // 缓存步骤结果
        stepResults.put(step.getId(), stepResult);
        
        log.debug("步骤执行完成，步骤ID: {}, 名称: {}, 输入长度: {}, 输出长度: {}", 
                step.getId(), step.getName(), 
                stepInput != null ? stepInput.length() : 0,
                stepResult != null ? stepResult.length() : 0);
        
        return stepResult;
    }

    /**
     * 获取步骤输入
     *
     * @param step 步骤定义
     * @param initialInput 初始输入数据
     * @return 步骤输入
     */
    private String getStepInput(WorkflowStep step, String initialInput) {
        StepInput stepInputConfig = step.getInput();
        if (stepInputConfig == null) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, 
                    "步骤输入配置不能为空，步骤ID: " + step.getId());
        }
        
        StepInput.InputType inputType = stepInputConfig.getType();
        if (inputType == null) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, 
                    "步骤输入类型不能为空，步骤ID: " + step.getId());
        }
        
        switch (inputType) {
            case STATIC:
                return stepInputConfig.getValue() != null ? stepInputConfig.getValue() : "";
                
            case PREVIOUS_OUTPUT:
                String previousStepId = stepInputConfig.getStepId();
                if (previousStepId == null || previousStepId.trim().isEmpty()) {
                    throw AiException.of(AiErrorCode.VALIDATION_ERROR, 
                            "上一个步骤ID不能为空，步骤ID: " + step.getId());
                }
                
                String previousResult = stepResults.get(previousStepId);
                if (previousResult == null) {
                    throw AiException.of(AiErrorCode.VALIDATION_ERROR, 
                            "上一个步骤结果未找到，步骤ID: " + step.getId() + 
                            ", 上一个步骤ID: " + previousStepId);
                }
                
                return previousResult;
                
            default:
                throw AiException.of(AiErrorCode.VALIDATION_ERROR, 
                        "不支持的输入类型: " + inputType + ", 步骤ID: " + step.getId());
        }
    }

    /**
     * 执行Agent
     *
     * @param chatClient Agent ChatClient
     * @param input 输入数据
     * @param step 步骤定义
     * @return Agent执行结果
     */
    private String executeAgent(ChatClient chatClient, String input, WorkflowStep step) {
        try {
            log.debug("开始执行Agent，步骤ID: {}, Agent ID: {}, 输入长度: {}", 
                    step.getId(), step.getAgentId(), input != null ? input.length() : 0);
            
            // 调用Agent ChatClient
            String result = chatClient.prompt()
                    .user(input)
                    .call()
                    .content();
            
            log.debug("Agent执行完成，步骤ID: {}, Agent ID: {}, 输出长度: {}", 
                    step.getId(), step.getAgentId(), result != null ? result.length() : 0);
            
            return result;
        } catch (Exception e) {
            log.error("Agent执行失败，步骤ID: {}, Agent ID: {}, 错误: {}", 
                    step.getId(), step.getAgentId(), e.getMessage(), e);
            throw AiException.of(AiErrorCode.AI_MODEL_ERROR, 
                    "Agent执行失败，步骤ID: " + step.getId() + ", Agent ID: " + step.getAgentId() + 
                    ", 错误: " + e.getMessage());
        }
    }

    /**
     * 获取步骤执行结果
     *
     * @param stepId 步骤ID
     * @return 步骤执行结果，如果未找到返回null
     */
    public String getStepResult(String stepId) {
        return stepResults.get(stepId);
    }

    /**
     * 获取所有步骤执行结果
     *
     * @return 步骤执行结果映射
     */
    public Map<String, String> getAllStepResults() {
        return new HashMap<>(stepResults);
    }

    /**
     * 清空步骤执行结果
     */
    public void clearResults() {
        stepResults.clear();
    }

    /**
     * 验证工作流定义
     *
     * @return 如果工作流定义有效返回true，否则返回false
     */
    public boolean isValid() {
        return definition != null && definition.isValid();
    }

    /**
     * 获取工作流名称
     *
     * @return 工作流名称
     */
    public String getWorkflowName() {
        return definition != null ? definition.getName() : null;
    }

    /**
     * 获取步骤数量
     *
     * @return 步骤数量
     */
    public int getStepCount() {
        return definition != null ? definition.getStepCount() : 0;
    }
}
