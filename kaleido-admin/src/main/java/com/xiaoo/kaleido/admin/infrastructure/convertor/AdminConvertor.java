package com.xiaoo.kaleido.admin.infrastructure.convertor;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminPO;
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
public interface AdminConvertor {

    AdminConvertor INSTANCE = Mappers.getMapper(AdminConvertor.class);

    /**
     * 将AdminUserAggregate转换为AdminUserPO
     */
    AdminPO toPO(AdminAggregate aggregate);

    /**
     * 将AdminUserPO转换为AdminUserAggregate
     */
    AdminAggregate toEntity(AdminPO po);
}
