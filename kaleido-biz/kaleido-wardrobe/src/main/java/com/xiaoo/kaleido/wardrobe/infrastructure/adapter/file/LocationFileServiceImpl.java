package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.LocationImageInfoCommand;
import com.xiaoo.kaleido.wardrobe.domain.image.enums.DomainType;
import com.xiaoo.kaleido.wardrobe.domain.image.service.UnifiedImageProcessingService;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.file.ILocationFileService;
import com.xiaoo.kaleido.wardrobe.domain.location.service.dto.LocationImageInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 位置图片文件服务实现
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Service
@RequiredArgsConstructor
public class LocationFileServiceImpl implements ILocationFileService {

    private final UnifiedImageProcessingService unifiedImageProcessingService;

    @Override
    public List<LocationImageInfoDTO> convertorImageInfo(List<LocationImageInfoCommand> images) {
        // 使用新的统一图片处理服务
        return unifiedImageProcessingService.processImages(
                DomainType.LOCATION,
                images,
                ImageInfoAdapter::fromLocationImageInfo
        );
    }
}
