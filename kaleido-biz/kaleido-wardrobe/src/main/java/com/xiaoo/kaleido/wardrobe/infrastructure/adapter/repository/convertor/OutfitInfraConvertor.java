package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.api.wardrobe.enums.ImageTypeEnums;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitClothing;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitClothingPO;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitImagePO;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 穿搭基础设施层转换器
 * <p>
 * 负责领域对象和持久化对象之间的转换
 * 使用MapStruct实现，编译时生成转换代码
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Mapper
public interface OutfitInfraConvertor {

    OutfitInfraConvertor INSTANCE = Mappers.getMapper(OutfitInfraConvertor.class);

    /**
     * 将OutfitPO转换为OutfitAggregate
     *
     * @param po 持久化对象
     * @return 领域聚合根
     */
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "clothings", ignore = true)
    @Mapping(target = "wearRecords", ignore = true)
    OutfitAggregate toAggregate(OutfitPO po);

    /**
     * 将OutfitAggregate转换为OutfitPO
     *
     * @param aggregate 领域聚合根
     * @return 持久化对象
     */
    OutfitPO toPO(OutfitAggregate aggregate);

    /**
     * 将OutfitImagePO列表转换为OutfitImage列表
     *
     * @param pos 持久化对象列表
     * @return 领域实体列表
     */
    List<OutfitImage> toImageEntities(List<OutfitImagePO> pos);

    /**
     * 将OutfitImage列表转换为OutfitImagePO列表
     *
     * @param entities 领域实体列表
     * @return 持久化对象列表
     */
    List<OutfitImagePO> toImagePOs(List<OutfitImage> entities);


    /**
     * 将OutfitClothingPO列表转换为OutfitClothing列表
     *
     * @param pos 持久化对象列表
     * @return 领域实体列表
     */
    List<OutfitClothing> toClothingEntities(List<OutfitClothingPO> pos);

    /**
     * 将OutfitClothing列表转换为OutfitClothingPO列表
     *
     * @param entities 领域实体列表
     * @return 持久化对象列表
     */
    List<OutfitClothingPO> toClothingPOs(List<OutfitClothing> entities);
}
