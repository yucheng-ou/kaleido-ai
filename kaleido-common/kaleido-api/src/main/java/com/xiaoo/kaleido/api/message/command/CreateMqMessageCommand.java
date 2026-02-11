package com.xiaoo.kaleido.api.message.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

/**
 * 创建MQ消息命令
 *
 * @author ouyucheng
 * @date 2026/2/11
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateMqMessageCommand extends BaseCommand {

    /**
     * 消息主题
     */
    @NotBlank(message = "消息主题不能为空")
    private String topic;

    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String message;
}
