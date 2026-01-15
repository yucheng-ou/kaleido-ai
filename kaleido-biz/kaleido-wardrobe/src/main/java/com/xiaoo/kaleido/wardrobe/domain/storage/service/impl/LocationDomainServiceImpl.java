package com.xiaoo.kaleido.wardrobe.domain.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.model.entity.LocationImage;
import com.xiaoo.kaleido.wardrobe.domain.model.entity.StorageLocation;
import com.xiaoo.kaleido.wardrobe.domain.service.ILocationDomainService;
import com.xiaoo.kaleido.wardrobe.domain.adapter.repository.ILocationImageRepository;
import com.xiaoo.kaleido.wardrobe.domain.adapter.repository.IStorageLocationRepository;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 位置领域服务实现类
 * <p>
 * 实现位置领域服务的所有业务逻辑，包括参数校验、业务规则验证、异常处理等
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationDomainServiceImpl implements ILocationDomainService {

    private final IStorageLocationRepository storageLocationRepository;
    private final ILocationImageRepository locationImageRepository;

    @Override
    public StorageLocationAggregate createLocation(
            String userId,
            String name,
            String description,
            String address) {
        // 1.参数校验
        validateCreateLocationParams(userId, name);

        // 2.检查位置名称在用户下是否唯一
        if (!isLocationNameUnique(userId, name)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NAME_EXISTS);
        }

        // 3.创建位置聚合根
        StorageLocationAggregate location = StorageLocationAggregate.create(
                userId, name, description, address);

        // 4.保存位置（将聚合根转换为实体）
        StorageLocation locationEntity = StorageLocation.fromAggregate(location);
        storageLocationRepository.save(locationEntity);

        // 5.记录日志
        log.info("位置创建成功，位置ID: {}, 位置名称: {}", location.getId(), location.getName());

        return location;
    }

    @Override
    public StorageLocationAggregate findByIdOrThrow(String locationId) {
        // 1.参数校验
        if (StrUtil.isBlank(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_ID_NOT_NULL);
        }

        // 2.查找位置
        return storageLocationRepository.findById(locationId)
                .map(StorageLocation::toAggregate)
                .orElseThrow(() -> WardrobeException.of(WardrobeErrorCode.LOCATION_NOT_FOUND));
    }

    @Override
    public StorageLocationAggregate updateLocation(
            String locationId,
            String name,
            String description,
            String address) {
        // 1.查找位置
        StorageLocationAggregate location = findByIdOrThrow(locationId);

        // 2.验证位置状态
        if (!location.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_DISABLED);
        }

        // 3.参数校验
        validateUpdateLocationParams(name);

        // 4.检查位置名称在用户下是否唯一（排除当前位置）
        if (!location.getName().equals(name) && !isLocationNameUnique(location.getUserId(), name)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NAME_EXISTS);
        }

        // 5.更新位置信息
        location.updateInfo(name, description, address);

        // 6.保存更新（将聚合根转换为实体）
        StorageLocation locationEntity = StorageLocation.fromAggregate(location);
        storageLocationRepository.save(locationEntity);

        // 7.记录日志
        log.info("位置信息更新成功，位置ID: {}", locationId);

        return location;
    }

    @Override
    public StorageLocationAggregate enableLocation(String locationId) {
        // 1.查找位置
        StorageLocationAggregate location = findByIdOrThrow(locationId);

        // 2.验证位置状态
        if (location.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_ALREADY_ENABLED);
        }

        // 3.启用位置
        location.enable();

        // 4.保存更新（将聚合根转换为实体）
        StorageLocation locationEntity = StorageLocation.fromAggregate(location);
        storageLocationRepository.save(locationEntity);

        // 5.记录日志
        log.info("位置启用成功，位置ID: {}", locationId);

        return location;
    }

    @Override
    public StorageLocationAggregate disableLocation(String locationId) {
        // 1.查找位置
        StorageLocationAggregate location = findByIdOrThrow(locationId);

        // 2.验证位置状态
        if (!location.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_ALREADY_DISABLED);
        }

        // 3.禁用位置
        location.disable();

        // 4.保存更新（将聚合根转换为实体）
        StorageLocation locationEntity = StorageLocation.fromAggregate(location);
        storageLocationRepository.save(locationEntity);

        // 5.记录日志
        log.info("位置禁用成功，位置ID: {}", locationId);

        return location;
    }

    @Override
    public LocationImage addImage(
            String locationId,
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
        // 1.查找位置
        StorageLocationAggregate location = findByIdOrThrow(locationId);

        // 2.验证位置状态
        if (!location.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_DISABLED);
        }

        // 3.参数校验
        validateAddImageParams(path, imageOrder, isPrimary);

        // 4.检查是否已存在主图
        if (Boolean.TRUE.equals(isPrimary)) {
            List<LocationImage> primaryImages = locationImageRepository.findByLocationIdAndIsPrimary(locationId, true);
            if (!primaryImages.isEmpty()) {
                throw WardrobeException.of(WardrobeErrorCode.PRIMARY_IMAGE_EXISTS);
            }
        }

        // 5.创建图片实体
        LocationImage image = LocationImage.create(
                locationId, path, imageOrder, isPrimary, fileName, fileSize,
                mimeType, width, height, thumbnailPath, description);

        // 6.添加到聚合根
        location.addImage(image);

        // 7.如果设置为主图，更新主图ID
        if (Boolean.TRUE.equals(isPrimary)) {
            location.setPrimaryImage(image.getId());
        }

        // 8.保存更新（将聚合根转换为实体）
        StorageLocation locationEntity = StorageLocation.fromAggregate(location);
        storageLocationRepository.save(locationEntity);

        // 9.记录日志
        log.info("位置图片添加成功，位置ID: {}, 图片ID: {}", locationId, image.getId());

        return image;
    }

    @Override
    public boolean removeImage(String locationId, String imageId) {
        // 1.参数校验
        if (StrUtil.isBlank(imageId)) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_ID_NOT_NULL);
        }

        // 2.查找位置
        StorageLocationAggregate location = findByIdOrThrow(locationId);

        // 3.验证位置状态
        if (!location.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_DISABLED);
        }

        // 4.移除图片
        boolean removed = location.removeImage(imageId);

        // 5.如果移除的是主图，清空主图ID
        if (removed && imageId.equals(location.getPrimaryImageId())) {
            location.setPrimaryImage(null);
        }

        // 6.保存更新（将聚合根转换为实体）
        StorageLocation locationEntity = StorageLocation.fromAggregate(location);
        storageLocationRepository.save(locationEntity);

        // 7.记录日志
        if (removed) {
            log.info("位置图片移除成功，位置ID: {}, 图片ID: {}", locationId, imageId);
        }

        return removed;
    }

    @Override
    public StorageLocationAggregate setPrimaryImage(String locationId, String imageId) {
        // 1.查找位置
        StorageLocationAggregate location = findByIdOrThrow(locationId);

        // 2.验证位置状态
        if (!location.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_DISABLED);
        }

        // 3.参数校验
        if (StrUtil.isBlank(imageId)) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_ID_NOT_NULL);
        }

        // 4.验证图片是否存在
        boolean imageExists = location.getAllImageIds().contains(imageId);
        if (!imageExists) {
            throw WardrobeException.of(WardrobeErrorCode.IMAGE_NOT_FOUND);
        }

        // 5.设置主图
        location.setPrimaryImage(imageId);

        // 6.保存更新（将聚合根转换为实体）
        StorageLocation locationEntity = StorageLocation.fromAggregate(location);
        storageLocationRepository.save(locationEntity);

        // 7.记录日志
        log.info("位置主图设置成功，位置ID: {}, 主图ID: {}", locationId, imageId);

        return location;
    }

    @Override
    public boolean isLocationNameUnique(String userId, String name) {
        // 1.参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.USER_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NAME_EMPTY);
        }

        // 2.检查位置名称在用户下是否唯一
        return storageLocationRepository.findByUserIdAndName(userId, name.trim()).isEmpty();
    }

    @Override
    public List<StorageLocationAggregate> findByUserId(String userId) {
        // 1.参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.USER_ID_NOT_NULL);
        }

        // 2.查询用户位置列表
        return storageLocationRepository.findByUserId(userId).stream()
                .map(StorageLocation::toAggregate)
                .collect(Collectors.toList());
    }

    /**
     * 验证创建位置的参数
     */
    private void validateCreateLocationParams(String userId, String name) {
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.USER_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NAME_EMPTY);
        }
        if (name.trim().length() > 100) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NAME_LENGTH_ERROR);
        }
    }

    /**
     * 验证更新位置的参数
     */
    private void validateUpdateLocationParams(String name) {
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NAME_EMPTY);
        }
        if (name.trim().length() > 100) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NAME_LENGTH_ERROR);
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
