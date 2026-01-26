package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.OutfitImageInfoCommand;
import com.xiaoo.kaleido.wardrobe.domain.image.enums.DomainType;
import com.xiaoo.kaleido.wardrobe.domain.image.service.UnifiedImageProcessingService;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.file.IOutfitFileService;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.dto.OutfitImageInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 穿搭图片文件服务实现
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Service
@RequiredArgsConstructor
public class OutfitFileServiceImpl implements IOutfitFileService {

    private final UnifiedImageProcessingService unifiedImageProcessingService;

    @Override
    public List<OutfitImageInfoDTO> convertorImageInfo(List<OutfitImageInfoCommand> images) {
        // 使用新的统一图片处理服务
        return unifiedImageProcessingService.processImages(
                DomainType.OUTFIT,
                images,
                ImageInfoAdapter::fromOutfitImageInfo
        );
    }
}
