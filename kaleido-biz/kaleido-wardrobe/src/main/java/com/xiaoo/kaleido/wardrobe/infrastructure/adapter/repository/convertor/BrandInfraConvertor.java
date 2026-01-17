package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.BrandPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 品牌基础设施层转换器
 * <p>
 * 负责BrandAggregate和BrandPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Mapper
public interface BrandInfraConvertor {

    BrandInfraConvertor INSTANCE = Mappers.getMapper(BrandInfraConvertor.class);

    /**
     * BrandAggregate转换为BrandPO
     *
     * @param aggregate 品牌聚合根
     * @return 品牌持久化对象
     */
    BrandPO toPO(BrandAggregate aggregate);

    /**
     * BrandPO转换为BrandAggregate
     *
     * @param po 品牌持久化对象
     * @return 品牌聚合根
     */
    BrandAggregate toAggregate(BrandPO po);
}
