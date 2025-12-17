package com.xiaoo.kaleido.notice.domain.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 通知模板实体
 * <p>
 * 可重用的通知模板，支持变量替换
 * 包含模板内容、模板类型、变量定义等
 * </p>
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Data
@Builder
public class NoticeTemplate {
    /**
     * 模板ID
     */
    private final String id;

    /**
     * 模板名称
     */
    private final String name;

    /**
     * 模板类型
     */
    private final String type;

    /**
     * 模板标题
     */
    private final String title;

    /**
     * 模板内容
     */
    private final String content;

    /**
     * 模板变量定义
     */
    private final Map<String, String> variableDefinitions;

    /**
     * 模板示例
     */
    private final String example;

    /**
     * 是否启用
     */
    private final boolean enabled;

    /**
     * 创建时间
     */
    private final LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private final LocalDateTime updatedAt;

    /**
     * 创建通知模板实体
     *
     * @param id                   模板ID
     * @param name                 模板名称
     * @param type                 模板类型
     * @param title                模板标题
     * @param content              模板内容
     * @param variableDefinitions  变量定义
     * @param example              模板示例
     * @param enabled              是否启用
     * @param createdAt            创建时间
     * @param updatedAt            更新时间
     */
    public NoticeTemplate(String id, String name, String type, String title, String content,
                         Map<String, String> variableDefinitions, String example, boolean enabled,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.title = title;
        this.content = content;
        this.variableDefinitions = variableDefinitions;
        this.example = example;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * 验证模板变量是否匹配定义
     *
     * @param variables 传入的变量
     * @return 是否匹配
     */
    public boolean validateVariables(Map<String, Object> variables) {
        if (variableDefinitions == null || variableDefinitions.isEmpty()) {
            return variables == null || variables.isEmpty();
        }

        if (variables == null) {
            return false;
        }

        // 检查所有必需的变量是否都存在
        for (String requiredKey : variableDefinitions.keySet()) {
            if (!variables.containsKey(requiredKey)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 渲染模板
     *
     * @param variables 模板变量
     * @return 渲染后的内容
     */
    public String render(Map<String, Object> variables) {
        if (!validateVariables(variables)) {
            throw new IllegalArgumentException("模板变量验证失败");
        }

        String rendered = content;
        if (variables != null) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                String key = "\\{" + entry.getKey() + "\\}";
                String value = entry.getValue() != null ? entry.getValue().toString() : "";
                rendered = rendered.replaceAll(key, value);
            }
        }
        return rendered;
    }

    /**
     * 创建新的通知模板
     *
     * @param id                   模板ID
     * @param name                 模板名称
     * @param type                 模板类型
     * @param title                模板标题
     * @param content              模板内容
     * @param variableDefinitions  变量定义
     * @param example              模板示例
     * @return 新的通知模板实体
     */
    public static NoticeTemplate create(String id, String name, String type, String title, String content,
                                       Map<String, String> variableDefinitions, String example) {
        LocalDateTime now = LocalDateTime.now();
        return NoticeTemplate.builder()
                .id(id)
                .name(name)
                .type(type)
                .title(title)
                .content(content)
                .variableDefinitions(variableDefinitions)
                .example(example)
                .enabled(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    /**
     * 禁用模板
     *
     * @return 新的通知模板实体（禁用状态）
     */
    public NoticeTemplate disable() {
        return NoticeTemplate.builder()
                .id(this.id)
                .name(this.name)
                .type(this.type)
                .title(this.title)
                .content(this.content)
                .variableDefinitions(this.variableDefinitions)
                .example(this.example)
                .enabled(false)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * 启用模板
     *
     * @return 新的通知模板实体（启用状态）
     */
    public NoticeTemplate enable() {
        return NoticeTemplate.builder()
                .id(this.id)
                .name(this.name)
                .type(this.type)
                .title(this.title)
                .content(this.content)
                .variableDefinitions(this.variableDefinitions)
                .example(this.example)
                .enabled(true)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
