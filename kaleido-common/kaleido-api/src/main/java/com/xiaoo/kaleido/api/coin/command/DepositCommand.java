package com.xiaoo.kaleido.api.coin.command;

import com.xiaoo.kaleido.api.coin.enums.CoinBizTypeEnum;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 增加金币命令
 * <p>
 * 用于通用的金币增加操作
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositCommand extends BaseCommand {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    @Size(max = 64, message = "用户ID长度不能超过64个字符")
    private String userId;

    /**
     * 增加金额
     */
    @NotNull(message = "增加金额不能为空")
    @Positive(message = "增加金额必须大于0")
    private Long amount;

    /**
     * 业务类型
     */
    @NotNull(message = "业务类型不能为空")
    private CoinBizTypeEnum bizType;

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
