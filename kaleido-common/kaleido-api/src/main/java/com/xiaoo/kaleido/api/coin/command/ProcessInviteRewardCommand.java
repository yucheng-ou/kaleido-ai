package com.xiaoo.kaleido.api.coin.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 处理邀请奖励命令
 * <p>
 * 用于处理用户邀请新用户注册的奖励
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInviteRewardCommand {

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
