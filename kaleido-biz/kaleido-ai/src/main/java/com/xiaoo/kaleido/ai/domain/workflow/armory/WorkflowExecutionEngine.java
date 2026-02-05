package com.xiaoo.kaleido.ai.domain.workflow.armory;

import com.xiaoo.kaleido.ai.domain.agent.armory.AgentFactory;
import com.xiaoo.kaleido.ai.domain.chat.service.IChatService;
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
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Slf4j
public class WorkflowExecutionEngine {

    /**
     * 工作流定义
     * -- GETTER --
     * 获取工作流定义
     */
    @Getter
    private final WorkflowDefinition definition;

    /**
     * Agent工厂
     */
    private final IChatService chatService;

    /**
     * 步骤执行结果缓存
     */
    private final Map<String, String> stepResults = new HashMap<>();

    /**
     * 构造函数
     *
     * @param definition  工作流定义
     * @param chatService Agent工厂
     */
    public WorkflowExecutionEngine(WorkflowDefinition definition, IChatService chatService) {
        this.definition = definition;
        this.chatService = chatService;

        if (!definition.isValid()) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流定义无效");
        }
    }

    /**
     * 执行工作流
     *
     * @param inputData 输入数据
     * @return 执行结果
     */
    public String execute(String inputData, String userId) {
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
                finalResult = executeStep(step, inputData, i + 1, steps, userId);
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
     * @param step         步骤定义
     * @param initialInput 初始输入数据（用于第一个步骤）
     * @param stepIndex    步骤索引（从1开始）
     * @param steps        所有步骤列表（用于获取上一个步骤的输出）
     * @return 步骤执行结果
     */
    private String executeStep(WorkflowStep step, String initialInput, int stepIndex, List<WorkflowStep> steps, String userId) {
        // 获取步骤输入（自动链式传递）
        String stepInput = getStepInput(step, initialInput, stepIndex, steps);

        String stepResult = chatService.chatWithWorkflowNode(step.getAgentId(), stepInput, userId);

        // 缓存步骤结果
        stepResults.put(step.getId(), stepResult);

        return stepResult;
    }

    /**
     * 获取步骤输入（自动链式传递）
     * 第一个步骤使用用户输入，后续步骤使用上一个步骤的输出
     *
     * @param step         步骤定义
     * @param initialInput 初始输入数据
     * @param stepIndex    步骤索引（从1开始）
     * @param steps        所有步骤列表
     * @return 步骤输入
     */
    private String getStepInput(WorkflowStep step, String initialInput, int stepIndex, List<WorkflowStep> steps) {
        if (stepIndex == 1) {
            // 第一个步骤使用初始输入
            return initialInput != null ? initialInput : "";
        } else {
            // 后续步骤使用上一个步骤的输出
            // 获取上一个步骤
            WorkflowStep previousStep = steps.get(stepIndex - 2); // stepIndex从1开始，所以减2
            String previousStepId = previousStep.getId();
            String previousResult = stepResults.get(previousStepId);

            if (previousResult == null) {
                throw AiException.of(AiErrorCode.VALIDATION_ERROR,
                        "无法获取输入：上一个步骤结果未找到，步骤ID: " + step.getId() +
                                ", 上一个步骤ID: " + previousStepId);
            }

            return previousResult;
        }
    }

    /**
     * 执行Agent
     *
     * @param chatClient Agent ChatClient
     * @param input      输入数据
     * @param step       步骤定义
     * @return Agent执行结果
     */
    private String executeAgent(ChatClient chatClient, String input, WorkflowStep step) {
        try {
            // 调用Agent ChatClient
            return chatClient.prompt()
                    .user(input)
                    .call()
                    .content();
        } catch (Exception e) {
            log.error("Agent执行失败，步骤ID: {}, Agent ID: {}, 错误: {}",
                    step.getId(), step.getAgentId(), e.getMessage(), e);
            throw AiException.of(AiErrorCode.AI_MODEL_ERROR,
                    "Agent执行失败，步骤ID: " + step.getId() + ", Agent ID: " + step.getAgentId() +
                            ", 错误: " + e.getMessage());
        }
    }
}
