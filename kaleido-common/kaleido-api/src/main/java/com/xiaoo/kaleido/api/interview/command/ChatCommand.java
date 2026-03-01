package com.xiaoo.kaleido.api.interview.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 招聘助手聊天命令
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatCommand extends BaseCommand {

    /**
     * 会话ID（用于维护聊天上下文）
     */
    @NotBlank(message = "会话ID不能为空")
    @Size(max = 100, message = "会话ID长度不能超过100个字符")
    private String sessionId;

    /**
     * 用户消息
     */
    @NotBlank(message = "消息不能为空")
    @Size(max = 5000, message = "消息长度不能超过5000个字符")
    private String message;
}
