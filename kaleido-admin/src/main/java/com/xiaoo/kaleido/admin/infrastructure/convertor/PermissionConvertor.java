package com.xiaoo.kaleido.admin.infrastructure.convertor;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.PermissionPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 权限转换器
 * 负责PermissionAggregate与PermissionPO之间的转换
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper
public interface PermissionConvertor {

    PermissionConvertor INSTANCE = Mappers.getMapper(PermissionConvertor.class);

    /**
     * 将PermissionAggregate转换为PermissionPO
     */
    PermissionPO toPO(PermissionAggregate aggregate);

    /**
     * 将PermissionPO转换为PermissionAggregate
     */
    PermissionAggregate toEntity(PermissionPO po);
}
