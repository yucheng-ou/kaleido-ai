package com.xiaoo.kaleido.user.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * User 实体与 UserPO 转换器
 *
 * @author ouyucheng
 * @date 2025/12/26
 */
@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    /**
     * 将 User 实体转换为 UserPO
     */
    UserPO toPO(User user);

    /**
     * 将 UserPO 转换为 User 实体
     */
    User toEntity(UserPO po);

}
