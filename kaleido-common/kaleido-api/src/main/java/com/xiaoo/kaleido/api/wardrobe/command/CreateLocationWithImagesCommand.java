package com.xiaoo.kaleido.api.wardrobe.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * 创建位置（包含图片）命令
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateLocationWithImagesCommand extends BaseCommand {

    /**
     * 位置名称
     */
    @NotBlank(message = "位置名称不能为空")
    @Size(max = 100, message = "位置名称长度不能超过100个字符")
    private String name;

    /**
     * 位置描述
     */
    @Size(max = 500, message = "位置描述长度不能超过500个字符")
    private String description;

    /**
     * 具体地址
     */
    @Size(max = 500, message = "地址长度不能超过500个字符")
    private String address;

    /**
     * 图片信息列表
     */
    private List<LocationImageInfoCommand> images;
}
