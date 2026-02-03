package com.xiaoo.kaleido.wardrobe.domain.image.strategy.impl;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageTypeEnums;
import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.wardrobe.domain.image.enums.DomainType;
import com.xiaoo.kaleido.wardrobe.domain.image.strategy.ImageConversionStrategy;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.dto.OutfitImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file.BasicImageInfo;
import org.springframework.stereotype.Component;

/**
 * 穿搭图片转换策略
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Component
public class OutfitImageConversionStrategy implements ImageConversionStrategy<OutfitImageInfoDTO> {
    
    @Override
    public OutfitImageInfoDTO convert(BasicImageInfo basicInfo, ImageInfo minioInfo) {
        if (minioInfo != null) {
            return OutfitImageInfoDTO.builder()
                    .imageOrder(basicInfo.getImageOrder())
                    .path(basicInfo.getPath())
                    .isPrimary(basicInfo.getIsPrimary())
                    .imageSize(minioInfo.getFileSize())
                    .width(minioInfo.getWidth())
                    .height(minioInfo.getHeight())
                    .imageTypeEnums(ImageTypeEnums.fromMimeType(minioInfo.getMimeType()))
                    .build();
        } else {
            return OutfitImageInfoDTO.builder()
                    .imageOrder(basicInfo.getImageOrder())
                    .path(basicInfo.getPath())
                    .isPrimary(basicInfo.getIsPrimary())
                    .build();
        }
    }
    
    @Override
    public DomainType getSupportedDomainType() {
        return DomainType.OUTFIT;
    }
}
