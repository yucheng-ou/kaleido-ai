package com.xiaoo.kaleido.api.coin.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 处理推荐生成扣费命令
 * <p>
 * 用于处理用户生成AI推荐时的金币扣费
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessRecommendGenerationCommand {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    @Size(max = 64, message = "用户ID长度不能超过64个字符")
    private String userId;

    /**
     * 推荐记录ID
     */
    @NotBlank(message = "推荐记录ID不能为空")
    @Size(max = 64, message = "推荐记录ID长度不能超过64个字符")
    private String recommendRecordId;
}
