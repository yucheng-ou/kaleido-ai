package com.xiaoo.kaleido.wardrobe.application.query.impl;

import com.xiaoo.kaleido.api.wardrobe.response.LocationInfoResponse;
import com.xiaoo.kaleido.wardrobe.application.convertor.LocationConvertor;
import com.xiaoo.kaleido.wardrobe.application.query.ILocationQueryService;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.model.entity.LocationImage;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 位置查询服务实现
 * <p>
 * 负责位置相关的查询操作，包括位置详情查询、列表查询等
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationQueryServiceImpl implements ILocationQueryService {

    private final ILocationRepository locationRepository;
    private final LocationConvertor locationConvertor;

    /**
     * 根据位置ID查询位置详情
     *
     * @param locationId 位置ID
     * @return 位置信息响应
     */
    @Override
    public LocationInfoResponse findById(String locationId) {
        try {
            // 1.查询位置聚合根
            StorageLocationAggregate location = locationRepository.findByIdOrThrow(locationId);

            // 2.转换为DTO
            LocationDTO locationDTO = locationConvertor.toDTO(location);
            
            // 3.获取图片列表
            List<LocationImage> images = location.getImages();
            List<LocationConvertor.LocationImageDTO> imageDTOs = locationConvertor.toImageDTOList(images);
            
            // 4.转换为Response
            LocationInfoResponse response = locationConvertor.toResponse(locationDTO);
            response.setImages(locationConvertor.toImageResponseList(imageDTOs));
            
            // 5.设置创建时间和更新时间（需要从聚合根获取）
            // 注意：这里需要从聚合根中获取时间信息，假设聚合根有这些字段
            // response.setCreatedAt(location.getCreatedAt());
            // response.setUpdatedAt(location.getUpdatedAt());
            
            return response;

        } catch (Exception e) {
            log.error("查询位置详情失败，位置ID: {}, 原因: {}", locationId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "位置详情查询失败");
        }
    }

    /**
     * 根据用户ID查询位置列表
     *
     * @param userId 用户ID
     * @return 位置信息响应列表
     */
    @Override
    public List<LocationInfoResponse> findByUserId(String userId) {
        try {
            // 1.查询用户的所有位置聚合根
            List<StorageLocationAggregate> locations = locationRepository.findByUserId(userId);

            // 2.转换为Response列表
            return locations.stream()
                    .map(location -> {
                        LocationDTO locationDTO = locationConvertor.toDTO(location);
                        LocationInfoResponse response = locationConvertor.toResponse(locationDTO);
                        
                        // 设置图片列表
                        List<LocationImage> images = location.getImages();
                        List<LocationConvertor.LocationImageDTO> imageDTOs = locationConvertor.toImageDTOList(images);
                        response.setImages(locationConvertor.toImageResponseList(imageDTOs));
                        
                        return response;
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("查询用户位置列表失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "用户位置列表查询失败");
        }
    }

    /**
     * 根据位置ID列表查询位置列表
     *
     * @param locationIds 位置ID列表
     * @return 位置信息响应列表
     */
    @Override
    public List<LocationInfoResponse> findByIds(List<String> locationIds) {
        try {
            // 1.查询位置ID列表对应的位置聚合根
            List<StorageLocationAggregate> locations = locationRepository.findByIds(locationIds);

            // 2.转换为Response列表
            return locations.stream()
                    .map(location -> {
                        LocationDTO locationDTO = locationConvertor.toDTO(location);
                        LocationInfoResponse response = locationConvertor.toResponse(locationDTO);
                        
                        // 设置图片列表
                        List<LocationImage> images = location.getImages();
                        List<LocationConvertor.LocationImageDTO> imageDTOs = locationConvertor.toImageDTOList(images);
                        response.setImages(locationConvertor.toImageResponseList(imageDTOs));
                        
                        return response;
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("查询位置列表失败，位置ID列表: {}, 原因: {}", locationIds, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "位置列表查询失败");
        }
    }

    /**
     * 检查位置是否存在
     *
     * @param locationId 位置ID
     * @return 是否存在
     */
    @Override
    public boolean existsById(String locationId) {
        try {
            return locationRepository.findById(locationId).isPresent();
        } catch (Exception e) {
            log.error("检查位置存在性失败，位置ID: {}, 原因: {}", locationId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "位置存在性检查失败");
        }
    }

    /**
     * 位置数据传输对象
     */
    public static class LocationDTO {
        private String id;
        private String userId;
        private String name;
        private String description;
        private String address;
        private String primaryImageId;
        // 可以添加更多字段，如创建时间、更新时间等

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPrimaryImageId() {
            return primaryImageId;
        }

        public void setPrimaryImageId(String primaryImageId) {
            this.primaryImageId = primaryImageId;
        }
    }
}
