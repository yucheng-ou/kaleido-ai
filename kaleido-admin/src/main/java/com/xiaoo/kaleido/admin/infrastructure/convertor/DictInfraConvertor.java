package com.xiaoo.kaleido.admin.infrastructure.convertor;

import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.DictPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * 字典基础设施层转换器
 * 负责领域对象与持久化对象之间的转换
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Mapper(componentModel = "spring")
@Component
public interface DictInfraConvertor {
    
    /**
     * DictAggregate 转换为 DictPO
     */
    @Mapping(target = "id", source = "id", qualifiedByName = "stringToLong")
    @Mapping(target = "deleted", constant = "0")
    DictPO toPO(DictAggregate aggregate);
    
    /**
     * DictPO 转换为 DictAggregate
     */
    @Mapping(target = "id", source = "id", qualifiedByName = "longToString")
    DictAggregate toEntity(DictPO po);
    
    @Named("stringToLong")
    default Long stringToLong(String id) {
        if (id == null) {
            return null;
        }
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    @Named("longToString")
    default String longToString(Long id) {
        return id != null ? id.toString() : null;
    }
}
