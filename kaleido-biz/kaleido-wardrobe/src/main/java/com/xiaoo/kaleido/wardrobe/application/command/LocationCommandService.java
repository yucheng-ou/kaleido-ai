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
 * <p>
 * 负责编排位置相关的命令操作，包括创建、更新、删除等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
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
    private final LocationRecordCommandService locationRecordCommandService;

    /**
     * 创建位置（包含图片）
     *
     * @param command 创建位置命令
     * @return 创建的位置ID
     */
    public String createLocation(CreateLocationWithImagesCommand command) {
        // 1. 使用模板方法转换图片信息
        List<LocationImageInfoDTO> imageInfoDTOS = locationFileService.convertorImageInfo(command.getImages());

        // 2. 调用原有方法
        return createLocationWithImages(
                command.getUserId(),
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
     * @param command 更新位置命令
     */
    public void updateLocation(UpdateLocationCommand command) {
        // 1. 使用模板方法转换图片信息
        List<LocationImageInfoDTO> imageInfoDTOS = locationFileService.convertorImageInfo(command.getImages());

        // 2. 调用原有方法
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
     * @param locationId 位置ID
     */
    public void deleteLocation(String locationId) {
        // 1. 调用领域服务验证并获取可删除的位置聚合根
        // Domain Service负责业务规则校验（如是否有服装引用）
        StorageLocationAggregate location = locationDomainService.validateAndGetForDeletion(locationId);
        
        // 2. 删除位置
        locationRepository.delete(locationId);

        // 3. 记录日志
        log.info("位置删除成功，位置ID: {}", locationId);
    }

    /**
     * 设置主图
     *
     * @param locationId 位置ID
     * @param imageId    图片ID
     */
    public void setPrimaryImage(String locationId, String imageId) {
        // 1. 调用领域服务设置主图
        StorageLocationAggregate location = locationDomainService.setPrimaryImage(locationId, imageId);

        // 2. 更新位置
        locationRepository.update(location);

        // 3. 记录日志
        log.info("位置主图设置成功，位置ID: {}, 主图ID: {}", locationId, imageId);
    }

    /**
     * 将衣服添加到位置
     *
     * @param command 将衣服添加到位置命令
     */
    public void addClothingToLocation(AddClothingToLocationCommand command) {
        // 调用位置记录命令服务
        locationRecordCommandService.addClothingToLocation(command);
    }
}
