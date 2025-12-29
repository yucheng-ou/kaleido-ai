package com.xiaoo.kaleido.user.application.convertor;

import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import org.mapstruct.Mapper;

/**
 * 用户转换器
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Mapper(componentModel = "spring")
public interface UserConvertor {

    /**
     * 将用户聚合根转换为用户查询响应
     *
     * @param userAggregate 用户聚合根
     * @return 用户查询响应
     */
    UserInfoResponse toResponse(UserAggregate userAggregate);

}
