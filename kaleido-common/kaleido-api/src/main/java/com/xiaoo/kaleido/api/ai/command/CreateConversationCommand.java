package com.xiaoo.kaleido.api.ai.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 创建会话命令
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateConversationCommand extends BaseCommand {

    /**
     * 会话ID（业务唯一）
     */
    @NotBlank(message = "会话ID不能为空")
    @Size(max = 100, message = "会话ID长度不能超过100个字符")
    private String conversationId;


    /**
     * 会话标题
     */
    @Size(max = 200, message = "会话标题长度不能超过200个字符")
    private String title;
}
