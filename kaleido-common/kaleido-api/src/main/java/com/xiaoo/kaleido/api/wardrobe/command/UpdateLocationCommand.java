package com.xiaoo.kaleido.api.wardrobe.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * 更新位置命令
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLocationCommand {

    /**
     * 位置ID
     */
    @NotBlank(message = "位置ID不能为空")
    @Size(max = 50, message = "位置ID长度不能超过50个字符")
    private String locationId;

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
