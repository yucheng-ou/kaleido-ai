package com.xiaoo.kaleido.tag.application.convertor;

import com.xiaoo.kaleido.api.tag.response.TagInfoResponse;
import com.xiaoo.kaleido.tag.domain.model.aggregate.TagAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 标签转换器
 * <p>
 * 标签应用层转换器，负责标签领域对象与应用层DTO之间的转换
 * 使用MapStruct自动生成实现代码
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Mapper(componentModel = "spring")
public interface TagConvertor {

    /**
     * 将标签聚合根转换为标签信息响应
     * <p>
     * 将领域层的标签聚合根转换为应用层的标签信息响应DTO
     *
     * @param tagAggregate 标签聚合根，不能为空
     * @return 标签信息响应，包含标签的基本信息和状态
     */
    @Mapping(source = "id", target = "tagId")
    TagInfoResponse toResponse(TagAggregate tagAggregate);
}
