package com.xiaoo.kaleido.user.domain.model.convertor;

import com.xiaoo.kaleido.api.user.response.UserInfoVO;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * 用户对象转换器
 * 使用MapStruct实现对象之间的转换，提供高性能的类型安全映射
 *
 * @author ouyucheng
 * @date 2025/11/19
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    /**
     * 将User实体转换为UserInfoVO
     * 基础字段映射由MapStruct自动处理
     *
     * @param user 用户实体
     * @param inviterMap 邀请人映射表，用于设置邀请人昵称
     * @return UserInfoVO对象
     */
    @Mapping(target = "inviterNickName", ignore = true)
    UserInfoVO toUserInfoVO(User user, @Context Map<Long, User> inviterMap);

    /**
     * 将User实体列表转换为UserInfoVO列表
     *
     * @param users 用户实体列表
     * @param inviterMap 邀请人映射表，用于设置邀请人昵称
     * @return UserInfoVO列表
     */
    List<UserInfoVO> toUserInfoVOList(List<User> users, @Context Map<Long, User> inviterMap);
}
