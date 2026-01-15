package com.xiaoo.kaleido.api.tag.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 创建标签命令
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTagCommand extends BaseCommand {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 50, message = "标签名称长度不能超过50个字符")
    private String name;

    /**
     * 标签类型编码
     * 关联字典表t_dict.dict_code，字典类型为TAG_TYPE
     */
    @NotBlank(message = "标签类型编码不能为空")
    @Size(max = 50, message = "标签类型编码长度不能超过50个字符")
    private String typeCode;

    /**
     * 标签颜色
     */
    @Size(max = 20, message = "标签颜色长度不能超过20个字符")
    private String color;

    /**
     * 标签描述
     */
    @Size(max = 200, message = "标签描述长度不能超过200个字符")
    private String description;
}
