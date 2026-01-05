package com.xiaoo.kaleido.user.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * UserPO 与 UserInfoVO 转换器
 *
 * @author ouyucheng
 * @date 2025/12/26
 */
@Mapper
public interface UserInfoVOConvertor {

    UserInfoVOConvertor INSTANCE = Mappers.getMapper(UserInfoVOConvertor.class);

    /**
     * 将 UserPO 转换为 UserInfoVO
     */
    UserInfoVO toVO(UserPO po);

}
