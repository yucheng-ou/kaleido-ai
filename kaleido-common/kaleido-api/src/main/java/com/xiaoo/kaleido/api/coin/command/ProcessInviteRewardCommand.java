package com.xiaoo.kaleido.api.coin.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 处理邀请奖励命令
 * <p>
 * 用于处理用户邀请新用户注册的奖励
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInviteRewardCommand extends BaseCommand {

    /**
     * 邀请人用户ID
     */
    @NotBlank(message = "邀请人用户ID不能为空")
    @Size(max = 64, message = "邀请人用户ID长度不能超过64个字符")
    private String inviterUserId;

    /**
     * 新用户ID
     */
    @NotBlank(message = "新用户ID不能为空")
    @Size(max = 64, message = "新用户ID长度不能超过64个字符")
    private String newUserId;
}
