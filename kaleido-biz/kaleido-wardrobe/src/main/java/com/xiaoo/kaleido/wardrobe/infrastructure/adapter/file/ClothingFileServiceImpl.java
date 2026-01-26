package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file;

import com.xiaoo.kaleido.api.wardrobe.command.ClothingImageInfoCommand;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.file.IClothingFileService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto.ClothingImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.domain.image.enums.DomainType;
import com.xiaoo.kaleido.wardrobe.domain.image.service.UnifiedImageProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服装图片文件服务实现
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Service
@RequiredArgsConstructor
public class ClothingFileServiceImpl implements IClothingFileService {

    private final UnifiedImageProcessingService unifiedImageProcessingService;

    @Override
    public List<ClothingImageInfoDTO> convertorImageInfo(List<ClothingImageInfoCommand> images) {
        // 使用新的统一图片处理服务
        return unifiedImageProcessingService.processImages(
                DomainType.CLOTHING,
                images,
                ImageInfoAdapter::fromClothingImageInfo
        );
    }
}
