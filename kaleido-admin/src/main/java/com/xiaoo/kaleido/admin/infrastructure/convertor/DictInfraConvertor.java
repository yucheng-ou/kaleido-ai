package com.xiaoo.kaleido.admin.infrastructure.convertor;

import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.DictPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * 字典基础设施层转换器
 * 负责领域对象与持久化对象之间的转换
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Mapper
public interface DictInfraConvertor {

    DictInfraConvertor INSTANCE = Mappers.getMapper(DictInfraConvertor.class);

    /**
     * DictAggregate 转换为 DictPO
     */
    DictPO toPO(DictAggregate aggregate);
    
    /**
     * DictPO 转换为 DictAggregate
     */
    DictAggregate toEntity(DictPO po);
}
