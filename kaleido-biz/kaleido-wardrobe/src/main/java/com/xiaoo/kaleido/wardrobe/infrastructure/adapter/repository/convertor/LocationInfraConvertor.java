package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.model.entity.LocationImage;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.LocationPO;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.LocationImagePO;
import com.xiaoo.kaleido.api.wardrobe.enums.ImageType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 位置基础设施层转换器
 * <p>
 * 负责StorageLocationAggregate和LocationPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Mapper
public interface LocationInfraConvertor {

    LocationInfraConvertor INSTANCE = Mappers.getMapper(LocationInfraConvertor.class);

    /**
     * StorageLocationAggregate转换为LocationPO
     *
     * @param aggregate 位置聚合根
     * @return 位置持久化对象
     */
    LocationPO toPO(StorageLocationAggregate aggregate);

    /**
     * LocationPO转换为StorageLocationAggregate
     *
     * @param po 位置持久化对象
     * @return 位置聚合根
     */
    StorageLocationAggregate toAggregate(LocationPO po);

    /**
     * LocationImage列表转换为LocationImagePO列表
     *
     * @param images 位置图片实体列表
     * @return 位置图片持久化对象列表
     */
    List<LocationImagePO> toImagePOs(List<LocationImage> images);

    /**
     * LocationImagePO转换为LocationImage
     *
     * @param po 位置图片持久化对象
     * @return 位置图片实体
     */
    LocationImage toImageEntity(LocationImagePO po);

    /**
     * LocationImagePO列表转换为LocationImage列表
     *
     * @param pos 位置图片持久化对象列表
     * @return 位置图片实体列表
     */
    List<LocationImage> toImageEntities(List<LocationImagePO> pos);

    /**
     * LocationImage转换为LocationImagePO
     *
     * @param entity 位置图片实体
     * @return 位置图片持久化对象
     */
    LocationImagePO toImagePO(LocationImage entity);

    /**
     * 将ImageType转换为String（MIME类型）
     *
     * @param imageType 图片类型
     * @return MIME类型字符串
     */
    default String imageTypeToString(ImageType imageType) {
        if (imageType == null) {
            return null;
        }
        return imageType.getMimeType();
    }

    /**
     * 将String（MIME类型）转换为ImageType
     *
     * @param mimeType MIME类型字符串
     * @return 图片类型
     */
    default ImageType stringToImageType(String mimeType) {
        if (mimeType == null || mimeType.trim().isEmpty()) {
            return null;
        }
        return ImageType.fromMimeType(mimeType);
    }
}
