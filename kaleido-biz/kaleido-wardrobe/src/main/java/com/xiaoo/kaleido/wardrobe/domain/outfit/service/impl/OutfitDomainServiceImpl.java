package com.xiaoo.kaleido.wardrobe.domain.outfit.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository.IOutfitClothingRepository;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository.IOutfitImageRepository;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository.IOutfitRepository;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository.IWearRecordRepository;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.Outfit;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitClothing;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.WearRecord;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.IOutfitDomainService;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 搭配领域服务实现类
 * <p>
 * 实现搭配领域服务的所有业务逻辑，包括参数校验、业务规则验证、异常处理等
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutfitDomainServiceImpl implements IOutfitDomainService {

    private final IOutfitRepository outfitRepository;
    private final IOutfitImageRepository outfitImageRepository;
    private final IOutfitClothingRepository outfitClothingRepository;
    private final IWearRecordRepository wearRecordRepository;

    @Override
    public OutfitAggregate createOutfit(
            String userId,
            String name,
            String description) {
        // 1.参数校验
        validateCreateOutfitParams(userId, name);

        // 2.检查搭配名称在用户下是否唯一
        if (!isOutfitNameUnique(userId, name)) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_NAME_EXISTS);
        }

        // 3.创建搭配聚合根
        OutfitAggregate outfit = OutfitAggregate.create(userId, name, description);

        // 4.保存搭配（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 5.记录日志
        log.info("搭配创建成功，搭配ID: {}, 搭配名称: {}", outfit.getId(), outfit.getName());

        return outfit;
    }

    @Override
    public OutfitAggregate findByIdOrThrow(String outfitId) {
        // 1.参数校验
        if (StrUtil.isBlank(outfitId)) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_ID_NOT_NULL);
        }

        // 2.查找搭配
        return outfitRepository.findById(outfitId)
                .map(Outfit::toAggregate)
                .orElseThrow(() -> WardrobeException.of(WardrobeErrorCode.OUTFIT_NOT_FOUND));
    }

    @Override
    public OutfitAggregate updateOutfit(
            String outfitId,
            String name,
            String description) {
        // 1.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证搭配状态
        if (!outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_DISABLED);
        }

        // 3.参数校验
        validateUpdateOutfitParams(name);

        // 4.检查搭配名称在用户下是否唯一（排除当前搭配）
        if (!outfit.getName().equals(name) && !isOutfitNameUnique(outfit.getUserId(), name)) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_NAME_EXISTS);
        }

        // 5.更新搭配信息
        outfit.updateInfo(name, description);

        // 6.保存更新（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 7.记录日志
        log.info("搭配信息更新成功，搭配ID: {}", outfitId);

        return outfit;
    }

    @Override
    public OutfitAggregate enableOutfit(String outfitId) {
        // 1.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证搭配状态
        if (outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_ALREADY_ENABLED);
        }

        // 3.启用搭配
        outfit.enable();

        // 4.保存更新（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 5.记录日志
        log.info("搭配启用成功，搭配ID: {}", outfitId);

        return outfit;
    }

    @Override
    public OutfitAggregate disableOutfit(String outfitId) {
        // 1.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证搭配状态
        if (!outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_ALREADY_DISABLED);
        }

        // 3.禁用搭配
        outfit.disable();

        // 4.保存更新（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 5.记录日志
        log.info("搭配禁用成功，搭配ID: {}", outfitId);

        return outfit;
    }

    @Override
    public OutfitAggregate addClothingAssociation(String outfitId, String clothingId) {
        // 1.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证搭配状态
        if (!outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_DISABLED);
        }

        // 3.参数校验
        if (StrUtil.isBlank(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_ID_EMPTY);
        }

        // 4.检查是否已关联
        if (outfitClothingRepository.existsByOutfitIdAndClothingId(outfitId, clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_CLOTHING_ASSOCIATION_EXISTS);
        }

        // 5.创建关联实体
        OutfitClothing association = OutfitClothing.create(outfitId, clothingId);

        // 6.添加到聚合根
        outfit.addClothingAssociation(association);

        // 7.保存更新（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 8.记录日志
        log.info("搭配服装关联添加成功，搭配ID: {}, 服装ID: {}", outfitId, clothingId);

        return outfit;
    }

    @Override
    public boolean removeClothingAssociation(String outfitId, String clothingId) {
        // 1.参数校验
        if (StrUtil.isBlank(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_ID_EMPTY);
        }

        // 2.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 3.验证搭配状态
        if (!outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_DISABLED);
        }

        // 4.移除关联
        boolean removed = outfit.removeClothingAssociation(clothingId);

        // 5.保存更新（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 6.记录日志
        if (removed) {
            log.info("搭配服装关联移除成功，搭配ID: {}, 服装ID: {}", outfitId, clothingId);
        }

        return removed;
    }

    @Override
    public OutfitImage addImage(
            String outfitId,
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
        // 1.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证搭配状态
        if (!outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_DISABLED);
        }

        // 3.参数校验
        validateAddImageParams(path, imageOrder, isPrimary);

        // 4.检查是否已存在主图
        if (Boolean.TRUE.equals(isPrimary)) {
            List<OutfitImage> primaryImages = outfitImageRepository.findByOutfitIdAndIsPrimary(outfitId, true);
            if (!primaryImages.isEmpty()) {
                throw WardrobeException.of(WardrobeErrorCode.PRIMARY_IMAGE_EXISTS);
            }
        }

        // 5.创建图片实体
        OutfitImage image = OutfitImage.create(
                outfitId, path, imageOrder, isPrimary, fileName, fileSize,
                mimeType, width, height, thumbnailPath, description);

        // 6.添加到聚合根
        outfit.addImage(image);

        // 7.如果设置为主图，更新主图ID
        if (Boolean.TRUE.equals(isPrimary)) {
            outfit.setPrimaryImage(image.getId());
        }

        // 8.保存更新（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 9.记录日志
        log.info("搭配图片添加成功，搭配ID: {}, 图片ID: {}", outfitId, image.getId());

        return image;
    }

    @Override
    public boolean removeImage(String outfitId, String imageId) {
        // 1.参数校验
        if (StrUtil.isBlank(imageId)) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_ID_NOT_NULL);
        }

        // 2.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 3.验证搭配状态
        if (!outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_DISABLED);
        }

        // 4.移除图片
        boolean removed = outfit.removeImage(imageId);

        // 5.如果移除的是主图，清空主图ID
        if (removed && imageId.equals(outfit.getPrimaryImageId())) {
            outfit.setPrimaryImage(null);
        }

        // 6.保存更新（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 7.记录日志
        if (removed) {
            log.info("搭配图片移除成功，搭配ID: {}, 图片ID: {}", outfitId, imageId);
        }

        return removed;
    }

    @Override
    public OutfitAggregate setPrimaryImage(String outfitId, String imageId) {
        // 1.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证搭配状态
        if (!outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_DISABLED);
        }

        // 3.参数校验
        if (StrUtil.isBlank(imageId)) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_ID_NOT_NULL);
        }

        // 4.验证图片是否存在
        boolean imageExists = outfit.getAllImageIds().contains(imageId);
        if (!imageExists) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_NOT_FOUND);
        }

        // 5.设置主图
        outfit.setPrimaryImage(imageId);

        // 6.保存更新（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 7.记录日志
        log.info("搭配主图设置成功，搭配ID: {}, 主图ID: {}", outfitId, imageId);

        return outfit;
    }

    @Override
    public WearRecord addWearRecord(String outfitId, LocalDate wearDate, String notes) {
        // 1.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证搭配状态
        if (!outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_DISABLED);
        }

        // 3.参数校验
        validateAddWearRecordParams(wearDate);

        // 4.检查是否已存在同日期穿着记录
        if (wearRecordRepository.existsByUserIdAndOutfitIdAndWearDate(outfit.getUserId(), outfitId, wearDate)) {
            throw WardrobeException.of(WardrobeErrorCode.WEAR_RECORD_EXISTS);
        }

        // 5.创建穿着记录实体
        WearRecord wearRecord = WearRecord.create(outfit.getUserId(), outfitId, wearDate, notes);

        // 6.添加到聚合根
        outfit.addWearRecord(wearRecord);

        // 7.保存更新（将聚合根转换为实体）
        Outfit outfitEntity = Outfit.fromAggregate(outfit);
        outfitRepository.save(outfitEntity);

        // 8.记录日志
        log.info("穿着记录添加成功，搭配ID: {}, 穿着日期: {}", outfitId, wearDate);

        return wearRecord;
    }

    @Override
    public boolean isOutfitNameUnique(String userId, String name) {
        // 1.参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.USER_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_NAME_EMPTY);
        }

        // 2.检查搭配名称在用户下是否唯一
        return outfitRepository.findByUserIdAndName(userId, name.trim()).isEmpty();
    }

    @Override
    public List<OutfitAggregate> findByUserId(String userId) {
        // 1.参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.USER_ID_NOT_NULL);
        }

        // 2.查询用户搭配列表
        return outfitRepository.findByUserId(userId).stream()
                .map(Outfit::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<WearRecord> getWearRecords(String outfitId) {
        // 1.查找搭配
        OutfitAggregate outfit = findByIdOrThrow(outfitId);

        // 2.验证搭配状态
        if (!outfit.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_DISABLED);
        }

        // 3.查询穿着记录
        return wearRecordRepository.findByOutfitId(outfitId);
    }

    /**
     * 验证创建搭配的参数
     */
    private void validateCreateOutfitParams(String userId, String name) {
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.USER_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_NAME_EMPTY);
        }
        if (name.trim().length() > 100) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_NAME_LENGTH_ERROR);
        }
    }

    /**
     * 验证更新搭配的参数
     */
    private void validateUpdateOutfitParams(String name) {
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_NAME_EMPTY);
        }
        if (name.trim().length() > 100) {
            throw WardrobeException.of(WardrobeErrorCode.OUTFIT_NAME_LENGTH_ERROR);
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

    /**
     * 验证添加穿着记录的参数
     */
    private void validateAddWearRecordParams(LocalDate wearDate) {
        if (wearDate == null) {
            throw WardrobeException.of(WardrobeErrorCode.WEAR_DATE_EMPTY);
        }
        if (wearDate.isAfter(LocalDate.now())) {
            throw WardrobeException.of(WardrobeErrorCode.WEAR_DATE_FORMAT_ERROR);
        }
    }
}
