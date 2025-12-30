package com.xiaoo.kaleido.notice.application.convertor;

import com.xiaoo.kaleido.api.notice.response.NoticeResponse;
import com.xiaoo.kaleido.api.notice.response.NoticeTemplateResponse;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * 应用层转换器
 * 负责领域对象到Response对象的转换
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Mapper(componentModel = "spring")
@Component
public interface NoticeAppConvertor {
    
    /**
     * NoticeAggregate 转换为 NoticeResponse
     */
    NoticeResponse toResponse(NoticeAggregate aggregate);
    
    /**
     * NoticeTemplateAggregate 转换为 NoticeTemplateResponse
     */
    NoticeTemplateResponse toTemplateResponse(NoticeTemplateAggregate aggregate);
}
