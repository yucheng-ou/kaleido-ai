package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.OutfitImageInfoCommand;
import com.xiaoo.kaleido.api.wardrobe.enums.ImageType;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.file.IOutfitFileService;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.dto.OutfitImageInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutfitFileServiceImpl implements IOutfitFileService {

    private final ImageProcessingService imageProcessingService;

    @Override
    public List<OutfitImageInfoDTO> convertorImageInfo(List<OutfitImageInfoCommand> images) {
        return imageProcessingService.processImages(
                images.stream()
                        .map(ImageInfoAdapter::fromOutfitImageInfo)
                        .collect(java.util.stream.Collectors.toList()),
                (adapter, minioInfo) -> {
                    if (minioInfo != null) {
                        return OutfitImageInfoDTO.builder()
                                .imageOrder(adapter.getImageOrder())
                                .path(adapter.getPath())
                                .isPrimary(adapter.getIsPrimary())
                                .imageSize(minioInfo.getFileSize())
                                .width(minioInfo.getWidth())
                                .height(minioInfo.getHeight())
                                .imageType(ImageType.fromMimeType(minioInfo.getMimeType()))
                                .build();
                    } else {
                        return OutfitImageInfoDTO.builder()
                                .imageOrder(adapter.getImageOrder())
                                .path(adapter.getPath())
                                .isPrimary(adapter.getIsPrimary())
                                .build();
                    }
                }
        );
    }
}
