package com.xiaoo.kaleido.api.notice.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 添加通知模板命令
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddNoticeTemplateCommand extends BaseCommand {

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空")
    @Size(min = 2, max = 50, message = "模板名称长度必须在2-50位之间")
    private String name;

    /**
     * 模板编码
     */
    @NotBlank(message = "模板编码不能为空")
    @Size(min = 2, max = 50, message = "模板编码长度必须在2-50位之间")
    private String code;

    /**
     * 模板内容
     */
    @NotBlank(message = "模板内容不能为空")
    @Size(min = 1, max = 500, message = "模板内容长度必须在1-500位之间")
    private String content;
}
