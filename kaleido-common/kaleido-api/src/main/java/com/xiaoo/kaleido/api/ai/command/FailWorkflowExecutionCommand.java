package com.xiaoo.kaleido.api.ai.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 工作流执行失败命令
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FailWorkflowExecutionCommand extends BaseCommand {

    /**
     * 错误信息
     */
    @NotBlank(message = "错误信息不能为空")
    @Size(max = 1000, message = "错误信息长度不能超过1000个字符")
    private String errorMessage;
}
