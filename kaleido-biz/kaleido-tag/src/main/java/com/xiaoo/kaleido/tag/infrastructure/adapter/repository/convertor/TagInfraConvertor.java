package com.xiaoo.kaleido.tag.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.tag.domain.model.aggregate.TagAggregate;
import com.xiaoo.kaleido.tag.infrastructure.dao.po.TagPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 标签基础设施层转换器
 * <p>
 * 负责TagAggregate和TagPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Mapper
public interface TagInfraConvertor {

    TagInfraConvertor INSTANCE = Mappers.getMapper(TagInfraConvertor.class);

    /**
     * TagAggregate转换为TagPO
     *
     * @param aggregate 标签聚合根
     * @return 标签持久化对象
     */
    TagPO toPO(TagAggregate aggregate);

    /**
     * TagPO转换为TagAggregate
     *
     * @param po 标签持久化对象
     * @return 标签聚合根
     */
    @Mapping(target = "relations", ignore = true)
    TagAggregate toAggregate(TagPO po);
}
