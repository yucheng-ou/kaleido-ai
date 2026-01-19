package com.xiaoo.kaleido.api.coin.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 减少金币命令
 * <p>
 * 用于通用的金币减少操作
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawCommand {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    @Size(max = 64, message = "用户ID长度不能超过64个字符")
    private String userId;

    /**
     * 减少金额
     */
    @NotNull(message = "减少金额不能为空")
    @Positive(message = "减少金额必须大于0")
    private Long amount;

    /**
     * 业务类型
     */
    @NotBlank(message = "业务类型不能为空")
    @Size(max = 20, message = "业务类型长度不能超过20个字符")
    private String bizType;

    /**
     * 业务ID
     */
    @Size(max = 64, message = "业务ID长度不能超过64个字符")
    private String bizId;

    /**
     * 备注
     */
    @Size(max = 200, message = "备注长度不能超过200个字符")
    private String remark;
}
