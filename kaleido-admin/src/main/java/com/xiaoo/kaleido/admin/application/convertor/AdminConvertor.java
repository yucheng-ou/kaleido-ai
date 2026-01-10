package com.xiaoo.kaleido.admin.application.convertor;

import com.xiaoo.kaleido.api.admin.user.response.AdminInfoResponse;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;
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
public interface AdminConvertor {
    
    /**
     * AdminAggregate 转换为 AdminInfoResponse
     */
    @Mapping(source = "id", target = "adminId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "roleIds", target = "roleIds")
    AdminInfoResponse toResponse(AdminAggregate aggregate);
}
