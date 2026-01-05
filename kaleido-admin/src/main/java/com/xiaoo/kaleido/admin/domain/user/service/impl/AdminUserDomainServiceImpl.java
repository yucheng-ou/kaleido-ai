package com.xiaoo.kaleido.admin.domain.user.service.impl;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminUserRepository;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminUserAggregate;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.AdminUserDomainService;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 管理员领域服务实现
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserDomainServiceImpl implements AdminUserDomainService {

    private final IAdminUserRepository adminUserRepository;
    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;

    @Override
    public AdminUserAggregate createAdminUser(String username, String passwordHash,
                                              String realName, String mobile) {
        // 验证管理员账号唯一性
        if (adminUserRepository.existsByUsername(username)) {
            throw AdminException.of(AdminErrorCode.ADMIN_USERNAME_EXIST.getCode(), AdminErrorCode.ADMIN_USERNAME_EXIST.getMessage());
        }

        // 验证手机号唯一性
        if (mobile != null && !mobile.trim().isEmpty() && adminUserRepository.existsByMobile(mobile)) {
            throw AdminException.of(AdminErrorCode.ADMIN_MOBILE_EXIST.getCode(), AdminErrorCode.ADMIN_MOBILE_EXIST.getMessage());
        }

        // 创建管理员
        AdminUserAggregate adminUser = AdminUserAggregate.create(username, passwordHash, realName, mobile);

        log.info("管理员领域服务创建管理员，管理员ID: {}, 管理员账号: {}, 真实姓名: {}",
                adminUser.getId(), username, realName);

        return adminUser;
    }

    @Override
    public AdminUserAggregate updateAdminUser(String adminUserId, String realName,
                                              String mobile) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 验证手机号唯一性（如果手机号有变化）
        if (mobile != null && !mobile.equals(adminUser.getMobile()) && adminUserRepository.existsByMobile(mobile)) {
            throw AdminException.of(AdminErrorCode.ADMIN_MOBILE_EXIST.getCode(), AdminErrorCode.ADMIN_MOBILE_EXIST.getMessage());
        }

        // 更新管理员信息
        adminUser.updateInfo(realName, mobile);

        log.info("管理员领域服务更新管理员，管理员ID: {}, 真实姓名: {}", adminUserId, realName);
        return adminUser;
    }

    @Override
    public AdminUserAggregate updatePassword(String adminUserId, String passwordHash) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 更新密码
        adminUser.updatePassword(passwordHash);

        log.info("管理员领域服务更新密码，管理员ID: {}", adminUserId);
        return adminUser;
    }

    @Override
    public AdminUserAggregate enableAdminUser(String adminUserId) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 启用管理员
        adminUser.enable();

        log.info("管理员领域服务启用管理员，管理员ID: {}", adminUserId);
        return adminUser;
    }

    @Override
    public AdminUserAggregate freezeAdminUser(String adminUserId) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 冻结管理员
        adminUser.freeze();

        log.info("管理员领域服务冻结管理员，管理员ID: {}", adminUserId);
        return adminUser;
    }

    @Override
    public void deleteAdminUser(String adminUserId) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 删除管理员
        adminUserRepository.deleteById(adminUserId);

        log.info("管理员领域服务删除管理员，管理员ID: {}", adminUserId);
    }

    @Override
    public AdminUserAggregate updateLastLoginTime(String adminUserId) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 更新最后登录时间
        adminUser.updateLastLoginTime();

        log.info("管理员领域服务更新最后登录时间，管理员ID: {}", adminUserId);
        return adminUser;
    }

    @Override
    public AdminUserAggregate assignRoles(String adminUserId, List<String> roleIds) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 验证角色是否存在且启用
        for (String roleId : roleIds) {
            RoleAggregate role = roleRepository.findById(roleId)
                    .orElseThrow(() -> AdminException.of(AdminErrorCode.ROLE_NOT_EXIST, "角色不存在: " + roleId));
            if (!role.isEnabled()) {
                throw AdminException.of(AdminErrorCode.ROLE_DISABLED, "角色已禁用: " + roleId);
            }
        }

        // 分配角色
        adminUser.addRoles(roleIds);

        log.info("管理员领域服务分配角色，管理员ID: {}, 角色数量: {}", adminUserId, roleIds.size());
        return adminUser;
    }

    @Override
    public AdminUserAggregate removeRoles(String adminUserId, List<String> roleIds) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 移除角色
        adminUser.removeRoles(roleIds);

        log.info("管理员领域服务移除角色，管理员ID: {}, 角色数量: {}", adminUserId, roleIds.size());
        return adminUser;
    }

    @Override
    public AdminUserAggregate clearRoles(String adminUserId) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 清空角色
        adminUser.clearRoles();

        log.info("管理员领域服务清空角色，管理员ID: {}", adminUserId);
        return adminUser;
    }

    @Override
    public AdminUserAggregate findByIdOrThrow(String adminUserId) {
        return adminUserRepository.findById(adminUserId)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.ADMIN_USER_NOT_EXIST.getCode(), AdminErrorCode.ADMIN_USER_NOT_EXIST.getMessage()));
    }

    @Override
    public AdminUserAggregate findByUsernameOrThrow(String username) {
        return adminUserRepository.findByUsername(username)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.ADMIN_USER_NOT_EXIST.getCode(), AdminErrorCode.ADMIN_USER_NOT_EXIST.getMessage()));
    }

    @Override
    public AdminUserAggregate findByMobile(String mobile) {
        return adminUserRepository.findByMobile(mobile)
                .orElse(null);
    }

    @Override
    public List<AdminUserAggregate> findNormalAdminUsers() {
        return adminUserRepository.findByStatus(com.xiaoo.kaleido.admin.domain.user.constant.AdminUserStatus.NORMAL);
    }

    @Override
    public List<AdminUserAggregate> findByRoleId(String roleId) {
        return adminUserRepository.findByRoleId(roleId);
    }

    @Override
    public boolean existsByUsername(String username) {
        return adminUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByMobile(String mobile) {
        return adminUserRepository.existsByMobile(mobile);
    }

    @Override
    public boolean isValidAdminUser(String adminUserId) {
        try {
            AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);
            return adminUser.isAvailable();
        } catch (AdminException e) {
            return false;
        }
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return !adminUserRepository.existsByUsername(username);
    }

    @Override
    public void verifyPassword(String adminUserId, String passwordHash) {
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);
         adminUser.verifyPassword(passwordHash);
    }

    @Override
    public boolean hasRole(String adminUserId, String roleId) {
        try {
            AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);
            return adminUser.hasRole(roleId);
        } catch (AdminException e) {
            return false;
        }
    }

    @Override
    public List<String> getPermissionsByAdminUserId(String adminUserId) {
        // 获取管理员
        AdminUserAggregate adminUser = findByIdOrThrow(adminUserId);

        // 获取管理员的所有角色
        List<String> roleIds = adminUser.getRoleIds();
        if (roleIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取所有角色
        List<RoleAggregate> roles = roleRepository.findAllById(roleIds);

        // 收集所有权限ID
        Set<String> permissionIds = new HashSet<>();
        for (RoleAggregate role : roles) {
            if (role.isEnabled()) {
                permissionIds.addAll(role.getPermissionIds());
            }
        }

        return new ArrayList<>(permissionIds);
    }

    @Override
    public boolean hasPermission(String adminUserId, String permissionId) {
        List<String> permissions = getPermissionsByAdminUserId(adminUserId);
        return permissions.contains(permissionId);
    }
}
