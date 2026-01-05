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
    @Mapping(source = "permission.code", target = "code")
    @Mapping(source = "permission.name", target = "name")
    @Mapping(source = "permission.type", target = "type")
    @Mapping(source = "permission.parentId", target = "parentId")
    @Mapping(source = "permission.sort", target = "sort")
    @Mapping(source = "permission.icon", target = "icon")
    @Mapping(source = "permission.path", target = "path")
    @Mapping(source = "permission.component", target = "component")
    @Mapping(source = "permission.isHidden", target = "isHidden")
    @Mapping(source = "permission.createdAt", target = "createdAt")
    @Mapping(source = "permission.updatedAt", target = "updatedAt")
    PermissionInfoResponse toResponse(PermissionAggregate aggregate);
    
    /**
     * PermissionAggregate 转换为树形 PermissionInfoResponse
     */
    @Mapping(source = "id", target = "permissionId")
    @Mapping(source = "permission.code", target = "code")
    @Mapping(source = "permission.name", target = "name")
    @Mapping(source = "permission.type", target = "type")
    @Mapping(source = "permission.parentId", target = "parentId")
    @Mapping(source = "permission.sort", target = "sort")
    @Mapping(source = "permission.icon", target = "icon")
    @Mapping(source = "permission.path", target = "path")
    @Mapping(source = "permission.component", target = "component")
    @Mapping(source = "permission.isHidden", target = "isHidden")
    @Mapping(source = "permission.createdAt", target = "createdAt")
    @Mapping(source = "permission.updatedAt", target = "updatedAt")
    @Mapping(source = "children", target = "children")
    PermissionInfoResponse toTreeResponse(PermissionAggregate aggregate);
    
    /**
     * PermissionAggregate 列表转换为 PermissionInfoResponse 列表
     */
    List<PermissionInfoResponse> toResponseList(List<PermissionAggregate> aggregateList);
    
    /**
     * PermissionAggregate 列表转换为树形 PermissionInfoResponse 列表
     */
    List<PermissionInfoResponse> toTreeResponseList(List<PermissionAggregate> aggregateList);
}
