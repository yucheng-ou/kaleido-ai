package com.xiaoo.kaleido.wardrobe.domain.location.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.model.entity.LocationImage;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationDomainService;
import com.xiaoo.kaleido.wardrobe.domain.location.service.dto.LocationImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 位置领域服务实现类
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationDomainServiceImpl implements ILocationDomainService {

    private final ILocationRepository locationRepository;

    @Override
    public StorageLocationAggregate createLocationWithImages(
            String userId,
            String name,
            String description,
            String address,
            List<LocationImageInfoDTO> images) {
        // 1. 参数校验
        validateCreateLocationParams(userId, name);

        // 2. 检查位置名称是否唯一
        if (!isLocationNameUnique(userId, name)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NAME_EXISTS);
        }

        // 3. 创建位置聚合根
        StorageLocationAggregate location = StorageLocationAggregate.create(userId, name, description, address);

        // 4. 处理图片（如果有）
        if (images != null && !images.isEmpty()) {
            validateAndAddImages(location, images);
        }

        // 5. 记录日志
        log.info("位置创建完成，位置ID: {}, 位置名称: {}, 用户ID: {}, 图片数量: {}",
                location.getId(), location.getName(), userId, images != null ? images.size() : 0);

        return location;
    }

    @Override
    public StorageLocationAggregate findByIdOrThrow(String locationId) {
        // 1. 参数校验
        if (StrUtil.isBlank(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置ID不能为空");
        }

        // 2. 判断数据是否存在
        StorageLocationAggregate locationAggregate = locationRepository.findById(locationId);
        if(locationAggregate == null) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NOT_FOUND);
        }

        return locationAggregate;
    }

    @Override
    public StorageLocationAggregate updateLocationWithImages(
            String locationId,
            String name,
            String description,
            String address,
            List<LocationImageInfoDTO> images) {
        // 1. 查找位置（如果位置不存在或已删除，findByIdOrThrow会抛出LOCATION_NOT_FOUND异常）
        StorageLocationAggregate location = findByIdOrThrow(locationId);

        // 2. 参数校验
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置名称不能为空");
        }

        // 3. 检查位置名称是否唯一（如果名称有变更）
        if (!location.getName().equals(name) && !isLocationNameUnique(location.getUserId(), name)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NAME_EXISTS);
        }

        // 4. 更新位置基本信息
        location.updateInfo(name, description, address);

        // 5. 处理图片（如果有）
        if (images != null && !images.isEmpty()) {
            // 清空原有图片
            location.getImages().clear();
            location.setPrimaryImageId(null);
            
            // 添加新图片
            validateAndAddImages(location, images);
        }

        // 6. 记录日志
        log.info("位置信息更新完成，位置ID: {}, 新位置名称: {}, 图片数量: {}",
                locationId, name, images != null ? images.size() : 0);

        return location;
    }

    @Override
    public boolean isLocationNameUnique(String userId, String name) {
        // 1. 参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置名称不能为空");
        }

        // 2. 检查位置名称是否已存在
        return !locationRepository.existsByNameAndUserId(name.trim(), userId);
    }

    @Override
    public boolean canDeleteLocation(String locationId) {
        // 1. 参数校验
        if (StrUtil.isBlank(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置ID不能为空");
        }

        // 2. 检查位置是否被服装引用
        return !locationRepository.hasClothingReferences(locationId);
    }

    @Override
    public StorageLocationAggregate validateAndGetForDeletion(String locationId) {
        // 1. 参数校验
        if (StrUtil.isBlank(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置ID不能为空");
        }

        // 2. 查找位置
        StorageLocationAggregate location = findByIdOrThrow(locationId);

        // 3. 检查位置是否可删除（业务规则校验）
        if (!canDeleteLocation(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_HAS_REFERENCES);
        }

        // 4. 记录日志
        log.info("位置删除验证通过，位置ID: {}, 位置名称: {}", locationId, location.getName());

        return location;
    }

    @Override
    public List<StorageLocationAggregate> findByUserId(String userId) {
        // 1. 参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }

        // 2. 查询用户的所有位置
        return locationRepository.findByUserId(userId);
    }

    @Override
    public String getLocationName(String locationId) {
        if (StrUtil.isBlank(locationId)) {
            return null;
        }
        try {
            StorageLocationAggregate location = locationRepository.findById(locationId);
            return location != null ? location.getName() : null;
        } catch (Exception e) {
            log.warn("查询位置名称失败，locationId: {}", locationId, e);
            return null;
        }
    }

    /**
     * 验证创建位置的参数
     *
     * @param userId 用户ID
     * @param name   位置名称
     */
    private void validateCreateLocationParams(String userId, String name) {
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置名称不能为空");
        }
    }

    /**
     * 验证并添加图片到位置聚合根
     *
     * @param location 位置聚合根
     * @param images   图片信息DTO列表
     */
    private void validateAndAddImages(StorageLocationAggregate location, List<LocationImageInfoDTO> images) {
        // 1. 检查图片数量限制
        if (images.size() > StorageLocationAggregate.MAX_IMAGES_PER_LOCATION) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_IMAGE_LIMIT_EXCEEDED);
        }

        // 2. 检查主图数量
        long primaryCount = images.stream()
                .filter(image -> Boolean.TRUE.equals(image.getIsPrimary()))
                .count();
        if (primaryCount > 1) {
            throw WardrobeException.of(WardrobeErrorCode.MULTIPLE_PRIMARY_IMAGES);
        }

        // 3. 如果已有主图，且新图片中包含主图，则抛出异常
        if (location.getPrimaryImageId() != null && primaryCount > 0) {
            throw WardrobeException.of(WardrobeErrorCode.PRIMARY_IMAGE_EXISTS);
        }

        // 4. 将DTO转换为实体
        List<LocationImage> locationImages = images.stream()
                .map(dto -> LocationImage.create(
                        location.getId(),
                        dto.getPath(),
                        dto.getImageOrder(),
                        dto.getIsPrimary(),
                        dto.getImageSize(),
                        dto.getImageTypeEnums(),
                        dto.getWidth(),
                        dto.getHeight()
                ))
                .collect(Collectors.toList());

        // 5. 添加图片
        location.addImages(locationImages);

        // 6. 如果新图片中有主图，设置主图
        // 找到对应的实体ID
        images.stream()
                .filter(image -> Boolean.TRUE.equals(image.getIsPrimary()))
                .findFirst().flatMap(primaryImage -> locationImages.stream()
                        .filter(img -> img.getPath().equals(primaryImage.getPath()))
                        .findFirst()).ifPresent(img -> location.setAsPrimaryImage(img.getId()));
    }
}
