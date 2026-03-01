package com.xiaoo.kaleido.api.interview.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * 发送Offer命令
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendOfferCommand extends BaseCommand {

    /**
     * 候选人ID
     */
    @NotBlank(message = "候选人ID不能为空")
    private String candidateId;

    /**
     * 薪资（单位：元/月）
     */
    @NotNull(message = "薪资不能为空")
    private BigDecimal salary;

    /**
     * 职位名称
     */
    private String position;

    /**
     * 入职日期
     */
    private String onboardDate;
}
