package com.xiaoo.kaleido.wardrobe.application.convertor;

import com.xiaoo.kaleido.api.wardrobe.response.LocationImageResponse;
import com.xiaoo.kaleido.api.wardrobe.response.LocationInfoResponse;
import com.xiaoo.kaleido.wardrobe.application.query.impl.LocationQueryServiceImpl;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.model.entity.LocationImage;
import com.xiaoo.kaleido.api.wardrobe.enums.ImageType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 位置转换器
 * <p>
 * 位置应用层转换器，负责位置领域对象与应用层DTO之间的转换
 * 使用MapStruct自动生成实现代码
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Mapper(componentModel = "spring")
public interface LocationConvertor {

    /**
     * 将位置聚合根转换为位置DTO
     * <p>
     * 将领域层的位置聚合根转换为应用层的位置DTO
     *
     * @param locationAggregate 位置聚合根，不能为空
     * @return 位置DTO，包含位置的基本信息
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "primaryImageId", target = "primaryImageId")
    LocationQueryServiceImpl.LocationDTO toDTO(StorageLocationAggregate locationAggregate);

    /**
     * 将位置图片实体转换为图片信息DTO
     * <p>
     * 将领域层的位置图片实体转换为应用层的图片信息DTO
     *
     * @param locationImage 位置图片实体，不能为空
     * @return 图片信息DTO，包含图片的基本信息
     */
    @Mapping(source = "id", target = "imageId")
    LocationImageDTO toImageDTO(LocationImage locationImage);

    /**
     * 将位置图片信息DTO转换为API响应
     * <p>
     * 将应用层的图片信息DTO转换为API层的图片响应
     *
     * @param imageDTO 图片信息DTO，不能为空
     * @return 图片响应，包含图片的基本信息
     */
    LocationImageResponse toImageResponse(LocationImageDTO imageDTO);

    /**
     * 将位置图片信息DTO列表转换为API响应列表
     * <p>
     * 将应用层的图片信息DTO列表转换为API层的图片响应列表
     *
     * @param imageDTOs 图片信息DTO列表，不能为空
     * @return 图片响应列表
     */
    List<LocationImageResponse> toImageResponseList(List<LocationImageDTO> imageDTOs);

    /**
     * 将位置图片实体列表转换为图片信息DTO列表
     * <p>
     * 将领域层的位置图片实体列表转换为应用层的图片信息DTO列表
     *
     * @param locationImages 位置图片实体列表，不能为空
     * @return 图片信息DTO列表
     */
    List<LocationImageDTO> toImageDTOList(List<LocationImage> locationImages);

    /**
     * 将位置DTO转换为API响应
     * <p>
     * 将应用层的位置DTO转换为API层的位置信息响应
     *
     * @param locationDTO 位置DTO，不能为空
     * @return 位置信息响应，包含位置的基本信息和图片列表
     */
    @Mapping(source = "id", target = "locationId")
    LocationInfoResponse toResponse(LocationQueryServiceImpl.LocationDTO locationDTO);

    /**
     * 位置图片信息DTO
     */
    class LocationImageDTO {
        private String imageId;
        private String path;
        private Integer imageOrder;
        private Boolean isPrimary;
        private Integer width;
        private Integer height;
        private Long fileSize;
        private ImageType imageType;

        // Getters and Setters
        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getImageOrder() {
            return imageOrder;
        }

        public void setImageOrder(Integer imageOrder) {
            this.imageOrder = imageOrder;
        }

        public Boolean getIsPrimary() {
            return isPrimary;
        }

        public void setIsPrimary(Boolean isPrimary) {
            this.isPrimary = isPrimary;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Long getFileSize() {
            return fileSize;
        }

        public void setFileSize(Long fileSize) {
            this.fileSize = fileSize;
        }

        public ImageType getImageType() {
            return imageType;
        }

        public void setImageType(ImageType imageType) {
            this.imageType = imageType;
        }
    }
}
