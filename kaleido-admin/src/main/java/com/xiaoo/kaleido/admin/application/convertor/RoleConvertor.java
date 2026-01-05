package com.xiaoo.kaleido.admin.application.convertor;

import com.xiaoo.kaleido.api.admin.auth.response.RoleInfoResponse;
import com.xiaoo.kaleido.api.admin.auth.response.RoleTreeResponse;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色应用层转换器
 * 负责领域对象到Response对象的转换
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper(componentModel = "spring")
@Component
public interface RoleConvertor {
    
    /**
     * RoleAggregate 转换为 RoleInfoResponse
     */
    @Mapping(source = "id", target = "roleId")
    @Mapping(source = "role.code", target = "code")
    @Mapping(source = "role.name", target = "name")
    @Mapping(source = "role.description", target = "description")
    @Mapping(source = "role.isSystem", target = "isSystem")
    @Mapping(source = "role.enabled", target = "enabled")
    @Mapping(source = "role.createdAt", target = "createdAt")
    @Mapping(source = "role.updatedAt", target = "updatedAt")
    @Mapping(source = "permissionIds", target = "permissionIds")
    @Mapping(source = "permissions", target = "permissions")
    RoleInfoResponse toResponse(RoleAggregate aggregate);
    
    /**
     * RoleAggregate 转换为 RoleTreeResponse
     */
    @Mapping(source = "id", target = "roleId")
    @Mapping(source = "role.code", target = "code")
    @Mapping(source = "role.name", target = "name")
    @Mapping(source = "role.description", target = "description")
    @Mapping(source = "role.isSystem", target = "isSystem")
    @Mapping(source = "role.enabled", target = "enabled")
    @Mapping(source = "children", target = "children")
    RoleTreeResponse toTreeResponse(RoleAggregate aggregate);
    
    /**
     * RoleAggregate 列表转换为 RoleInfoResponse 列表
     */
    List<RoleInfoResponse> toResponseList(List<RoleAggregate> aggregateList);
    
    /**
     * RoleAggregate 列表转换为 RoleTreeResponse 列表
     */
    List<RoleTreeResponse> toTreeResponseList(List<RoleAggregate> aggregateList);
}
