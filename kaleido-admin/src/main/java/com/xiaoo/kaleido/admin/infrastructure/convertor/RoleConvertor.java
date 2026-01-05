package com.xiaoo.kaleido.admin.infrastructure.convertor;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.RolePO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色转换器
 * 负责RoleAggregate与RolePO之间的转换
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper
public interface RoleConvertor {

    RoleConvertor INSTANCE = Mappers.getMapper(RoleConvertor.class);

    /**
     * 将RoleAggregate转换为RolePO
     */
    RolePO toPO(RoleAggregate aggregate);

    /**
     * 将RolePO转换为RoleAggregate
     */
    RoleAggregate toEntity(RolePO po);
}
