package com.xiaoo.kaleido.wardrobe.application.convertor;

import com.xiaoo.kaleido.api.wardrobe.response.OutfitImageResponse;
import com.xiaoo.kaleido.api.wardrobe.response.OutfitInfoResponse;
import com.xiaoo.kaleido.api.wardrobe.response.WearRecordResponse;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.WearRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 穿搭转换器
 * <p>
 * 穿搭应用层转换器，负责穿搭领域对象与应用层DTO之间的转换
 * 使用MapStruct自动生成实现代码
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Mapper(componentModel = "spring")
public interface OutfitConvertor {

    /**
     * 将穿搭聚合根转换为API响应
     *
     * @param outfitAggregate 穿搭聚合根，不能为空
     * @return 穿搭信息响应
     */
    @Mapping(source = "id", target = "outfitId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "wearCount", target = "wearCount")
    @Mapping(source = "lastWornDate", target = "lastWornDate")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "wearRecords", target = "wearRecords")
    OutfitInfoResponse toOutfitResponse(OutfitAggregate outfitAggregate);

    /**
     * 将穿搭图片实体转换为API响应
     *
     * @param outfitImage 穿搭图片实体，不能为空
     * @return 穿搭图片响应
     */
    @Mapping(source = "id", target = "imageId")
    @Mapping(source = "path", target = "path")
    @Mapping(source = "imageOrder", target = "imageOrder")
    @Mapping(source = "primary", target = "isPrimary")
    @Mapping(source = "width", target = "width")
    @Mapping(source = "height", target = "height")
    @Mapping(source = "imageSize", target = "fileSize")
    @Mapping(source = "imageType", target = "imageType")
    @Mapping(source = "description", target = "description")
    OutfitImageResponse toOutfitImageResponse(OutfitImage outfitImage);

    /**
     * 将穿搭图片实体列表转换为API响应列表
     *
     * @param outfitImages 穿搭图片实体列表，不能为空
     * @return 穿搭图片响应列表
     */
    List<OutfitImageResponse> toOutfitImageResponseList(List<OutfitImage> outfitImages);

    /**
     * 将穿着记录实体转换为API响应
     *
     * @param wearRecord 穿着记录实体，不能为空
     * @return 穿着记录响应
     */
    @Mapping(source = "id", target = "recordId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "outfitId", target = "outfitId")
    @Mapping(source = "wearDate", target = "wearDate")
    @Mapping(source = "notes", target = "notes")
    @Mapping(source = "createdAt", target = "createdAt")
    WearRecordResponse toWearRecordResponse(WearRecord wearRecord);

    /**
     * 将穿着记录实体列表转换为API响应列表
     *
     * @param wearRecords 穿着记录实体列表，不能为空
     * @return 穿着记录响应列表
     */
    List<WearRecordResponse> toWearRecordResponseList(List<WearRecord> wearRecords);
}
