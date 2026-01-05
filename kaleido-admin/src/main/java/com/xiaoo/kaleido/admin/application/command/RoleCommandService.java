package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.admin.auth.command.*;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.RoleDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final RoleDomainService roleDomainService;

    /**
     * 创建角色
     *
     * @param command 创建角色命令
     * @return 角色ID
     */
    @Transactional
    public String createRole(AddRoleCommand command) {
        // 调用领域服务创建角色
        RoleAggregate roleAggregate = roleDomainService.createRole(
                command.getCode(),
                command.getName(),
                command.getDescription(),
                command.getIsSystem()
        );

        // 保存角色
        roleRepository.save(roleAggregate);

        log.info("角色创建成功，角色ID: {}, 角色编码: {}", 
                roleAggregate.getId(), command.getCode());
        return roleAggregate.getId();
    }

    /**
     * 更新角色信息
     *
     * @param command 更新角色信息命令
     */
    @Transactional
    public void updateRole(UpdateRoleCommand command) {
        // 调用领域服务更新角色信息
        RoleAggregate roleAggregate = roleDomainService.updateRole(
                command.getRoleId(),
                command.getName(),
                command.getDescription()
        );

        // 保存角色
        roleRepository.save(roleAggregate);

        log.info("角色信息更新成功，角色ID: {}", command.getRoleId());
    }

    /**
     * 启用角色
     *
     * @param command 启用角色命令
     */
    @Transactional
    public void enableRole(EnableRoleCommand command) {
        // 调用领域服务启用角色
        RoleAggregate roleAggregate = roleDomainService.enableRole(command.getRoleId());

        // 保存角色
        roleRepository.save(roleAggregate);

        log.info("角色启用成功，角色ID: {}", command.getRoleId());
    }

    /**
     * 禁用角色
     *
     * @param command 禁用角色命令
     */
    @Transactional
    public void disableRole(DisableRoleCommand command) {
        // 调用领域服务禁用角色
        RoleAggregate roleAggregate = roleDomainService.disableRole(command.getRoleId());

        // 保存角色
        roleRepository.save(roleAggregate);

        log.info("角色禁用成功，角色ID: {}", command.getRoleId());
    }

    /**
     * 删除角色
     *
     * @param command 删除角色命令
     */
    @Transactional
    public void deleteRole(DeleteRoleCommand command) {
        // 调用领域服务删除角色
        roleDomainService.deleteRole(command.getRoleId());

        log.info("角色删除成功，角色ID: {}", command.getRoleId());
    }

    /**
     * 分配权限给角色
     *
     * @param command 分配权限给角色命令
     */
    @Transactional
    public void assignPermissions(AssignPermissionsToRoleCommand command) {
        // 调用领域服务分配权限
        RoleAggregate roleAggregate = roleDomainService.assignPermissions(
                command.getRoleId(),
                command.getPermissionIds()
        );

        // 保存角色
        roleRepository.save(roleAggregate);

        log.info("权限分配成功，角色ID: {}, 权限数量: {}", 
                command.getRoleId(), command.getPermissionIds().size());
    }

    /**
     * 从角色移除权限
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     */
    @Transactional
    public void removePermissions(String roleId, List<String> permissionIds) {
        // 调用领域服务移除权限
        RoleAggregate roleAggregate = roleDomainService.removePermissions(roleId, permissionIds);

        // 保存角色
        roleRepository.save(roleAggregate);

        log.info("权限移除成功，角色ID: {}, 移除权限数量: {}", roleId, permissionIds.size());
    }

    /**
     * 清空角色权限
     *
     * @param roleId 角色ID
     */
    @Transactional
    public void clearPermissions(String roleId) {
        // 调用领域服务清空权限
        RoleAggregate roleAggregate = roleDomainService.clearPermissions(roleId);

        // 保存角色
        roleRepository.save(roleAggregate);

        log.info("角色权限清空成功，角色ID: {}", roleId);
    }

    /**
     * 检查角色编码是否可用
     *
     * @param code 角色编码
     * @return 是否可用
     */
    public boolean isCodeAvailable(String code) {
        return roleDomainService.isCodeAvailable(code);
    }

    /**
     * 检查角色是否存在且启用
     *
     * @param roleId 角色ID
     * @return 是否存在且启用
     */
    public boolean isValidRole(String roleId) {
        return roleDomainService.isValidRole(roleId);
    }

    /**
     * 验证角色是否拥有某个权限
     *
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 是否拥有
     */
    public boolean hasPermission(String roleId, String permissionId) {
        return roleDomainService.hasPermission(roleId, permissionId);
    }
}
