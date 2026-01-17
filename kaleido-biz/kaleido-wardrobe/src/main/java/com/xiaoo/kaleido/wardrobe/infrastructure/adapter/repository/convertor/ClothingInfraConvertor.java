package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.ClothingPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 服装基础设施层转换器
 * <p>
 * 负责ClothingAggregate和ClothingPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Mapper
public interface ClothingInfraConvertor {

    ClothingInfraConvertor INSTANCE = Mappers.getMapper(ClothingInfraConvertor.class);

    /**
     * ClothingAggregate转换为ClothingPO
     *
     * @param aggregate 服装聚合根
     * @return 服装持久化对象
     */
    ClothingPO toPO(ClothingAggregate aggregate);

    /**
     * ClothingPO转换为ClothingAggregate
     *
     * @param po 服装持久化对象
     * @return 服装聚合根
     */
    @Mapping(target = "images", ignore = true)
    ClothingAggregate toAggregate(ClothingPO po);
}
