package com.xiaoo.kaleido.api.wardrobe.command;

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
@AllArgsConstructor
@NoArgsConstructor
public class CreateLocationWithImagesCommand {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    @Size(max = 50, message = "用户ID长度不能超过50个字符")
    private String userId;

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
    private List<ImageInfo> images;

    /**
     * 图片信息
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageInfo {
        /**
         * 图片路径（在minio中的文件路径）
         */
        @NotBlank(message = "图片路径不能为空")
        @Size(max = 500, message = "图片路径长度不能超过500个字符")
        private String path;

        /**
         * 排序序号
         */
        @NotNull(message = "排序序号不能为空")
        private Integer imageOrder;

        /**
         * 是否为主图
         */
        private Boolean isPrimary = false;
    }
}
