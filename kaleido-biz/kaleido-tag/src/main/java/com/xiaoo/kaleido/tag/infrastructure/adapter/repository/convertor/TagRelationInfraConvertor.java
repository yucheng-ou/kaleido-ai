package com.xiaoo.kaleido.tag.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.tag.domain.model.entity.TagRelation;
import com.xiaoo.kaleido.tag.infrastructure.dao.po.TagRelationPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 标签关联基础设施层转换器
 * <p>
 * 负责TagRelation和TagRelationPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Mapper
public interface TagRelationInfraConvertor {

    TagRelationInfraConvertor INSTANCE = Mappers.getMapper(TagRelationInfraConvertor.class);

    /**
     * TagRelation转换为TagRelationPO
     *
     * @param relation 标签关联实体
     * @return 标签关联持久化对象
     */
    TagRelationPO toPO(TagRelation relation);

    /**
     * TagRelationPO转换为TagRelation
     *
     * @param po 标签关联持久化对象
     * @return 标签关联实体
     */
    TagRelation toEntity(TagRelationPO po);

    /**
     * TagRelation列表转换为TagRelationPO列表
     *
     * @param relations 标签关联实体列表
     * @return 标签关联持久化对象列表
     */
    List<TagRelationPO> toPOList(List<TagRelation> relations);

    /**
     * TagRelationPO列表转换为TagRelation列表
     *
     * @param pos 标签关联持久化对象列表
     * @return 标签关联实体列表
     */
    List<TagRelation> toEntityList(List<TagRelationPO> pos);
}
