package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity.ClothingImage;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.ClothingImagePO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 服装图片基础设施层转换器
 * <p>
 * 负责ClothingImage和ClothingImagePO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Mapper
public interface ClothingImageInfraConvertor {

    ClothingImageInfraConvertor INSTANCE = Mappers.getMapper(ClothingImageInfraConvertor.class);

    /**
     * ClothingImage转换为ClothingImagePO
     *
     * @param entity 服装图片实体
     * @return 服装图片持久化对象
     */
    ClothingImagePO toPO(ClothingImage entity);

    /**
     * ClothingImagePO转换为ClothingImage
     *
     * @param po 服装图片持久化对象
     * @return 服装图片实体
     */
    ClothingImage toEntity(ClothingImagePO po);

    /**
     * ClothingImage列表转换为ClothingImagePO列表
     *
     * @param entities 服装图片实体列表
     * @return 服装图片持久化对象列表
     */
    List<ClothingImagePO> toPOList(List<ClothingImage> entities);

    /**
     * ClothingImagePO列表转换为ClothingImage列表
     *
     * @param pos 服装图片持久化对象列表
     * @return 服装图片实体列表
     */
    List<ClothingImage> toEntityList(List<ClothingImagePO> pos);
}
