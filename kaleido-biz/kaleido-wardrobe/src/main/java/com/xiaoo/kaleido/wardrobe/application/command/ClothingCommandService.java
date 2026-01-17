package com.xiaoo.kaleido.wardrobe.application.command;

import com.xiaoo.kaleido.api.wardrobe.command.CreateClothingWithImagesCommand;
import com.xiaoo.kaleido.api.wardrobe.command.ClothingImageInfoCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateClothingCommand;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.file.IClothingFileService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IClothingRepository;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IClothingDomainService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto.ImageInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服装命令服务
 * <p>
 * 负责编排服装相关的命令操作，包括创建、更新等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClothingCommandService {

    private final IClothingDomainService clothingDomainService;
    private final IClothingRepository clothingRepository;
    private final IClothingFileService clothingFileService;

    /**
     * 创建服装（包含图片）
     *
     * @param command 创建服装命令
     * @return 创建的服装ID
     */
    public String createClothingWithImages(CreateClothingWithImagesCommand command) {
        // 1.准备图片信息
        List<ClothingImageInfoCommand> imageInfos = command.getImages();

        // 2.使用图片处理服务转换图片信息
        List<ImageInfoDTO> domainImageInfos = clothingFileService.convertorImageInfo(imageInfos);

        // 3.调用领域服务创建服装
        ClothingAggregate clothing = clothingDomainService.createClothingWithImages(
                command.getUserId(),
                command.getName(),
                command.getTypeCode(),
                command.getColorCode(),
                command.getSeasonCode(),
                command.getBrandId(),
                command.getSize(),
                command.getPurchaseDate(),
                command.getPrice(),
                command.getDescription(),
                command.getCurrentLocationId(),
                domainImageInfos
        );

        // 4.保存服装
        clothingRepository.save(clothing);

        // 5.记录日志
        log.info("服装创建成功，服装ID: {}, 用户ID: {}, 服装名称: {}",
                clothing.getId(), command.getUserId(), command.getName());

        return clothing.getId();
    }

    /**
     * 更新服装信息（包含图片）
     *
     * @param command 更新服装命令
     */
    public void updateClothing(UpdateClothingCommand command) {
        // 1.准备图片信息
        List<ClothingImageInfoCommand> imageInfos = command.getImages();

        // 2.使用图片处理服务转换图片信息
        List<ImageInfoDTO> domainImageInfos = clothingFileService.convertorImageInfo(imageInfos);

        // 3.调用领域服务更新服装
        ClothingAggregate clothing = clothingDomainService.updateClothing(
                command.getClothingId(),
                command.getUserId(),
                command.getName(),
                command.getTypeCode(),
                command.getColorCode(),
                command.getSeasonCode(),
                command.getBrandId(),
                command.getSize(),
                command.getPurchaseDate(),
                command.getPrice(),
                command.getDescription(),
                command.getCurrentLocationId(),
                domainImageInfos
        );

        // 4.保存服装
        clothingRepository.update(clothing);

        // 5.记录日志
        log.info("服装更新成功，服装ID: {}, 用户ID: {}, 新名称: {}",
                command.getClothingId(), command.getUserId(), command.getName());
    }

    /**
     * 删除服装
     *
     * @param clothingId 服装ID
     * @param userId     用户ID
     */
    public void deleteClothing(String clothingId, String userId) {
        // 1.调用领域服务删除服装（逻辑删除，将状态设置为DISABLE）
        ClothingAggregate clothing = clothingDomainService.deleteClothing(clothingId, userId);

        // 2.更新服装状态（逻辑删除）
        clothingRepository.delete(clothingId);

        // 3.记录日志
        log.info("服装删除成功，服装ID: {}, 用户ID: {}", clothingId, userId);
    }
}
