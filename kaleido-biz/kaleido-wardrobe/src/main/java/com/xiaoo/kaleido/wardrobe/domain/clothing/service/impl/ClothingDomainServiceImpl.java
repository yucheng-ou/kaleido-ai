package com.xiaoo.kaleido.wardrobe.domain.clothing.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IClothingImageRepository;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity.ClothingImage;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IClothingDomainService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IClothingRepository;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 服装领域服务实现类
 * <p>
 * 实现服装领域服务的所有业务逻辑，包括参数校验、业务规则验证、异常处理等
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClothingDomainServiceImpl implements IClothingDomainService {

    private final IClothingRepository clothingRepository;
    private final IClothingImageRepository clothingImageRepository;

    @Override
    public ClothingAggregate createClothing(
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
            String currentLocationId) {
        // 1.参数校验
        validateCreateClothingParams(userId, name, typeCode);

        // 2.检查服装名称在用户下是否唯一
        if (!isClothingNameUnique(userId, name)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NAME_EXISTS);
        }

        // 3.创建服装聚合根
        ClothingAggregate clothing = ClothingAggregate.create(
                userId, name, typeCode, colorCode, seasonCode, brandId,
                size, purchaseDate, price, description, currentLocationId);

        // 4.保存服装
        clothingRepository.save(clothing);

        // 5.记录日志
        log.info("服装创建成功，服装ID: {}, 服装名称: {}", clothing.getId(), clothing.getName());

        return clothing;
    }

    @Override
    public ClothingAggregate findByIdOrThrow(String clothingId) {
        // 1.参数校验
        if (StrUtil.isBlank(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_ID_NOT_NULL);
        }

        // 2.查找服装
        return clothingRepository.findById(clothingId)
                .orElseThrow(() -> WardrobeException.of(WardrobeErrorCode.CLOTHING_NOT_FOUND));
    }

    @Override
    public ClothingAggregate updateClothing(
            String clothingId,
            String name,
            String typeCode,
            String colorCode,
            String seasonCode,
            String brandId,
            String size,
            Date purchaseDate,
            BigDecimal price,
            String description) {
        // 1.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 2.验证服装状态
        if (!clothing.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_DISABLED);
        }

        // 3.参数校验
        validateUpdateClothingParams(name, typeCode);

        // 4.检查服装名称在用户下是否唯一（排除当前服装）
        if (!clothing.getName().equals(name) && !isClothingNameUnique(clothing.getUserId(), name)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NAME_EXISTS);
        }

        // 5.更新服装信息
        clothing.updateInfo(name, typeCode, colorCode, seasonCode, brandId,
                size, purchaseDate, price, description);

        // 6.保存更新
        clothingRepository.update(clothing);

        // 7.记录日志
        log.info("服装信息更新成功，服装ID: {}", clothingId);

        return clothing;
    }

    @Override
    public ClothingAggregate changeClothingLocation(String clothingId, String locationId) {
        // 1.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 2.验证服装状态
        if (!clothing.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_DISABLED);
        }

        // 3.参数校验
        if (StrUtil.isBlank(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_ID_EMPTY);
        }

        // 4.变更位置
        clothing.changeLocation(locationId);

        // 5.保存更新
        clothingRepository.update(clothing);

        // 6.记录日志
        log.info("服装位置变更成功，服装ID: {}, 新位置ID: {}", clothingId, locationId);

        return clothing;
    }

    @Override
    public ClothingAggregate enableClothing(String clothingId) {
        // 1.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 2.验证服装状态
        if (clothing.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_ALREADY_ENABLED);
        }

        // 3.启用服装
        clothing.enable();

        // 4.保存更新
        clothingRepository.update(clothing);

        // 5.记录日志
        log.info("服装启用成功，服装ID: {}", clothingId);

        return clothing;
    }

    @Override
    public ClothingAggregate disableClothing(String clothingId) {
        // 1.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 2.验证服装状态
        if (!clothing.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_ALREADY_DISABLED);
        }

        // 3.禁用服装
        clothing.disable();

        // 4.保存更新
        clothingRepository.update(clothing);

        // 5.记录日志
        log.info("服装禁用成功，服装ID: {}", clothingId);

        return clothing;
    }

    @Override
    public ClothingImage addImage(
            String clothingId,
            String path,
            Integer imageOrder,
            Boolean isPrimary,
            String fileName,
            Long fileSize,
            String mimeType,
            Integer width,
            Integer height,
            String thumbnailPath,
            String description) {
        // 1.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 2.验证服装状态
        if (!clothing.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_DISABLED);
        }

        // 3.参数校验
        validateAddImageParams(path, imageOrder, isPrimary);

        // 4.检查是否已存在主图
        if (Boolean.TRUE.equals(isPrimary) && clothingImageRepository.hasPrimaryImage(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.PRIMARY_IMAGE_EXISTS);
        }

        // 5.创建图片实体
        ClothingImage image = ClothingImage.create(
                clothingId, path, imageOrder, isPrimary, fileName, fileSize,
                mimeType, width, height, thumbnailPath, description);

        // 6.添加到聚合根
        clothing.addImage(image);

        // 7.如果设置为主图，更新主图ID
        if (Boolean.TRUE.equals(isPrimary)) {
            clothing.setPrimaryImage(image.getId());
        }

        // 8.保存更新
        clothingRepository.update(clothing);

        // 9.记录日志
        log.info("服装图片添加成功，服装ID: {}, 图片ID: {}", clothingId, image.getId());

        return image;
    }

    @Override
    public boolean removeImage(String clothingId, String imageId) {
        // 1.参数校验
        if (StrUtil.isBlank(imageId)) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_ID_NOT_NULL);
        }

        // 2.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 3.验证服装状态
        if (!clothing.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_DISABLED);
        }

        // 4.移除图片
        boolean removed = clothing.removeImage(imageId);

        // 5.如果移除的是主图，清空主图ID
        if (removed && imageId.equals(clothing.getPrimaryImageId())) {
            clothing.setPrimaryImage(null);
        }

        // 6.保存更新
        clothingRepository.update(clothing);

        // 7.记录日志
        if (removed) {
            log.info("服装图片移除成功，服装ID: {}, 图片ID: {}", clothingId, imageId);
        }

        return removed;
    }

    @Override
    public ClothingAggregate setPrimaryImage(String clothingId, String imageId) {
        // 1.查找服装
        ClothingAggregate clothing = findByIdOrThrow(clothingId);

        // 2.验证服装状态
        if (!clothing.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_DISABLED);
        }

        // 3.参数校验
        if (StrUtil.isBlank(imageId)) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_ID_NOT_NULL);
        }

        // 4.验证图片是否存在
        boolean imageExists = clothing.getAllImageIds().contains(imageId);
        if (!imageExists) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_NOT_FOUND);
        }

        // 5.设置主图
        clothing.setPrimaryImage(imageId);

        // 6.保存更新
        clothingRepository.update(clothing);

        // 7.记录日志
        log.info("服装主图设置成功，服装ID: {}, 主图ID: {}", clothingId, imageId);

        return clothing;
    }

    @Override
    public boolean isClothingNameUnique(String userId, String name) {
        // 1.参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.USER_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NAME_EMPTY);
        }

        // 2.检查服装名称在用户下是否唯一
        return !clothingRepository.existsByUserIdAndName(userId, name.trim());
    }

    @Override
    public List<ClothingAggregate> findByUserId(String userId) {
        // 1.参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.USER_ID_NOT_NULL);
        }

        // 2.查询用户服装列表
        return clothingRepository.findByUserId(userId);
    }

    /**
     * 验证创建服装的参数
     */
    private void validateCreateClothingParams(String userId, String name, String typeCode) {
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.USER_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NAME_EMPTY);
        }
        if (StrUtil.isBlank(typeCode)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_TYPE_CODE_EMPTY);
        }
        if (name.trim().length() > 100) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NAME_LENGTH_ERROR);
        }
    }

    /**
     * 验证更新服装的参数
     */
    private void validateUpdateClothingParams(String name, String typeCode) {
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NAME_EMPTY);
        }
        if (StrUtil.isBlank(typeCode)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_TYPE_CODE_EMPTY);
        }
        if (name.trim().length() > 100) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NAME_LENGTH_ERROR);
        }
    }

    /**
     * 验证添加图片的参数
     */
    private void validateAddImageParams(String path, Integer imageOrder, Boolean isPrimary) {
        if (StrUtil.isBlank(path)) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_PATH_EMPTY);
        }
        if (imageOrder == null) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_ORDER_INVALID);
        }
        if (isPrimary == null) {
            throw WardrobeException.of(WardrobeErrorCode.PRIMARY_IMAGE_EXISTS);
        }
        if (path.trim().length() > 500) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_PATH_LENGTH_ERROR);
        }
    }
}
