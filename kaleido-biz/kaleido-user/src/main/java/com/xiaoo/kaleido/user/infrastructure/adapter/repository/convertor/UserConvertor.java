package com.xiaoo.kaleido.user.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * User 实体与 UserPO 转换器
 * 
 * 用户基础设施层转换器，负责用户领域实体与持久化对象之间的转换
 * 使用MapStruct实现，编译时生成转换代码，性能高效
 * 
 * @author ouyucheng
 * @date 2025/12/26
 */
@Mapper
public interface UserConvertor {

    /**
     * 转换器实例
     * MapStruct生成的单例实例，用于执行转换操作
     */
    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    /**
     * 将 User 实体转换为 UserPO
     * 
     * 将领域层的用户实体转换为基础设施层的持久化对象
     * 
     * @param user 用户实体，不能为空
     * @return 用户持久化对象
     * @throws IllegalArgumentException 当用户实体为空时抛出
     */
    UserPO toPO(User user);

    /**
     * 将 UserPO 转换为 User 实体
     * 
     * 将基础设施层的持久化对象转换为领域层的用户实体
     * 
     * @param po 用户持久化对象，不能为空
     * @return 用户实体
     * @throws IllegalArgumentException 当持久化对象为空时抛出
     */
    User toEntity(UserPO po);

}
