package com.xiaoo.kaleido.wardrobe.application.command;

import com.xiaoo.kaleido.api.wardrobe.command.CreateOutfitWithClothingsCommand;
import com.xiaoo.kaleido.api.wardrobe.command.RecordOutfitWearCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateOutfitCommand;
import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.file.service.IMinIOService;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository.IOutfitRepository;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.IOutfitDomainService;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.dto.OutfitImageInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 穿搭命令服务
 * <p>
 * 负责编排穿搭相关的命令操作，包括创建、更新、记录穿着等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutfitCommandService {

    private final IOutfitDomainService outfitDomainService;
    private final IOutfitRepository outfitRepository;
    private final IMinIOService minIOService;

    /**
     * 创建穿搭（包含服装和图片）
     *
     * @param command 创建穿搭命令
     * @return 创建的穿搭ID
     */
    public String createOutfitWithClothingsAndImages(CreateOutfitWithClothingsCommand command) {
        // 1.准备图片信息
        List<CreateOutfitWithClothingsCommand.ImageInfo> imageInfos = command.getImages();

        // 2.为每个图片获取MinIO中的完整信息并转换为领域服务需要的格式
        List<OutfitImageInfoDTO> domainImageInfos = imageInfos.stream()
                .map(info -> {
                    try {
                        // 从MinIO获取图片详细信息
                        ImageInfo minioImageInfo = minIOService.getImageInfo(info.getPath());

                        // 创建完整的图片信息
                        return OutfitImageInfoDTO.builder()
                                .imageOrder(info.getImageOrder())
                                .path(info.getPath())
                                .isPrimary(info.getIsPrimary())
                                .imageSize(minioImageInfo.getFileSize())
                                .width(minioImageInfo.getWidth())
                                .height(minioImageInfo.getHeight())
                                .build();

                    } catch (Exception e) {
                        // 处理异常：记录日志并返回基本图片信息
                        log.warn("获取图片信息失败，路径: {}, 错误: ", info.getPath(), e);

                        return OutfitImageInfoDTO.builder()
                                .imageOrder(info.getImageOrder())
                                .path(info.getPath())
                                .isPrimary(info.getIsPrimary())
                                .build();
                    }
                })
                .collect(Collectors.toList());

        // TODO: 验证服装ID列表中的所有服装属于同一用户
        // 暂时跳过验证，后续通过依赖注入服装查询服务实现

        // 3.调用领域服务创建穿搭
        OutfitAggregate outfit = outfitDomainService.createOutfitWithClothingsAndImages(
                command.getUserId(),
                command.getName(),
                command.getDescription(),
                command.getClothingIds(),
                domainImageInfos
        );

        // 4.保存穿搭
        outfitRepository.save(outfit);

        // 5.记录日志
        log.info("穿搭创建成功，穿搭ID: {}, 用户ID: {}, 穿搭名称: {}, 服装数量: {}, 图片数量: {}",
                outfit.getId(), command.getUserId(), command.getName(),
                command.getClothingIds().size(), command.getImages().size());

        return outfit.getId();
    }

    /**
     * 更新穿搭信息（包含服装和图片）
     *
     * @param command 更新穿搭命令
     */
    public void updateOutfit(UpdateOutfitCommand command) {
        // 1.准备图片信息
        List<UpdateOutfitCommand.ImageInfo> imageInfos = command.getImages();

        // 2.为每个图片获取MinIO中的完整信息并转换为领域服务需要的格式
        List<OutfitImageInfoDTO> domainImageInfos = imageInfos.stream()
                .map(info -> {
                    try {
                        // 从MinIO获取图片详细信息
                        ImageInfo minioImageInfo = minIOService.getImageInfo(info.getPath());

                        // 创建完整的图片信息
                        return OutfitImageInfoDTO.builder()
                                .imageOrder(info.getImageOrder())
                                .path(info.getPath())
                                .isPrimary(info.getIsPrimary())
                                .imageSize(minioImageInfo.getFileSize())
                                .width(minioImageInfo.getWidth())
                                .height(minioImageInfo.getHeight())
                                .build();
                    } catch (Exception e) {
                        // 处理异常：记录日志并返回基本图片信息
                        log.warn("获取图片信息失败，路径: {}, 错误: {}", info.getPath(), e.getMessage());

                        return OutfitImageInfoDTO.builder()
                                .imageOrder(info.getImageOrder())
                                .path(info.getPath())
                                .isPrimary(info.getIsPrimary())
                                .build();
                    }
                })
                .collect(Collectors.toList());

        // TODO: 验证服装ID列表中的所有服装属于同一用户
        // 暂时跳过验证，后续通过依赖注入服装查询服务实现

        // 3.调用领域服务更新穿搭
        OutfitAggregate outfit = outfitDomainService.updateOutfit(
                command.getOutfitId(),
                command.getUserId(),
                command.getName(),
                command.getDescription(),
                command.getClothingIds(),
                domainImageInfos
        );

        // 4.保存穿搭
        outfitRepository.save(outfit);

        // 5.记录日志
        log.info("穿搭更新成功，穿搭ID: {}, 用户ID: {}, 新名称: {}, 服装数量: {}, 图片数量: {}",
                command.getOutfitId(), command.getUserId(), command.getName(),
                command.getClothingIds().size(), command.getImages().size());
    }

    /**
     * 删除穿搭
     *
     * @param outfitId 穿搭ID
     * @param userId   用户ID
     */
    public void deleteOutfit(String outfitId, String userId) {
        // 1.调用领域服务删除穿搭（逻辑删除，将状态设置为DISABLE）
        OutfitAggregate outfit = outfitDomainService.deleteOutfit(outfitId, userId);

        // 2.更新穿搭状态（逻辑删除）
        outfitRepository.save(outfit);

        // 3.记录日志
        log.info("穿搭删除成功，穿搭ID: {}, 用户ID: {}", outfitId, userId);
    }

    /**
     * 记录穿搭穿着
     *
     * @param command 记录穿着命令
     */
    public void recordOutfitWear(RecordOutfitWearCommand command) {
        // 1.调用领域服务记录穿着
        OutfitAggregate outfit = outfitDomainService.recordOutfitWear(
                command.getOutfitId(),
                command.getUserId(),
                command.getNotes()
        );

        // 2.保存穿搭（更新穿着次数和最后穿着日期）
        outfitRepository.save(outfit);

        // 3.记录日志
        log.info("穿搭穿着记录成功，穿搭ID: {}, 用户ID: {}, 穿着日期: {}",
                command.getOutfitId(), command.getUserId(), command.getWearDate());
    }
}
