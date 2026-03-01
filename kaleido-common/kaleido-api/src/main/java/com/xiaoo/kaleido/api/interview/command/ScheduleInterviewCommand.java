package com.xiaoo.kaleido.api.interview.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

/**
 * 安排面试命令
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleInterviewCommand extends BaseCommand {

    /**
     * 候选人ID
     */
    @NotBlank(message = "候选人ID不能为空")
    private String candidateId;

    /**
     * 面试时间
     */
    @NotNull(message = "面试时间不能为空")
    private Date interviewTime;

    /**
     * 面试官姓名
     */
    @NotBlank(message = "面试官姓名不能为空")
    private String interviewerName;
}
