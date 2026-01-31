package com.xiaoo.kaleido.api.ai.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

/**
 * 创建Agent命令
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAgentCommand extends BaseCommand {

    /**
     * Agent编码
     */
    @NotBlank(message = "Agent编码不能为空")
    @Size(max = 50, message = "Agent编码长度不能超过50个字符")
    private String code;

    /**
     * Agent名称
     */
    @NotBlank(message = "Agent名称不能为空")
    @Size(max = 100, message = "Agent名称长度不能超过100个字符")
    private String name;

    /**
     * Agent描述
     */
    @Size(max = 500, message = "Agent描述长度不能超过500个字符")
    private String description;

    /**
     * 系统提示词
     */
    @NotBlank(message = "系统提示词不能为空")
    @Size(max = 5000, message = "系统提示词长度不能超过5000个字符")
    private String systemPrompt;

    /**
     * AI模型名称
     */
    @Size(max = 100, message = "AI模型名称长度不能超过100个字符")
    private String modelName;

    /**
     * 温度参数
     */
    private BigDecimal temperature;

    /**
     * 最大token数
     */
    private Integer maxTokens;
}
