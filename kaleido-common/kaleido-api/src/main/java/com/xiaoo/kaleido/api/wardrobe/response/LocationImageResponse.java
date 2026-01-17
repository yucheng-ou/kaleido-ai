package com.xiaoo.kaleido.api.wardrobe.response;
import com.xiaoo.kaleido.api.wardrobe.enums.ImageType;
import lombok.*;

/**
 * 位置图片响应
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationImageResponse {

    /**
     * 图片ID
     */
    private String imageId;

    /**
     * 图片路径（在minio中的文件路径）
     */
    private String path;

    /**
     * 排序序号
     */
    private Integer imageOrder;

    /**
     * 是否为主图
     */
    private Boolean isPrimary;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 图片类型
     */
    private ImageType imageType;
}
