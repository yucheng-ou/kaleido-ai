package com.xiaoo.kaleido.recommend.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate.RecommendRecordAggregate;
import com.xiaoo.kaleido.recommend.infrastructure.dao.po.RecommendRecordPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 推荐记录基础设施层转换器
 * <p>
 * 负责RecommendRecordAggregate和RecommendRecordPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Mapper
public interface RecommendRecordInfraConvertor {

    RecommendRecordInfraConvertor INSTANCE = Mappers.getMapper(RecommendRecordInfraConvertor.class);

    /**
     * RecommendRecordAggregate转换为RecommendRecordPO
     *
     * @param aggregate 推荐记录聚合根
     * @return 推荐记录持久化对象
     */
    RecommendRecordPO toPO(RecommendRecordAggregate aggregate);

    /**
     * RecommendRecordPO转换为RecommendRecordAggregate
     *
     * @param po 推荐记录持久化对象
     * @return 推荐记录聚合根
     */
    RecommendRecordAggregate toAggregate(RecommendRecordPO po);
}
