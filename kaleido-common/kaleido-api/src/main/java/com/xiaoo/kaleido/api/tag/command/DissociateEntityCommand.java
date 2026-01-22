package com.xiaoo.kaleido.api.tag.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 取消关联实体命令（单个取消关联）
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DissociateEntityCommand extends BaseCommand {

    /**
     * 标签ID
     */
    @NotBlank(message = "标签ID不能为空")
    private String tagId;

    /**
     * 实体ID
     * 取消关联的实体ID，如服装ID、搭配ID等
     */
    @NotBlank(message = "实体ID不能为空")
    private String entityId;
}
