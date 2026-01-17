package com.xiaoo.kaleido.wardrobe.domain.clothing.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity.ClothingImage;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IClothingDomainService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto.ImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IClothingRepository;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服装领域服务实现类
 * <p>
 * 实现服装领域服务的所有业务逻辑，包括参数校验、业务规则验证、异常处理等
 * 遵循DDD原则：负责参数校验（针对controller未校验部分）+ 业务规则验证 + 聚合根操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClothingDomainServiceImpl implements IClothingDomainService {

    private final IClothingRepository clothingRepository;

    // ==================== 服装基本信息管理 ====================


    @Override
    public ClothingAggregate createClothingWithImages(
            String userId,
            String name,
            String typeCode,
            String colorCode,
            String seasonCode,
            String brandId,
            String size,
            Date purchaseDate,
            BigDecimal price,
            String description,
            String currentLocationId,
            List<ImageInfoDTO> images) {

        // 1.业务规则校验：服装名称在用户下唯一性
        if (isClothingNameUnique(userId, name)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NAME_EXISTS);
        }

        // 2.业务规则校验：图片数量限制
        if (images.size() > ClothingAggregate.MAX_IMAGES_PER_CLOTHING) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_IMAGE_LIMIT_EXCEEDED);
        }

        // 3.业务规则校验：主图唯一性
        long primaryCount = images.stream()
                .filter(img -> img.getIsPrimary() != null && img.getIsPrimary())
                .count();
        if (primaryCount > 1) {
            throw WardrobeException.of(WardrobeErrorCode.MULTIPLE_PRIMARY_IMAGES);
        }

        // 5.创建服装聚合根
        ClothingAggregate clothing = ClothingAggregate.create(
                userId, name, typeCode, colorCode, seasonCode, brandId,
                size, purchaseDate, price, description, currentLocationId);

        // 6.批量添加图片
        List<ClothingImage> imageEntities = images.stream()
                .map(img -> ClothingImage.create(
                        clothing.getId(), 
                        img.getPath(), 
                        img.getImageOrder(), 
                        img.getIsPrimary(),
                        img.getImageSize(),
                        img.getImageType() != null ? img.getImageType().name() : null,
                        img.getWidth(),
                        img.getHeight()
                ))
                .collect(Collectors.toList());
        
        clothing.addImages(imageEntities);

        // 7.设置主图（如果有）
        imageEntities.stream()
                .filter(img -> Boolean.TRUE.equals(img.isPrimaryImage()))
                .findFirst()
                .ifPresent(primaryImage -> clothing.setAsPrimaryImage(primaryImage.getId()));

        // 8.记录日志
        log.info("服装创建成功（包含图片），服装ID: {}, 用户ID: {}, 服装名称: {}, 图片数量: {}", 
                clothing.getId(), userId, name, images.size());

        return clothing;
    }

    @Override
    public ClothingAggregate findByIdOrThrow(String clothingId) {
        // 1.参数校验
        if (StrUtil.isBlank(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "服装ID不能为空");
        }

        // 2.查找服装（包含图片列表）
        return clothingRepository.findById(clothingId)
                .orElseThrow(() -> WardrobeException.of(WardrobeErrorCode.CLOTHING_NOT_FOUND));
    }

    @Override
    public ClothingAggregate updateClothing(
            String clothingId,
            String userId,
            String name,
            String typeCode,
            String colorCode,
            String seasonCode,
            String brandId,
            String size,
            Date purchaseDate,
            BigDecimal price,
            String description,
            String currentLocationId,
            List<ImageInfoDTO> images) {
        
        // 1.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 2.验证用户权限（只有服装所有者可以修改）
        if (!clothing.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_OWNER_MISMATCH);
        }

        // 3.业务规则校验：如果名称变更，检查新名称的唯一性
        if (!clothing.getName().equals(name) && isClothingNameUnique(userId, name)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NAME_EXISTS);
        }

        // 4.业务规则校验：图片数量限制
        if (images.size() > ClothingAggregate.MAX_IMAGES_PER_CLOTHING) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_IMAGE_LIMIT_EXCEEDED);
        }

        // 5.业务规则校验：主图唯一性
        long primaryCount = images.stream()
                .filter(img -> img.getIsPrimary() != null && img.getIsPrimary())
                .count();
        if (primaryCount > 1) {
            throw WardrobeException.of(WardrobeErrorCode.MULTIPLE_PRIMARY_IMAGES);
        }

        // 8.更新服装基本信息
        clothing.updateInfo(name, typeCode, colorCode, seasonCode, brandId,
                size, purchaseDate, price, description);
        
        // 9.更新位置（如果有）
        if (currentLocationId != null) {
            clothing.changeLocation(currentLocationId);
        }

        // 10.记录日志
        log.info("服装信息更新成功（包含图片），服装ID: {}, 用户ID: {}, 新名称: {}, 图片数量: {}", 
                clothingId, userId, name, images.size());

        return clothing;
    }

    @Override
    public ClothingAggregate changeClothingLocation(String clothingId, String locationId) {
        // 1.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 2.变更位置
        clothing.changeLocation(locationId);

        // 3.记录日志
        log.info("服装位置变更完成，服装ID: {}, 新位置ID: {}", clothingId, locationId);

        return clothing;
    }

    @Override
    public ClothingAggregate deleteClothing(String clothingId, String userId) {
        // 1.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 2.验证用户权限（只有服装所有者可以删除）
        if (!clothing.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_OWNER_MISMATCH);
        }

        return clothing;
    }

    @Override
    public List<ClothingAggregate> findByUserId(String userId) {
        // 查询用户服装列表
        return clothingRepository.findByUserId(userId);
    }

    private boolean isClothingNameUnique(String userId, String name) {
        // 从仓储层检查服装名称唯一性
        return clothingRepository.existsByUserIdAndName(userId, name);
    }
}
