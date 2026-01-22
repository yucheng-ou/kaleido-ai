package com.xiaoo.kaleido.wardrobe.application.command;

import com.xiaoo.kaleido.api.wardrobe.command.AddClothingToLocationCommand;
import com.xiaoo.kaleido.api.wardrobe.command.CreateLocationWithImagesCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateLocationCommand;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.file.ILocationFileService;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationDomainService;
import com.xiaoo.kaleido.wardrobe.domain.location.service.dto.LocationImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.file.ImageProcessingService;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    /**
     * 创建位置（包含图片）
     *
     * @param command 创建位置命令
     * @return 创建的位置ID
     */
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
}
