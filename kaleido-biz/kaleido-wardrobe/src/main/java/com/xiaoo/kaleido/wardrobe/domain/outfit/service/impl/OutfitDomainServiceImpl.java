package com.xiaoo.kaleido.wardrobe.domain.outfit.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitClothing;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.IOutfitDomainService;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.dto.OutfitImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository.IOutfitRepository;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.clothing.IClothingServiceAdapter;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 穿搭领域服务实现类
 * <p>
 * 实现穿搭领域服务的所有业务逻辑，包括参数校验、业务规则验证、异常处理等
 * 遵循DDD原则：负责参数校验（针对controller未校验部分）+ 业务规则验证 + 聚合根操作
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutfitDomainServiceImpl implements IOutfitDomainService {

    private final IOutfitRepository outfitRepository;
    private final IClothingServiceAdapter clothingServiceAdapter;

    @Override
    public OutfitAggregate
    createOutfitWithClothingsAndImages(
            String userId,
            String name,
            String description,
            List<String> clothingIds,
            List<OutfitImageInfoDTO> images) {

        // 1.业务规则校验（使用公共方法）
        validateOutfitBusinessRules(userId, name, clothingIds, images, true);

        // 2.创建穿搭聚合根
        OutfitAggregate outfit = OutfitAggregate.create(userId, name, description);

        // 3.创建服装关联实体列表
        List<OutfitClothing> clothingEntities = clothingIds.stream()
                .map(clothingId -> OutfitClothing.builder()
                        .id(com.xiaoo.kaleido.distribute.util.SnowflakeUtil.newSnowflakeId())
                        .outfitId(outfit.getId())
                        .clothingId(clothingId)
                        .build())
                .collect(Collectors.toList());
        
        outfit.updateClothings(clothingEntities);

        // 4.创建图片实体列表
        List<OutfitImage> imageEntities = images.stream()
                .map(img -> OutfitImage.create(
                        outfit.getId(),
                        img.getPath(),
                        img.getImageOrder(),
                        img.getIsPrimary(),
                        img.getImageSize(),
                        img.getImageTypeEnums(),
                        img.getWidth(),
                        img.getHeight(),
                        img.getDescription()
                ))
                .collect(Collectors.toList());
        
        outfit.addImages(imageEntities);

        // 5.设置主图（如果有）
        imageEntities.stream()
                .filter(img -> Boolean.TRUE.equals(img.isPrimaryImage()))
                .findFirst()
                .ifPresent(primaryImage -> outfit.setAsPrimaryImage(primaryImage.getId()));

        // 6.记录日志
        log.info("穿搭创建成功，穿搭ID: {}, 用户ID: {}, 穿搭名称: {}, 服装数量: {}, 图片数量: {}",
                outfit.getId(), userId, name, clothingIds.size(), images.size());

        return outfit;
    }

    @Override
    public OutfitAggregate findByIdOrThrow(String outfitId) {
        // 1.参数校验
        if (StrUtil.isBlank(outfitId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "搭配ID不能为空");
        }

        // 2.查找穿搭（包含服装列表、图片列表和穿着记录）
        // 注意：outfitRepository.findById 已经会抛出异常，所以直接返回即可
        return outfitRepository.findById(outfitId);
    }

    @Override
    public OutfitAggregate findByIdAndUserIdOrThrow(String outfitId, String userId) {
        // 1.参数校验
        if (StrUtil.isBlank(outfitId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "穿搭ID不能为空");
        }
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }

        // 2.查找穿搭
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 3.验证用户权限（只有穿搭所有者可以查询）
        if (!outfit.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.DATA_NOT_BELONG_TO_USER);
        }

        return outfit;
    }

    @Override
    public OutfitAggregate updateOutfit(
            String outfitId,
            String userId,
            String name,
            String description,
            List<String> clothingIds,
            List<OutfitImageInfoDTO> images) {
        
        // 1.查找穿搭
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证用户权限（只有穿搭所有者可以修改）
        if (!outfit.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.DATA_NOT_BELONG_TO_USER);
        }

        // 3.业务规则校验（使用公共方法，根据名称是否变更决定是否检查名称唯一性）
        validateOutfitBusinessRules(userId, name, clothingIds, images, !outfit.getName().equals(name));

        // 4.更新穿搭基本信息
        outfit.updateInfo(name, description);

        // 5.更新服装关联实体列表
        List<OutfitClothing> clothingEntities = clothingIds.stream()
                .map(clothingId -> OutfitClothing.builder()
                        .id(com.xiaoo.kaleido.distribute.util.SnowflakeUtil.newSnowflakeId())
                        .outfitId(outfit.getId())
                        .clothingId(clothingId)
                        .build())
                .collect(Collectors.toList());
        
        outfit.updateClothings(clothingEntities);

        // 6.更新图片实体列表（全量替换）
        List<OutfitImage> imageEntities = images.stream()
                .map(img -> OutfitImage.create(
                        outfit.getId(),
                        img.getPath(),
                        img.getImageOrder(),
                        img.getIsPrimary(),
                        img.getImageSize(),
                        img.getImageTypeEnums(),
                        img.getWidth(),
                        img.getHeight(),
                        img.getDescription()
                ))
                .collect(Collectors.toList());
        
        // 清空原有图片并添加新图片
        outfit.getImages().clear();
        outfit.addImages(imageEntities);

        // 7.设置主图（如果有）
        imageEntities.stream()
                .filter(img -> Boolean.TRUE.equals(img.isPrimaryImage()))
                .findFirst()
                .ifPresent(primaryImage -> outfit.setAsPrimaryImage(primaryImage.getId()));

        // 8.记录日志
        log.info("穿搭信息更新成功，穿搭ID: {}, 用户ID: {}, 新名称: {}, 服装数量: {}, 图片数量: {}",
                outfitId, userId, name, clothingIds.size(), images.size());

        return outfit;
    }

    @Override
    public OutfitAggregate recordOutfitWear(String outfitId, String userId, String notes) {
        // 1.查找穿搭
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证用户权限（只有穿搭所有者可以记录穿着）
        if (!outfit.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.DATA_NOT_BELONG_TO_USER);
        }

        // 3.记录穿着行为
        outfit.recordWear(userId, notes);

        // 4.记录日志
        log.info("穿搭穿着记录成功，穿搭ID: {}, 用户ID: {}, 穿着次数: {}, 最后穿着日期: {}",
                outfitId, userId, outfit.getWearCount(), outfit.getLastWornDate());

        return outfit;
    }

    @Override
    public OutfitAggregate deleteOutfit(String outfitId, String userId) {
        // 1.查找穿搭
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证用户权限（只有穿搭所有者可以删除）
        if (!outfit.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.DATA_NOT_BELONG_TO_USER);
        }

        // 3.记录日志
        log.info("穿搭删除操作，穿搭ID: {}, 用户ID: {}, 穿搭名称: {}",
                outfitId, userId, outfit.getName());

        return outfit;
    }

    @Override
    public List<OutfitAggregate> findOutfitsByUserId(String userId) {
        // 1.参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }

        // 2.查询用户穿搭列表
        return outfitRepository.findByUserId(userId);
    }

    /**
     * 检查穿搭名称在用户下是否唯一
     *
     * @param userId 用户ID
     * @param name   穿搭名称
     * @return 如果唯一返回false，如果已存在返回true
     */
    private boolean isOutfitNameUnique(String userId, String name) {
        // 1.参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "搭配名称不能为空");
        }

        // 2.从仓储层检查穿搭名称唯一性
        return outfitRepository.existsByUserIdAndName(userId, name);
    }

    /**
     * 校验穿搭业务规则参数
     * 
     * @param userId 用户ID（Controller层已校验非空）
     * @param name 穿搭名称（Controller层已校验非空）
     * @param clothingIds 服装ID列表（Controller层已校验非空）
     * @param images 图片信息列表（Controller层已校验非空）
     * @param checkNameUniqueness 是否检查名称唯一性
     */
    private void validateOutfitBusinessRules(String userId, String name, 
                                            List<String> clothingIds, 
                                            List<OutfitImageInfoDTO> images,
                                            boolean checkNameUniqueness) {
        
        // 1. 业务规则校验：服装数量限制
        if (clothingIds.size() > OutfitAggregate.MAX_CLOTHINGS_PER_OUTFIT) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_CLOTHING_LIMIT_EXCEEDED);
        }

        // 2. 业务规则校验：图片数量限制
        if (images.size() > OutfitAggregate.MAX_IMAGES_PER_OUTFIT) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_IMAGE_LIMIT_EXCEEDED);
        }

        // 3. 业务规则校验：主图唯一性
        long primaryCount = images.stream()
                .filter(img -> img.getIsPrimary() != null && img.getIsPrimary())
                .count();
        if (primaryCount > 1) {
            throw WardrobeException.of(WardrobeErrorCode.MULTIPLE_PRIMARY_IMAGES);
        }

        // 4. 业务规则校验：名称唯一性（根据参数决定是否检查）
        if (checkNameUniqueness && isOutfitNameUnique(userId, name)) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_NAME_EXISTS);
        }

        // 5. 业务规则校验：验证服装ID列表中的所有服装属于同一用户
        validateClothingsBelongToUser(userId, clothingIds);
    }

    /**
     * 验证服装ID列表中的所有服装属于同一用户
     *
     * @param userId      用户ID
     * @param clothingIds 服装ID列表
     */
    private void validateClothingsBelongToUser(String userId, List<String> clothingIds) {
        // 使用防腐层验证服装所属用户
        clothingServiceAdapter.validateClothingsBelongToUser(clothingIds, userId);
    }
}
