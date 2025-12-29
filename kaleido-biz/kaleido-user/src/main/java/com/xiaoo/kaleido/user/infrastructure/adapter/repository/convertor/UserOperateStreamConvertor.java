package com.xiaoo.kaleido.user.infrastructure.adapter.repository.convertor;

import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserOperateStreamPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * UserOperateStream 实体与 UserOperateStreamPO 转换器
 *
 * @author ouyucheng
 * @date 2025/12/26
 */
@Mapper
public interface UserOperateStreamConvertor {

    UserOperateStreamConvertor INSTANCE = Mappers.getMapper(UserOperateStreamConvertor.class);

    /**
     * 将 UserOperateStream 实体转换为 UserOperateStreamPO
     */
    UserOperateStreamPO toPO(UserOperateStream stream);

    /**
     * 将 UserOperateStreamPO 转换为 UserOperateStream 实体
     */
    UserOperateStream toEntity(UserOperateStreamPO po);



}
