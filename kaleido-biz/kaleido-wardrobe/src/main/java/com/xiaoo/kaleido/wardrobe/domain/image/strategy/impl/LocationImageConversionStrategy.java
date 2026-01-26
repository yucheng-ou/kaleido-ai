package com.xiaoo.kaleido.wardrobe.domain.image.strategy.impl;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageType;
import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.wardrobe.domain.image.enums.DomainType;
import com.xiaoo.kaleido.wardrobe.domain.image.strategy.ImageConversionStrategy;
import com.xiaoo.kaleido.wardrobe.domain.location.service.dto.LocationImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file.BasicImageInfo;
import org.springframework.stereotype.Component;

/**
 * 位置图片转换策略
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Component
public class LocationImageConversionStrategy implements ImageConversionStrategy<LocationImageInfoDTO> {
    
    @Override
    public LocationImageInfoDTO convert(BasicImageInfo basicInfo, ImageInfo minioInfo) {
        if (minioInfo != null) {
            return LocationImageInfoDTO.builder()
                    .imageOrder(basicInfo.getImageOrder())
                    .path(basicInfo.getPath())
                    .isPrimary(basicInfo.getIsPrimary())
                    .imageSize(minioInfo.getFileSize())
                    .width(minioInfo.getWidth())
                    .height(minioInfo.getHeight())
                    .imageType(ImageType.fromMimeType(minioInfo.getMimeType()))
                    .build();
        } else {
            return LocationImageInfoDTO.builder()
                    .imageOrder(basicInfo.getImageOrder())
                    .path(basicInfo.getPath())
                    .isPrimary(basicInfo.getIsPrimary())
                    .build();
        }
    }
    
    @Override
    public DomainType getSupportedDomainType() {
        return DomainType.LOCATION;
    }
}
