package com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageType;
import lombok.*;

/**
 * 图片信息DTO
 * <p>
 * 用于在领域服务中传递图片数据的传输对象
 * 实现IClothingDomainService.ImageInfo接口
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfoDTO  {
    private String path;
    private Integer imageOrder;
    private Boolean isPrimary;
    private Long imageSize;
    private ImageType imageType;
    private Integer width;
    private Integer height;
}
