package com.xiaoo.kaleido.admin.application.convertor;

import com.xiaoo.kaleido.api.admin.user.response.AdminUserInfoResponse;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminUserAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * 管理员应用层转换器
 * 负责领域对象到Response对象的转换
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper(componentModel = "spring")
@Component
public interface AdminUserConvertor {
    
    /**
     * AdminUserAggregate 转换为 AdminUserInfoResponse
     */
    @Mapping(source = "id", target = "adminUserId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "roleIds", target = "roleIds")
    AdminUserInfoResponse toResponse(AdminUserAggregate aggregate);
}
