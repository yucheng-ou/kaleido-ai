package com.xiaoo.kaleido.user.domain.model.convertor;

import com.xiaoo.kaleido.api.user.response.UserOperateVo;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author ouyucheng
 * @date 2025/11/19
 * @description
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserConvertor {


    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    UserOperateVo mapToVo(User user);
}
