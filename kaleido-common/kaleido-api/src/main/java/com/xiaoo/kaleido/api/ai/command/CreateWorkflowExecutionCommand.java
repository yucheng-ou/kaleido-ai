package com.xiaoo.kaleido.api.ai.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 创建工作流执行命令
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkflowExecutionCommand extends BaseCommand {

    /**
     * 执行ID（业务唯一）
     */
    @NotBlank(message = "执行ID不能为空")
    @Size(max = 50, message = "执行ID长度不能超过50个字符")
    private String executionId;

    /**
     * 工作流ID
     */
    @NotBlank(message = "工作流ID不能为空")
    @Size(max = 50, message = "工作流ID长度不能超过50个字符")
    private String workflowId;

    /**
     * 输入数据
     */
    @Size(max = 5000, message = "输入数据长度不能超过5000个字符")
    private String inputData;
}
