package com.xiaoo.kaleido.api.tag.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

/**
 * 取消关联标签命令
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DissociateTagsCommand extends BaseCommand {

    /**
     * 标签ID列表
     */
    @NotEmpty(message = "标签ID列表不能为空")
    private List<String> tagIds;

    /**
     * 实体ID
     * 取消关联的实体ID
     */
    @NotBlank(message = "实体ID不能为空")
    private String entityId;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;
}
