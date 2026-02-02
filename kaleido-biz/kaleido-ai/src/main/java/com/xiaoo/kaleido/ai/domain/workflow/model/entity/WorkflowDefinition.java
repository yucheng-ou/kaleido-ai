package com.xiaoo.kaleido.ai.domain.workflow.model.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工作流定义
 * <p>
 * 定义工作流的完整结构，包括版本、名称、描述和步骤
 *
 * @author ouyucheng
 * @date 2026/2/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDefinition {

    /**
     * 工作流版本
     */
    private String version;

    /**
     * 工作流名称
     */
    private String name;

    /**
     * 工作流描述
     */
    private String description;

    /**
     * 工作流步骤列表
     */
    private List<WorkflowStep> steps;

    /**
     * 从JSON字符串解析工作流定义
     *
     * @param json JSON字符串
     * @return 工作流定义
     * @throws JsonProcessingException 如果JSON解析失败
     */
    public static WorkflowDefinition fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, WorkflowDefinition.class);
    }

    /**
     * 将工作流定义转换为JSON字符串
     *
     * @return JSON字符串
     * @throws JsonProcessingException 如果JSON序列化失败
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    /**
     * 验证工作流定义是否有效
     *
     * @return 如果工作流定义有效返回true，否则返回false
     */
    public boolean isValid() {
        if (version == null || version.trim().isEmpty()) {
            return false;
        }
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (steps == null || steps.isEmpty()) {
            return false;
        }

        // 验证所有步骤
        for (WorkflowStep step : steps) {
            if (!step.isValid()) {
                return false;
            }
        }

        // 验证步骤ID唯一性
        List<String> stepIds = steps.stream()
                .map(WorkflowStep::getId)
                .collect(Collectors.toList());
        if (stepIds.size() != stepIds.stream().distinct().count()) {
            return false;
        }

        return true;
    }

    /**
     * 获取步骤数量
     *
     * @return 步骤数量
     */
    public int getStepCount() {
        return steps != null ? steps.size() : 0;
    }

    /**
     * 根据步骤ID获取步骤
     *
     * @param stepId 步骤ID
     * @return 步骤，如果不存在返回null
     */
    public WorkflowStep getStepById(String stepId) {
        if (steps == null || stepId == null) {
            return null;
        }
        return steps.stream()
                .filter(step -> stepId.equals(step.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取步骤执行顺序映射
     *
     * @return 步骤ID到执行顺序的映射
     */
    public Map<String, Integer> getStepOrderMap() {
        if (steps == null) {
            return Map.of();
        }
        return steps.stream()
                .collect(Collectors.toMap(
                        WorkflowStep::getId,
                        step -> step.getOrder() != null ? step.getOrder() : 0
                ));
    }

    /**
     * 获取按执行顺序排序的步骤列表
     *
     * @return 按执行顺序排序的步骤列表
     */
    public List<WorkflowStep> getSortedSteps() {
        if (steps == null) {
            return new ArrayList<>();
        }
        return steps.stream()
                .sorted((s1, s2) -> {
                    int order1 = s1.getOrder() != null ? s1.getOrder() : 0;
                    int order2 = s2.getOrder() != null ? s2.getOrder() : 0;
                    return Integer.compare(order1, order2);
                })
                .collect(Collectors.toList());
    }

    /**
     * 检查工作流是否包含指定步骤
     *
     * @param stepId 步骤ID
     * @return 如果包含返回true，否则返回false
     */
    public boolean containsStep(String stepId) {
        return getStepById(stepId) != null;
    }
}
