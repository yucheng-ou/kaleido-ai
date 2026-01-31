package com.xiaoo.kaleido.api.ai.command;

import com.xiaoo.kaleido.api.ai.enums.ToolType;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 添加Agent工具命令
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddAgentToolCommand extends BaseCommand {

    /**
     * 工具编码
     */
    @NotBlank(message = "工具编码不能为空")
    @Size(max = 50, message = "工具编码长度不能超过50个字符")
    private String toolCode;

    /**
     * 工具名称
     */
    @NotBlank(message = "工具名称不能为空")
    @Size(max = 100, message = "工具名称长度不能超过100个字符")
    private String toolName;

    /**
     * 工具类型
     */
    @NotNull(message = "工具类型不能为空")
    private ToolType toolType;

    /**
     * 工具配置
     */
    @Size(max = 5000, message = "工具配置长度不能超过5000个字符")
    private String toolConfig;
}
