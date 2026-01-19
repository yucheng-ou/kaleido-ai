package com.xiaoo.kaleido.wardrobe.application.convertor;

import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;
import com.xiaoo.kaleido.api.wardrobe.response.ClothingInfoResponse;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity.ClothingImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 衣橱转换器
 * <p>
 * 衣橱应用层转换器，负责衣橱领域对象与应用层DTO之间的转换
 * 使用MapStruct自动生成实现代码
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Mapper(componentModel = "spring")
public interface WardrobeConvertor {

    /**
     * 将品牌聚合根转换为品牌信息响应
     * <p>
     * 将领域层的品牌聚合根转换为应用层的品牌信息响应DTO
     *
     * @param brandAggregate 品牌聚合根，不能为空
     * @return 品牌信息响应，包含品牌的基本信息和状态
     */
    @Mapping(source = "id", target = "brandId")
    BrandInfoResponse toBrandResponse(BrandAggregate brandAggregate);

    /**
     * 将服装聚合根转换为服装信息响应
     * <p>
     * 将领域层的服装聚合根转换为应用层的服装信息响应DTO
     *
     * @param clothingAggregate 服装聚合根，不能为空
     * @return 服装信息响应，包含服装的基本信息和图片信息
     */
    @Mapping(source = "id", target = "clothingId")
    @Mapping(source = "images", target = "images")
    ClothingInfoResponse toClothingResponse(ClothingAggregate clothingAggregate);

    /**
     * 将服装图片实体列表转换为图片信息响应列表
     * <p>
     * 将领域层的服装图片实体列表转换为应用层的图片信息响应DTO列表
     *
     * @param clothingImages 服装图片实体列表，不能为空
     * @return 图片信息响应列表
     */
    List<ClothingInfoResponse.ImageInfo> toImageResponseList(List<ClothingImage> clothingImages);
}
