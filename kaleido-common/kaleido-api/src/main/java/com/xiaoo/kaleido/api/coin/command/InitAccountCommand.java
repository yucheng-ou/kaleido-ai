package com.xiaoo.kaleido.api.coin.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 初始化账户命令
 * <p>
 * 用于初始化用户金币账户
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitAccountCommand extends BaseCommand {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    @Size(max = 64, message = "用户ID长度不能超过64个字符")
    private String userId;
}
