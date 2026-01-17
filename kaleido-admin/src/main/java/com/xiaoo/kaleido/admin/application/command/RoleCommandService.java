package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.admin.user.command.*;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.IRoleDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色命令服务（应用层）
 * 负责角色相关的写操作编排
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleCommandService {

    private final IRoleRepository roleRepository;
    private final IRoleDomainService roleDomainService;

    public String createRole(AddRoleCommand command) {
        // 1. 调用领域服务创建角色
        RoleAggregate roleAggregate = roleDomainService.createRole(
                command.getCode(),
                command.getName(),
                command.getDescription()
        );

        // 2. 保存角色
        roleRepository.save(roleAggregate);

        log.info("角色创建成功，角色ID: {}, 角色编码: {}",
                roleAggregate.getId(), command.getCode());
        return roleAggregate.getId();
    }

    public void updateRole(String roleId, UpdateRoleCommand command) {
        // 1. 调用领域服务更新角色信息
        RoleAggregate roleAggregate = roleDomainService.updateRole(
                roleId,
                command.getName(),
                command.getDescription()
        );

        // 2. 保存角色
        roleRepository.save(roleAggregate);

        log.info("角色信息更新成功，角色ID: {}", roleId);
    }

    public void deleteRole(String roleId) {
        // 1. 调用领域服务获取要删除的角色（验证角色是否存在）
        RoleAggregate roleAggregate = roleDomainService.deleteRole(roleId);

        // 2. 调用仓储层删除角色
        roleRepository.deleteById(roleAggregate.getId());

        log.info("角色删除成功，角色ID: {}", roleId);
    }

    public void assignPermissions(String roleId, AssignPermissionsToRoleCommand command) {
        // 1. 调用领域服务分配权限
        RoleAggregate roleAggregate = roleDomainService.assignPermissions(
                roleId,
                command.getPermissionIds()
        );

        // 2. 保存角色权限关系
        roleRepository.saveRolePermissions(roleAggregate.getId(), roleAggregate.getPermissionIds());

        log.info("权限分配成功，角色ID: {}, 权限数量: {}",
                roleId, command.getPermissionIds().size());
    }

    public boolean isCodeAvailable(String code) {
        return roleDomainService.isCodeAvailable(code);
    }

    public boolean isValidRole(String roleId) {
        return roleDomainService.isValidRole(roleId);
    }

    public boolean hasPermission(String roleId, String permissionId) {
        return roleDomainService.hasPermission(roleId, permissionId);
    }
}
