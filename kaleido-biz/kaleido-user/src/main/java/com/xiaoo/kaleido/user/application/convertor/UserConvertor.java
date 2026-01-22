package com.xiaoo.kaleido.user.application.convertor;

import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.api.user.enums.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * 用户转换器
 * <p>
 * 用户应用层转换器，负责用户领域对象与应用层DTO之间的转换
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Mapper(componentModel = "spring")
public interface UserConvertor {

    /**
     * 将用户聚合根转换为用户查询响应

     * 将领域层的用户聚合根转换为应用层的用户查询响应DTO
     *
     * @param userAggregate 用户聚合根，不能为空
     * @return 用户查询响应，包含用户的基本信息和状态
     */
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "user.status", target = "status", qualifiedByName = "statusToString")
    @Mapping(source = "user.lastLoginTime", target = "lastLoginTime")
    @Mapping(source = "user.avatar", target = "avatar")
    UserInfoResponse toResponse(UserAggregate userAggregate);

    /**
     * 将 UserStatus 枚举转换为字符串
     *
     * @param status 用户状态枚举
     * @return 状态描述字符串
     */
    @Named("statusToString")
    default String statusToString(UserStatus status) {
        return status != null ? status.name() : null;
    }

}
