package com.xiaoo.kaleido.wardrobe.application.command;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.api.coin.IRpcCoinService;
import com.xiaoo.kaleido.api.coin.command.ProcessLocationCreationCommand;
import com.xiaoo.kaleido.api.tag.IRpcTagService;
import com.xiaoo.kaleido.api.tag.command.AssociateEntityCommand;
import com.xiaoo.kaleido.api.tag.command.DissociateEntityCommand;
import com.xiaoo.kaleido.api.wardrobe.command.CreateLocationWithImagesCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateLocationCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.file.ILocationFileService;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRecordRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.LocationRecordAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationDomainService;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationRecordDomainService;
import com.xiaoo.kaleido.wardrobe.domain.location.service.dto.LocationImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.types.constant.EntityTypeConstants;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 位置命令服务
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationCommandService {

    private final ILocationDomainService locationDomainService;
    private final ILocationRepository locationRepository;
    private final ILocationFileService locationFileService;
    private final ILocationRecordDomainService locationRecordDomainService;
    private final ILocationRecordRepository locationRecordRepository;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcTagService rpcTagService;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcCoinService rpcCoinService;

    /**
     * 创建位置（包含图片）
     *
     * @param command 创建位置命令
     * @return 创建的位置ID
     */
    @Transactional(rollbackFor = Exception.class)
    public String createLocation(String userId, CreateLocationWithImagesCommand command) {
        // 1. 使用模板方法转换图片信息
        List<LocationImageInfoDTO> imageInfoDTOS = locationFileService.convertorImageInfo(command.getImages());

        // 2. 调用原有方法
        return createLocationWithImages(
                userId,
                command.getName(),
                command.getDescription(),
                command.getAddress(),
                imageInfoDTOS
        );
    }

    /**
     * 创建位置（包含图片）
     *
     * @param userId      用户ID
     * @param name        位置名称
     * @param description 位置描述
     * @param address     具体地址
     * @param images      图片信息列表
     * @return 创建的位置ID
     */
    @Transactional(rollbackFor = Exception.class)
    public String createLocationWithImages(
            String userId,
            String name,
            String description,
            String address,
            List<LocationImageInfoDTO> images) {

        // 2. 调用领域服务创建位置（包含图片）
        StorageLocationAggregate location = locationDomainService.createLocationWithImages(
                userId,
                name,
                description,
                address,
                images
        );

        // 3. 保存位置
        locationRepository.save(location);

        // 4. 记录日志
        log.info("位置创建成功，位置ID: {}, 位置名称: {}, 用户ID: {}, 图片数量: {}",
                location.getId(), location.getName(), userId, images.size());

        // 5. 调用金币服务扣减金币
        deductCoinsForLocationCreation(userId, location.getId());

        return location.getId();
    }

    /**
     * 更新位置信息
     *
     * @param userId  用户ID
     * @param command 更新位置命令
     */
    public void updateLocation(String userId, UpdateLocationCommand command) {
        // 1. 验证权限：确保位置属于当前用户
        StorageLocationAggregate existingLocation = locationRepository.findById(command.getLocationId());
        if (!existingLocation.getUserId().equals(userId)) {
            throw WardrobeException.of("PERMISSION_DENIED", "无权更新该位置");
        }

        // 2. 使用模板方法转换图片信息
        List<LocationImageInfoDTO> imageInfoDTOS = locationFileService.convertorImageInfo(command.getImages());

        // 3. 调用原有方法
        updateLocationWithImages(
                command.getLocationId(),
                command.getName(),
                command.getDescription(),
                command.getAddress(),
                imageInfoDTOS
        );
    }

    /**
     * 更新位置信息（包含图片）
     *
     * @param locationId  位置ID
     * @param name        新位置名称
     * @param description 新位置描述
     * @param address     新具体地址
     * @param images      新图片信息列表
     */
    public void updateLocationWithImages(
            String locationId,
            String name,
            String description,
            String address,
            List<LocationImageInfoDTO> images) {
        // 1. 准备图片信息

        // 2. 调用领域服务更新位置（包含图片）
        StorageLocationAggregate location = locationDomainService.updateLocationWithImages(
                locationId,
                name,
                description,
                address,
                images
        );

        // 3. 更新位置
        locationRepository.update(location);

        // 4. 记录日志
        log.info("位置更新成功，位置ID: {}, 新位置名称: {}, 图片数量: {}", locationId, name, images.size());
    }

    /**
     * 删除位置（逻辑删除）
     *
     * @param userId     用户ID
     * @param locationId 位置ID
     */
    public void deleteLocation(String userId, String locationId) {
        // 1. 验证权限：确保位置属于当前用户
        StorageLocationAggregate existingLocation = locationRepository.findById(locationId);
        if (!existingLocation.getUserId().equals(userId)) {
            throw WardrobeException.of("PERMISSION_DENIED", "无权删除该位置");
        }

        // 2. 调用领域服务验证并获取可删除的位置聚合根
        // Domain Service负责业务规则校验（如是否有服装引用）
        StorageLocationAggregate location = locationDomainService.validateAndGetForDeletion(locationId);

        // 3. 删除位置
        locationRepository.delete(locationId);

        // 4. 记录日志
        log.info("位置删除成功，位置ID: {}, 用户ID: {}", locationId, userId);
    }

    /**
     * 为位置添加标签
     *
     * @param userId     用户ID
     * @param locationId 位置ID
     * @param tagId      标签ID
     */
    public void associateTagToLocation(String userId, String locationId, String tagId) {
        // 1. 验证位置是否存在且属于当前用户
        StorageLocationAggregate location = locationRepository.findById(locationId);
        if (location == null) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NOT_FOUND, "位置不存在");
        }
        if (!location.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PERMISSION_DENIED, "无权操作该位置");
        }
        
        // 2. 构建标签关联命令
        AssociateEntityCommand command = AssociateEntityCommand.builder()
                .tagId(tagId)
                .entityId(locationId)
                .entityTypeCode(EntityTypeConstants.LOCATION)
                .build();
        
        // 3. 调用标签RPC服务
        Result<Void> result = rpcTagService.associateTags(userId, command);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            throw WardrobeException.of(WardrobeErrorCode.TAG_ASSOCIATION_FAILED, "标签关联失败: " + result.getMsg());
        }
        
        // 4. 记录日志
        log.info("位置标签关联成功，用户ID: {}, 位置ID: {}, 标签ID: {}", userId, locationId, tagId);
    }

    /**
     * 从位置移除标签
     *
     * @param userId     用户ID
     * @param locationId 位置ID
     * @param tagId      标签ID
     */
    public void dissociateTagFromLocation(String userId, String locationId, String tagId) {
        // 1. 验证位置是否存在且属于当前用户
        StorageLocationAggregate location = locationRepository.findById(locationId);
        if (location == null) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_NOT_FOUND, "位置不存在");
        }
        if (!location.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PERMISSION_DENIED, "无权操作该位置");
        }
        
        // 2. 构建标签取消关联命令
        DissociateEntityCommand command = DissociateEntityCommand.builder()
                .tagId(tagId)
                .entityId(locationId)
                .build();
        
        // 3. 调用标签RPC服务
        Result<Void> result = rpcTagService.dissociateTags(userId, command);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            throw WardrobeException.of(WardrobeErrorCode.TAG_DISSOCIATION_FAILED, "标签取消关联失败: " + result.getMsg());
        }
        
        // 4. 记录日志
        log.info("位置标签取消关联成功，用户ID: {}, 位置ID: {}, 标签ID: {}", userId, locationId, tagId);
    }

    /**
     * 为位置创建扣减金币
     *
     * @param userId     用户ID
     * @param locationId 位置ID
     */
    private void deductCoinsForLocationCreation(String userId, String locationId) {
        try {
            ProcessLocationCreationCommand command = ProcessLocationCreationCommand.builder()
                    .userId(userId)
                    .locationId(locationId)
                    .build();
            
            Result<Void> result = rpcCoinService.processLocationCreation(userId, command);
            
            if (!Boolean.TRUE.equals(result.getSuccess())) {
                log.error("位置创建金币扣减失败，位置ID: {}, 用户ID: {}, 错误: {}", 
                        locationId, userId, result.getMsg());
                throw WardrobeException.of(WardrobeErrorCode.COIN_DEDUCTION_FAILED, 
                        "金币扣减失败: " + result.getMsg());
            } else {
                log.info("位置创建金币扣减成功，位置ID: {}, 用户ID: {}", locationId, userId);
            }
        } catch (Exception e) {
            log.error("调用金币服务异常，位置ID: {}, 用户ID: {}", locationId, userId, e);
            throw WardrobeException.of(WardrobeErrorCode.COIN_SERVICE_UNAVAILABLE, 
                    "金币服务不可用: " + e.getMessage());
        }
    }

    /**
     * 创建位置记录
     *
     * @param clothingId     服装ID
     * @param locationId     位置ID
     * @param userId         用户ID
     * @return 创建的位置记录
     */
    public LocationRecordAggregate createLocationRecord(String clothingId, String locationId, String userId) {
        if (StrUtil.isNotBlank(locationId)) {
            LocationRecordAggregate locationRecord = locationRecordDomainService.createLocationRecord(
                    clothingId,
                    locationId,
                    userId
            );
            locationRecordRepository.save(locationRecord);

            log.info("服装位置记录创建成功，服装ID: {}, 位置ID: {}, 位置记录ID: {}",
                    clothingId, locationId, locationRecord.getId());
            return locationRecord;
        }
        return null;
    }

    /**
     * 处理位置变更
     *
     * @param clothingId     服装ID
     * @param oldLocationId  旧位置ID
     * @param newLocationId  新位置ID
     * @param userId         用户ID
     * @return 是否创建了新的位置记录
     */
    public boolean handleLocationChange(String clothingId, String oldLocationId, String newLocationId, String userId) {
        boolean locationChanged = checkLocationChanged(oldLocationId, newLocationId);
        if (locationChanged && StrUtil.isNotBlank(newLocationId)) {
            // 将旧位置记录标记为非当前
            locationRecordRepository.markAllAsNotCurrentByClothingId(clothingId);

            // 创建新的位置记录
            createLocationRecord(clothingId, newLocationId, userId);

            log.info("服装位置变更记录创建成功，服装ID: {}, 旧位置ID: {}, 新位置ID: {}",
                    clothingId, oldLocationId, newLocationId);
            return true;
        }
        return false;
    }

    /**
     * 检查位置是否发生变化
     *
     * @param oldLocationId 旧位置ID
     * @param newLocationId 新位置ID
     * @return 如果位置发生变化返回true，否则返回false
     */
    private boolean checkLocationChanged(String oldLocationId, String newLocationId) {
        // 如果旧位置ID和新位置ID都为null，表示没有变化
        if (oldLocationId == null && newLocationId == null) {
            return false;
        }

        // 如果旧位置ID为null，新位置ID不为null，表示从无位置变为有位置
        if (oldLocationId == null) {
            return true;
        }

        // 如果旧位置ID不为null，新位置ID为null，表示从有位置变为无位置
        if (newLocationId == null) {
            return true;
        }

        // 如果都不为null，比较是否相等
        return !oldLocationId.equals(newLocationId);
    }
}
