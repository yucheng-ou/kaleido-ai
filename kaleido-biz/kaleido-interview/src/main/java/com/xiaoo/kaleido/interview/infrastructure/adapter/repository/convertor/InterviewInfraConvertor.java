package com.xiaoo.kaleido.interview.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.interview.domain.interview.model.aggregate.InterviewAggregate;
import com.xiaoo.kaleido.interview.infrastructure.dao.po.InterviewPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 面试基础设施层转换器
 * <p>
 * 负责InterviewAggregate和InterviewPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Mapper
public interface InterviewInfraConvertor {

    InterviewInfraConvertor INSTANCE = Mappers.getMapper(InterviewInfraConvertor.class);

    /**
     * InterviewAggregate转换为InterviewPO
     *
     * @param aggregate 面试聚合根
     * @return 面试持久化对象
     */
    InterviewPO toPO(InterviewAggregate aggregate);

    /**
     * InterviewPO转换为InterviewAggregate
     *
     * @param po 面试持久化对象
     * @return 面试聚合根
     */
    InterviewAggregate toAggregate(InterviewPO po);

    /**
     * InterviewPO列表转换为InterviewAggregate列表
     *
     * @param pos 面试持久化对象列表
     * @return 面试聚合根列表
     */
    List<InterviewAggregate> toAggregateList(List<InterviewPO> pos);
}
