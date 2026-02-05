package com.xiaoo.kaleido.api.ai.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 执行工作流命令
 *
 * @author ouyucheng
 * @date 2026/2/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteWorkflowCommand extends BaseCommand {

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
