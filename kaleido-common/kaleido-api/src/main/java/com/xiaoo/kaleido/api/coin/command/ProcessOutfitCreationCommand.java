package com.xiaoo.kaleido.api.coin.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 处理搭配创建扣费命令
 * <p>
 * 用于处理用户创建穿搭搭配时的金币扣费
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessOutfitCreationCommand extends BaseCommand {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    @Size(max = 64, message = "用户ID长度不能超过64个字符")
    private String userId;

    /**
     * 搭配ID
     */
    @NotBlank(message = "搭配ID不能为空")
    @Size(max = 64, message = "搭配ID长度不能超过64个字符")
    private String outfitId;
}
