package com.xiaoo.kaleido.ai.domain.model.aggregate;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.ai.domain.model.valobj.WorkflowStatus;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * AI工作流聚合根
 * <p>
 * AI工作流领域模型的核心聚合根，封装工作流定义及其执行信息
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WorkflowAggregate extends BaseEntity {


    /**
     * 工作流编码（唯一）
     */
    private String code;

    /**
     * 工作流名称
     */
    private String name;

    /**
     * 工作流描述
     */
    private String description;

    /**
     * 工作流DSL定义（JSON格式）
     */
    private String definition;

    /**
     * 工作流状态
     */
    private WorkflowStatus status;

    /**
     * 创建新工作流聚合根
     * <p>
     * 用于创建新工作流时构建聚合根
     *
     * @param code        工作流编码，不能为空
     * @param name        工作流名称，不能为空
     * @param description 工作流描述，可为空
     * @param definition  工作流DSL定义，不能为空
     * @return 工作流聚合根
     * @throws AiException 当参数无效时抛出
     */
    public static WorkflowAggregate create(
            String code,
            String name,
            String description,
            String definition) {
        
        if (StrUtil.isBlank(code)) {
            throw AiException.of(AiErrorCode.WORKFLOW_CODE_EMPTY, "工作流编码不能为空");
        }
        if (StrUtil.isBlank(name)) {
            throw AiException.of(AiErrorCode.WORKFLOW_NAME_EMPTY, "工作流名称不能为空");
        }
        if (StrUtil.isBlank(definition)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流定义不能为空");
        }

        return WorkflowAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .code(code.trim())
                .name(name.trim())
                .description(description != null ? description.trim() : null)
                .definition(definition.trim())
                .status(WorkflowStatus.NORMAL)
                .build();
    }

    /**
     * 更新工作流信息
     * <p>
     * 更新工作流的名称、描述和定义
     *
     * @param name        新工作流名称，不能为空
     * @param description 新工作流描述，可为空
     * @param definition  新工作流定义，不能为空
     * @throws AiException 如果工作流状态不允许修改
     */
    public void updateInfo(String name, String description, String definition) {
        if (StrUtil.isBlank(name)) {
            throw AiException.of(AiErrorCode.WORKFLOW_NAME_EMPTY, "工作流名称不能为空");
        }
        if (StrUtil.isBlank(definition)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "工作流定义不能为空");
        }

        this.name = name.trim();
        this.description = description != null ? description.trim() : null;
        this.definition = definition.trim();
    }

    /**
     * 启用工作流
     * <p>
     * 将工作流状态设置为NORMAL
     */
    public void enable() {
        this.status = WorkflowStatus.NORMAL;
    }

    /**
     * 禁用工作流
     * <p>
     * 将工作流状态设置为DISABLED
     */
    public void disable() {
        this.status = WorkflowStatus.DISABLED;
    }

    /**
     * 检查工作流是否启用
     *
     * @return 如果工作流状态为NORMAL返回true，否则返回false
     */
    public boolean isEnabled() {
        return WorkflowStatus.NORMAL.equals(this.status);
    }

    /**
     * 验证工作流定义格式
     * <p>
     * 检查工作流定义是否为有效的JSON格式
     *
     * @return 如果工作流定义格式有效返回true，否则返回false
     */
    public boolean isValidDefinition() {
        if (StrUtil.isBlank(this.definition)) {
            return false;
        }

        String trimmed = this.definition.trim();
        // 简单的JSON格式检查
        return (trimmed.startsWith("{") && trimmed.endsWith("}")) ||
               (trimmed.startsWith("[") && trimmed.endsWith("]"));
    }

    /**
     * 获取工作流定义长度
     *
     * @return 工作流定义字符串长度
     */
    public int getDefinitionLength() {
        return this.definition != null ? this.definition.length() : 0;
    }

    /**
     * 检查工作流编码是否匹配
     *
     * @param code 工作流编码
     * @return 如果工作流编码匹配返回true，否则返回false
     */
    public boolean matchesCode(String code) {
        return this.code.equals(code);
    }
}
