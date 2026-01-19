package com.xiaoo.kaleido.api.coin.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 处理搭配创建扣费命令
 * <p>
 * 用于处理用户创建穿搭搭配时的金币扣费
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessOutfitCreationCommand {

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
