package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.LocationRecordAggregate;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.LocationRecordPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 位置记录基础设施层转换器
 * <p>
 * 负责LocationRecordAggregate和LocationRecordPO之间的转换
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Mapper
public interface LocationRecordInfraConvertor {

    LocationRecordInfraConvertor INSTANCE = Mappers.getMapper(LocationRecordInfraConvertor.class);

    /**
     * LocationRecordAggregate转换为LocationRecordPO
     *
     * @param aggregate 位置记录聚合根
     * @return 位置记录持久化对象
     */
    @Mapping(source = "isCurrent", target = "isCurrent", qualifiedByName = "booleanToInteger")
    LocationRecordPO toPO(LocationRecordAggregate aggregate);

    /**
     * LocationRecordPO转换为LocationRecordAggregate
     *
     * @param po 位置记录持久化对象
     * @return 位置记录聚合根
     */
    @Mapping(source = "isCurrent", target = "isCurrent", qualifiedByName = "integerToBoolean")
    LocationRecordAggregate toAggregate(LocationRecordPO po);

    /**
     * LocationRecordPO列表转换为LocationRecordAggregate列表
     *
     * @param poList 位置记录持久化对象列表
     * @return 位置记录聚合根列表
     */
    List<LocationRecordAggregate> toAggregateList(List<LocationRecordPO> poList);

    /**
     * 将Boolean转换为Integer
     * 用于isCurrent字段的转换：true -> 1, false -> 0
     *
     * @param value Boolean值
     * @return Integer值
     */
    @Named("booleanToInteger")
    default Integer booleanToInteger(Boolean value) {
        if (value == null) {
            return null;
        }
        return value ? 1 : 0;
    }

    /**
     * 将Integer转换为Boolean
     * 用于isCurrent字段的转换：1 -> true, 0 -> false, 其他值 -> false
     *
     * @param value Integer值
     * @return Boolean值
     */
    @Named("integerToBoolean")
    default Boolean integerToBoolean(Integer value) {
        if (value == null) {
            return null;
        }
        return value == 1;
    }
}
