package com.xiaoo.kaleido.admin.infrastructure.convertor;

import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.PermissionPO;
import com.xiaoo.kaleido.api.admin.user.enums.PermissionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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
    @Mapping(target = "type", source = "type", qualifiedByName = "permissionTypeToString")
    PermissionPO toPO(PermissionAggregate aggregate);

    /**
     * 将PermissionPO转换为PermissionAggregate
     */
    @Mapping(target = "type", source = "type", qualifiedByName = "stringToPermissionType")
    PermissionAggregate toEntity(PermissionPO po);

    /**
     * 将PermissionType转换为String
     */
    @Named("permissionTypeToString")
    default String permissionTypeToString(PermissionType type) {
        return type != null ? type.name() : null;
    }

    /**
     * 将String转换为PermissionType
     */
    @Named("stringToPermissionType")
    default PermissionType stringToPermissionType(String code) {
        if (code == null) {
            return null;
        }
        try {
            // 直接使用valueOf方法（枚举名称）
            return PermissionType.valueOf(code);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
