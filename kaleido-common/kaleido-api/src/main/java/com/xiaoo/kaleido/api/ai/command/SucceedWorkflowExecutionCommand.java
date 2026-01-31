package com.xiaoo.kaleido.api.ai.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 工作流执行成功命令
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SucceedWorkflowExecutionCommand extends BaseCommand {

    /**
     * 输出数据
     */
    @Size(max = 5000, message = "输出数据长度不能超过5000个字符")
    private String outputData;
}
