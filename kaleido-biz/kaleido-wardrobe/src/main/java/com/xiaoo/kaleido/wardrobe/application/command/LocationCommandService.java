package com.xiaoo.kaleido.wardrobe.application.command;

import com.xiaoo.kaleido.api.wardrobe.command.CreateLocationWithImagesCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateLocationCommand;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationDomainService;
import com.xiaoo.kaleido.wardrobe.domain.location.service.dto.LocationImageInfoDTO;
import com.xiaoo.kaleido.api.wardrobe.enums.ImageType;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    private final ImageProcessingService imageProcessingService;

    /**
     * 创建位置（包含图片）
     *
     * @param command 创建位置命令
     * @return 创建的位置ID
     */
    public String createLocation(CreateLocationWithImagesCommand command) {
        // 1. 使用模板方法转换图片信息
        List<LocationImageInfo> imageInfos = LocationImageInfoConverter.convertCreateCommandImages(command.getImages());

        // 2. 调用原有方法
        return createLocationWithImages(
                command.getUserId(),
                command.getName(),
                command.getDescription(),
                command.getAddress(),
                imageInfos
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
            List<LocationImageInfo> images) {
        // 1. 准备图片信息
        List<LocationImageInfoDTO> domainImageInfos = prepareImageInfos(images);

        // 2. 调用领域服务创建位置（包含图片）
        StorageLocationAggregate location = locationDomainService.createLocationWithImages(
                userId,
                name,
                description,
                address,
                domainImageInfos
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
        List<LocationImageInfo> imageInfos = LocationImageInfoConverter.convertUpdateCommandImages(command.getImages());

        // 2. 调用原有方法
        updateLocationWithImages(
                command.getLocationId(),
                command.getName(),
                command.getDescription(),
                command.getAddress(),
                imageInfos
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
            List<LocationImageInfo> images) {
        // 1. 准备图片信息
        List<LocationImageInfoDTO> domainImageInfos = prepareImageInfos(images);

        // 2. 调用领域服务更新位置（包含图片）
        StorageLocationAggregate location = locationDomainService.updateLocationWithImages(
                locationId,
                name,
                description,
                address,
                domainImageInfos
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
        // 1. 检查是否可以删除（是否有服装引用）
        if (!locationDomainService.canDeleteLocation(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.LOCATION_HAS_REFERENCES);
        }

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
     * 准备图片信息
     * <p>
     * 将用户传入的图片信息转换为领域服务需要的格式，同时从MinIO获取图片详细信息
     *
     * @param images 用户传入的图片信息列表
     * @return 领域服务需要的图片信息DTO列表
     */
    private List<LocationImageInfoDTO> prepareImageInfos(List<LocationImageInfo> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }

        return imageProcessingService.processImages(
                images.stream()
                        .map(ImageInfoAdapter::fromLocationCommandImageInfo)
                        .collect(java.util.stream.Collectors.toList()),
                (adapter, minioInfo) -> {
                    if (minioInfo != null) {
                        return LocationImageInfoDTO.builder()
                                .path(adapter.getPath())
                                .imageOrder(adapter.getImageOrder())
                                .isPrimary(adapter.getIsPrimary())
                                .imageSize(minioInfo.getFileSize())
                                .width(minioInfo.getWidth())
                                .height(minioInfo.getHeight())
                                .imageType(ImageType.fromMimeType(minioInfo.getMimeType()))
                                .build();
                    } else {
                        return LocationImageInfoDTO.builder()
                                .path(adapter.getPath())
                                .imageOrder(adapter.getImageOrder())
                                .isPrimary(adapter.getIsPrimary())
                                .build();
                    }
                }
        );
    }

    /**
     * 位置图片信息（内部类）
     */
    @Data
    public static class LocationImageInfo {
        /**
         * 图片路径（在minio中的文件路径）
         */
        private String path;

        /**
         * 排序序号
         */
        private Integer imageOrder;

        /**
         * 是否为主图
         */
        private Boolean isPrimary;
    }
}
