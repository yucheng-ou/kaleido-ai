package com.xiaoo.kaleido.api.interview.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 创建候选人命令
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCandidateCommand extends BaseCommand {

    /**
     * 候选人姓名
     */
    @NotBlank(message = "候选人姓名不能为空")
    private String name;

    /**
     * 技能列表（逗号分隔）
     */
    private String skills;

    /**
     * 工作年限
     */
    private Integer experienceYears;

    /**
     * 原始简历文本
     */
    private String rawResumeText;
}
