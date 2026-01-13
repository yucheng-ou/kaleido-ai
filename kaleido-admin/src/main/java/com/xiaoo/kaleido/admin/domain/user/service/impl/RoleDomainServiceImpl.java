package com.xiaoo.kaleido.admin.domain.user.service.impl;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.IRoleDomainService;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
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
        RoleAggregate role = RoleAggregate.create(code, name, description);

        log.info("角色领域服务创建角色，角色ID: {}, 角色编码: {}, 角色名称: {}",
                role.getId(), code, name);

        return role;
    }

    @Override
    public RoleAggregate updateRole(String roleId, String name, String description) {
        // 1. 获取角色
        RoleAggregate role = findByIdOrThrow(roleId);

        // 2. 更新角色信息
        role.updateInfo(name, description);

        log.info("角色领域服务更新角色，角色ID: {}, 角色名称: {}", roleId, name);
        return role;
    }

    @Override
    public RoleAggregate enableRole(String roleId) {
        // 1. 获取角色
        RoleAggregate role = findByIdOrThrow(roleId);

        // 2. 启用角色
        role.enable();

        log.info("角色领域服务启用角色，角色ID: {}", roleId);
        return role;
    }

    @Override
    public RoleAggregate disableRole(String roleId) {
        // 1. 获取角色
        RoleAggregate role = findByIdOrThrow(roleId);

        // 2. 禁用角色
        role.disable();

        log.info("角色领域服务禁用角色，角色ID: {}", roleId);
        return role;
    }

    @Override
    public void deleteRole(String roleId) {
        // 1. 获取角色
        RoleAggregate role = findByIdOrThrow(roleId);

        // 2. 删除角色
        roleRepository.deleteById(roleId);

        log.info("角色领域服务删除角色，角色ID: {}", roleId);
    }

    @Override
    public RoleAggregate assignPermissions(String roleId, List<String> permissionIds) {
        // 1. 获取角色
        RoleAggregate role = findByIdOrThrow(roleId);

        // 2. 验证权限是否存在
        for (String permissionId : permissionIds) {
            PermissionAggregate permission = permissionRepository.findById(permissionId)
                    .orElseThrow(() -> AdminException.of(AdminErrorCode.PERMISSION_NOT_EXIST, "权限不存在: " + permissionId));
        }

        // 3. 分配权限
        role.addPermissions(permissionIds);

        log.info("角色领域服务分配权限，角色ID: {}, 权限数量: {}", roleId, permissionIds.size());
        return role;
    }

    @Override
    public RoleAggregate findByIdOrThrow(String roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.ROLE_NOT_EXIST.getCode(), AdminErrorCode.ROLE_NOT_EXIST.getMessage()));
    }

    @Override
    public RoleAggregate findByCodeOrThrow(String code) {
        return roleRepository.findByCode(code)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.ROLE_NOT_EXIST.getCode(), AdminErrorCode.ROLE_NOT_EXIST.getMessage()));
    }

    @Override
    public List<RoleAggregate> findEnabledRoles() {
        return roleRepository.findByStatus(DataStatusEnum.ENABLE);
    }

    @Override
    public boolean existsByCode(String code) {
        return roleRepository.existsByCode(code);
    }

    @Override
    public boolean isValidRole(String roleId) {
        try {
            // 1. 获取角色
            RoleAggregate role = findByIdOrThrow(roleId);
            
            // 2. 检查角色是否启用
            return role.isEnabled();
        } catch (AdminException e) {
            return false;
        }
    }

    @Override
    public boolean isCodeAvailable(String code) {
        return !roleRepository.existsByCode(code);
    }

    @Override
    public boolean hasPermission(String roleId, String permissionId) {
        try {
            // 1. 获取角色
            RoleAggregate role = findByIdOrThrow(roleId);
            
            // 2. 检查角色是否拥有指定权限
            return role.hasPermission(permissionId);
        } catch (AdminException e) {
            return false;
        }
    }
}
