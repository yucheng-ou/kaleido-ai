package com.xiaoo.kaleido.user.query.convertor;

import com.xiaoo.kaleido.user.domain.constant.UserStatus;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import com.xiaoo.kaleido.user.query.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户转换器
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    /**
     * 将UserPO转换为UserDTO
     */
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "nickName", target = "nickName")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "inviteCode", target = "inviteCode")
    @Mapping(source = "inviterId", target = "inviterId")
    @Mapping(source = "lastLoginTime", target = "lastLoginTime")
    @Mapping(source = "avatarUrl", target = "avatarUrl")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    UserDTO toDTO(UserPO userPO);

    /**
     * 将UserPO列表转换为UserDTO列表
     */
    List<UserDTO> toDTOList(List<UserPO> userPOList);

    /**
     * 将状态码转换为UserStatus枚举
     */
    default UserStatus toUserStatus(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        return UserStatus.fromCode(statusCode);
    }
}
