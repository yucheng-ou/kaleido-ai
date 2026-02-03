package com.xiaoo.kaleido.api.ai.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 与Agent聊天命令
 *
 * @author ouyucheng
 * @date 2026/2/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatWithAgentCommand extends BaseCommand {

    /**
     * 用户消息
     */
    @NotBlank(message = "消息不能为空")
    @Size(max = 5000, message = "消息长度不能超过5000个字符")
    private String message;

    /**
     * 会话ID（可选）
     */
    @Size(max = 100, message = "会话ID长度不能超过100个字符")
    private String conversationId;
}
