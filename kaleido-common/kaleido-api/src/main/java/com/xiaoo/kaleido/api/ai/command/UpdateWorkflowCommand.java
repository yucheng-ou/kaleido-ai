package com.xiaoo.kaleido.api.ai.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 更新工作流命令
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkflowCommand extends BaseCommand {

    /**
     * 工作流名称
     */
    @NotBlank(message = "工作流名称不能为空")
    @Size(max = 100, message = "工作流名称长度不能超过100个字符")
    private String name;

    /**
     * 工作流描述
     */
    @Size(max = 500, message = "工作流描述长度不能超过500个字符")
    private String description;

    /**
     * 工作流DSL定义
     */
    @NotBlank(message = "工作流定义不能为空")
    @Size(max = 5000, message = "工作流定义长度不能超过5000个字符")
    private String definition;
}
