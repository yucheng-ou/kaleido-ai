package com.xiaoo.kaleido.wardrobe.domain.location.service.dto;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageTypeEnums;
import lombok.*;

/**
 * 位置图片信息DTO
 * <p>
 * 用于在位置领域服务中传递图片数据的传输对象
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationImageInfoDTO {
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
     * 图片大小（字节）
     */
    private Long imageSize;

    /**
     * 图片类型
     */
    private ImageTypeEnums imageTypeEnums;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;
}
