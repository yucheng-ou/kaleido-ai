package com.xiaoo.kaleido.api.tag.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 关联实体命令（单个关联）
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociateEntityCommand extends BaseCommand {

    /**
     * 标签ID
     */
    @NotBlank(message = "标签ID不能为空")
    private String tagId;

    /**
     * 实体ID
     * 关联的实体ID，如服装ID、搭配ID等
     */
    @NotBlank(message = "实体ID不能为空")
    private String entityId;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 实体类型编码
     * 用于验证标签类型与实体类型是否匹配
     */
    @NotBlank(message = "实体类型编码不能为空")
    private String entityTypeCode;
}
