package com.xiaoo.kaleido.api.wardrobe.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 图片信息
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public  class BaseImageInfoCommand {

    /**
     * 图片路径（在MinIO中的文件路径）
     */
    @NotBlank(message = "图片路径不能为空")
    private String path;

    /**
     * 排序序号
     */
    @NotNull(message = "排序序号不能为空")
    private Integer imageOrder;

    /**
     * 是否为主图
     */
    @NotNull(message = "是否为主图不能为空")
    private Boolean isPrimary;
}
