package com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageType;
import lombok.*;

/**
 * 图片信息DTO
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothingImageInfoDTO {
    private String path;
    private Integer imageOrder;
    private Boolean isPrimary;
    private Long imageSize;
    private ImageType imageType;
    private Integer width;
    private Integer height;
}
