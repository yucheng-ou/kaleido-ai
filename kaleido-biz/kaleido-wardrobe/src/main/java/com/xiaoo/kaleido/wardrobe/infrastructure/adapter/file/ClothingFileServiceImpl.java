package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.ClothingImageInfoCommand;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.file.IClothingFileService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto.ImageInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothingFileServiceImpl implements IClothingFileService {

    private final ImageProcessingService imageProcessingService;

    @Override
    public List<ImageInfoDTO> convertorImageInfo(List<ClothingImageInfoCommand> images) {
        return imageProcessingService.processImages(
                images.stream()
                        .map(ImageInfoAdapter::fromClothingImageInfo)
                        .collect(java.util.stream.Collectors.toList()),
                (adapter, minioInfo) -> {
                    if (minioInfo != null) {
                        return ImageInfoDTO.builder()
                                .imageOrder(adapter.getImageOrder())
                                .path(adapter.getPath())
                                .isPrimary(adapter.getIsPrimary())
                                .imageSize(minioInfo.getFileSize())
                                .width(minioInfo.getWidth())
                                .height(minioInfo.getHeight())
                                .build();
                    } else {
                        return ImageInfoDTO.builder()
                                .imageOrder(adapter.getImageOrder())
                                .path(adapter.getPath())
                                .isPrimary(adapter.getIsPrimary())
                                .build();
                    }
                }
        );
    }
}
