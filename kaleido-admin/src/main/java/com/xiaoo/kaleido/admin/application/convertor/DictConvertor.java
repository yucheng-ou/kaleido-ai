package com.xiaoo.kaleido.admin.application.convertor;

import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.admin.domain.dict.aggregate.DictAggregate;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * 字典应用层转换器
 * 负责领域对象到Response对象的转换
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Mapper(componentModel = "spring")
@Component
public interface DictConvertor {
    
    /**
     * DictAggregate 转换为 DictResponse
     */
    DictResponse toResponse(DictAggregate aggregate);
}
