package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.LocationImageInfoCommand;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.file.ILocationFileService;
import com.xiaoo.kaleido.wardrobe.domain.location.service.dto.LocationImageInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationFileServiceImpl implements ILocationFileService {

    private final ImageProcessingService imageProcessingService;

    @Override
    public List<LocationImageInfoDTO> convertorImageInfo(List<LocationImageInfoCommand> images) {
        return imageProcessingService.processImages(
                images.stream()
                        .map(ImageInfoAdapter::fromLocationImageInfo)
                        .collect(java.util.stream.Collectors.toList()),
                (adapter, minioInfo) -> {
                    if (minioInfo != null) {
                        return LocationImageInfoDTO.builder()
                                .imageOrder(adapter.getImageOrder())
                                .path(adapter.getPath())
                                .isPrimary(adapter.getIsPrimary())
                                .imageSize(minioInfo.getFileSize())
                                .width(minioInfo.getWidth())
                                .height(minioInfo.getHeight())
                                .build();
                    } else {
                        return LocationImageInfoDTO.builder()
                                .imageOrder(adapter.getImageOrder())
                                .path(adapter.getPath())
                                .isPrimary(adapter.getIsPrimary())
                                .build();
                    }
                }
        );
    }
}
