package com.xiaoo.kaleido.user.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserOperateStreamPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * UserOperateStream 实体与 UserOperateStreamPO 转换器
 * 
 * 用户操作流水基础设施层转换器，负责用户操作流水领域实体与持久化对象之间的转换
 * 使用MapStruct实现，编译时生成转换代码，性能高效
 * 
 * @author ouyucheng
 * @date 2025/12/26
 */
@Mapper
public interface UserOperateStreamConvertor {

    /**
     * 转换器实例
     * MapStruct生成的单例实例，用于执行转换操作
     */
    UserOperateStreamConvertor INSTANCE = Mappers.getMapper(UserOperateStreamConvertor.class);

    /**
     * 将 UserOperateStream 实体转换为 UserOperateStreamPO
     * 
     * 将领域层的用户操作流水实体转换为基础设施层的持久化对象
     * 
     * @param stream 用户操作流水实体，不能为空
     * @return 用户操作流水持久化对象
     * @throws IllegalArgumentException 当用户操作流水实体为空时抛出
     */
    UserOperateStreamPO toPO(UserOperateStream stream);

    /**
     * 将 UserOperateStreamPO 转换为 UserOperateStream 实体
     * 
     * 将基础设施层的持久化对象转换为领域层的用户操作流水实体
     * 
     * @param po 用户操作流水持久化对象，不能为空
     * @return 用户操作流水实体
     * @throws IllegalArgumentException 当持久化对象为空时抛出
     */
    UserOperateStream toEntity(UserOperateStreamPO po);



}
