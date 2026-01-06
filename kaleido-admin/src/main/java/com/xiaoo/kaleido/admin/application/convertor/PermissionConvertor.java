package com.xiaoo.kaleido.admin.application.convertor;

import com.xiaoo.kaleido.api.admin.auth.response.PermissionInfoResponse;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限应用层转换器
 * 负责领域对象到Response对象的转换
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper(componentModel = "spring")
@Component
public interface PermissionConvertor {
    
    /**
     * PermissionAggregate 转换为 PermissionInfoResponse
     */
    @Mapping(source = "id", target = "permissionId")
    PermissionInfoResponse toResponse(PermissionAggregate aggregate);
    
    /**
     * PermissionAggregate 列表转换为 PermissionInfoResponse 列表
     */
    List<PermissionInfoResponse> toResponseList(List<PermissionAggregate> aggregateList);
}
