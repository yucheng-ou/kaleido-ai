package com.xiaoo.kaleido.admin.infrastructure.convertor;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminUserAggregate;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminUserPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 管理员转换器
 * 负责AdminUserAggregate与AdminUserPO之间的转换
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Mapper
public interface AdminUserConvertor {

    AdminUserConvertor INSTANCE = Mappers.getMapper(AdminUserConvertor.class);

    /**
     * 将AdminUserAggregate转换为AdminUserPO
     */
    AdminUserPO toPO(AdminUserAggregate aggregate);

    /**
     * 将AdminUserPO转换为AdminUserAggregate
     */
    AdminUserAggregate toEntity(AdminUserPO po);
}
