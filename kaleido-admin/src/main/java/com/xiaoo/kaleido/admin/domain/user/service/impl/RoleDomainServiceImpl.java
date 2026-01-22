package com.xiaoo.kaleido.admin.domain.user.service.impl;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.IRoleDomainService;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色领域服务实现
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleDomainServiceImpl implements IRoleDomainService {

    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;

    @Override
    public RoleAggregate createRole(String code, String name, String description) {
        // 1. 验证角色编码唯一性
        if (roleRepository.existsByCode(code)) {
            throw AdminException.of(AdminErrorCode.ROLE_CODE_EXIST.getCode(), AdminErrorCode.ROLE_CODE_EXIST.getMessage());
        }

        // 2. 创建角色
        return RoleAggregate.create(code, name, description);
    }

    @Override
    public RoleAggregate updateRole(String roleId, String name, String description) {
        // 1. 获取角色
        RoleAggregate role = findByIdOrThrow(roleId);

        // 2. 更新角色信息
        role.updateInfo(name, description);

        return role;
    }

    @Override
    public RoleAggregate assignPermissions(String roleId, List<String> permissionIds) {
        // 1. 获取角色
        RoleAggregate role = findByIdOrThrow(roleId);

        // 2. 验证权限是否存在
        for (String permissionId : permissionIds) {
            PermissionAggregate permission = permissionRepository.findById(permissionId);
            if (permission == null) {
                throw AdminException.of(AdminErrorCode.PERMISSION_NOT_EXIST, "权限不存在: " + permissionId);
            }
        }

        // 3. 分配权限
        role.addPermissions(permissionIds);

        return role;
    }

    @Override
    public RoleAggregate findByIdOrThrow(String roleId) {
        RoleAggregate role = roleRepository.findById(roleId);
        if (role == null) {
            throw AdminException.of(AdminErrorCode.ROLE_NOT_EXIST.getCode(), AdminErrorCode.ROLE_NOT_EXIST.getMessage());
        }
        return role;
    }

    @Override
    public RoleAggregate findByCodeOrThrow(String code) {
        RoleAggregate role = roleRepository.findByCode(code);
        if (role == null) {
            throw AdminException.of(AdminErrorCode.ROLE_NOT_EXIST.getCode(), AdminErrorCode.ROLE_NOT_EXIST.getMessage());
        }
        return role;
    }

    @Override
    public List<RoleAggregate> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public boolean existsByCode(String code) {
        return roleRepository.existsByCode(code);
    }
}
