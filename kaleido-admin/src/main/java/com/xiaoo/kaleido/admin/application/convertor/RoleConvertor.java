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
    RoleInfoResponse toResponse(RoleAggregate aggregate);
    
    /**
     * RoleAggregate 转换为 RoleTreeResponse
     */
    @Mapping(source = "id", target = "roleId")
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
