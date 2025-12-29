package com.xiaoo.kaleido.notice.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import com.xiaoo.kaleido.notice.infrastructure.dao.po.NoticeTemplatePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * NoticeTemplateAggregate 与 NoticeTemplatePO 转换器
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Mapper
public interface NoticeTemplateConvertor {

    NoticeTemplateConvertor INSTANCE = Mappers.getMapper(NoticeTemplateConvertor.class);

    /**
     * 将 NoticeTemplateAggregate 转换为 NoticeTemplatePO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "code", target = "templateCode")
    @Mapping(source = "name", target = "templateName")
    @Mapping(source = "content", target = "templateContent")
    @Mapping(source = "status", target = "status")
    NoticeTemplatePO toPO(NoticeTemplateAggregate aggregate);

    /**
     * 将 NoticeTemplatePO 转换为 NoticeTemplateAggregate
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "templateCode", target = "code")
    @Mapping(source = "templateName", target = "name")
    @Mapping(source = "templateContent", target = "content")
    @Mapping(source = "status", target = "status")
    NoticeTemplateAggregate toAggregate(NoticeTemplatePO po);
}
