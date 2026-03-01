package com.xiaoo.kaleido.interview.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;
import com.xiaoo.kaleido.interview.domain.candidate.model.vo.CandidateStatus;
import com.xiaoo.kaleido.interview.infrastructure.dao.po.CandidatePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 候选人基础设施层转换器
 * <p>
 * 负责CandidateAggregate和CandidatePO之间的转换
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Mapper
public interface CandidateInfraConvertor {

    CandidateInfraConvertor INSTANCE = Mappers.getMapper(CandidateInfraConvertor.class);

    /**
     * CandidateAggregate转换为CandidatePO
     *
     * @param aggregate 候选人聚合根
     * @return 候选人持久化对象
     */
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToCode")
    CandidatePO toPO(CandidateAggregate aggregate);

    /**
     * CandidatePO转换为CandidateAggregate
     *
     * @param po 候选人持久化对象
     * @return 候选人聚合根
     */
    @Mapping(target = "status", source = "status", qualifiedByName = "codeToStatus")
    CandidateAggregate toAggregate(CandidatePO po);

    /**
     * CandidatePO列表转换为CandidateAggregate列表
     *
     * @param pos 候选人持久化对象列表
     * @return 候选人聚合根列表
     */
    List<CandidateAggregate> toAggregateList(List<CandidatePO> pos);

    /**
     * 状态枚举转换为状态编码
     *
     * @param status 候选人状态
     * @return 状态编码
     */
    @Named("statusToCode")
    default String statusToCode(CandidateStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    /**
     * 状态编码转换为状态枚举
     *
     * @param code 状态编码
     * @return 候选人状态
     */
    @Named("codeToStatus")
    default CandidateStatus codeToStatus(String code) {
        if (code == null) {
            return null;
        }
        return CandidateStatus.fromCode(code);
    }
}
