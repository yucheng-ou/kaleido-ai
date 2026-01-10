package com.xiaoo.kaleido.admin.domain.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.admin.domain.user.constant.AdminStatus;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.AbstractAdminDomainService;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminRepository;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.IAdminDomainService;
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
public class AdminDomainServiceImpl extends AbstractAdminDomainService<AdminAggregate, String>
        implements IAdminDomainService {

    private final IAdminRepository adminUserRepository;
    private final IRoleRepository roleRepository;

    @Override
    public AdminAggregate createAdmin(String mobile) {
        // 验证手机号唯一性
        if (adminUserRepository.existsByMobile(mobile)) {
            throw AdminException.of(AdminErrorCode.ADMIN_MOBILE_EXIST.getCode(), AdminErrorCode.ADMIN_MOBILE_EXIST.getMessage());
        }

        // 创建管理员
        AdminAggregate adminUser = AdminAggregate.create(mobile);

        log.info("管理员领域服务创建管理员，管理员ID: {}, 手机号: {}",
                adminUser.getId(), adminUser.getMobile());

        return adminUser;
    }

    @Override
    public AdminAggregate updateAdmin(String adminId, String realName,
                                      String mobile) {
        // 获取管理员
        AdminAggregate adminUser = findByIdOrThrow(adminId);

        // 验证手机号唯一性（如果手机号有变化）
        if (StrUtil.isNotBlank(mobile) && !mobile.equals(adminUser.getMobile()) && adminUserRepository.existsByMobile(mobile)) {
            throw AdminException.of(AdminErrorCode.ADMIN_MOBILE_EXIST);
        }

        // 更新管理员信息
        adminUser.updateInfo(realName, mobile);

        log.info("管理员领域服务更新管理员，管理员ID: {}, 真实姓名: {}", adminId, realName);
        return adminUser;
    }

    @Override
    public AdminAggregate enableAdmin(String adminId) {
        return executeOperationWithResult(adminId,
                adminUser -> {
                    adminUser.enable();
                    return adminUser;
                },
                "启用管理员"
        );
    }

    @Override
    public AdminAggregate freezeAdmin(String adminId) {
        return executeOperationWithResult(adminId,
                adminUser -> {
                    adminUser.freeze();
                    return adminUser;
                },
                "冻结管理员"
        );
    }


    @Override
    public AdminAggregate updateLastLoginTime(String adminId) {
        return executeOperationWithResult(adminId,
                adminUser -> {
                    adminUser.updateLastLoginTime();
                    return adminUser;
                },
                "更新最后登录时间"
        );
    }

    @Override
    public AdminAggregate assignRoles(String adminId, List<String> roleIds) {
        // 验证角色是否存在且启用
        for (String roleId : roleIds) {
            RoleAggregate role = roleRepository.findById(roleId)
                    .orElseThrow(() -> AdminException.of(AdminErrorCode.ROLE_NOT_EXIST, "角色不存在: " + roleId));
            if (!role.isEnabled()) {
                throw AdminException.of(AdminErrorCode.ROLE_DISABLED, "角色已禁用: " + roleId);
            }
        }

        return executeOperationWithResult(adminId,
                adminUser -> {
                    adminUser.addRoles(roleIds);
                    return adminUser;
                },
                "分配角色"
        );
    }

    @Override
    public AdminAggregate findByIdOrThrow(String adminId) {
        return adminUserRepository.findById(adminId)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.ADMIN_USER_NOT_EXIST.getCode(), AdminErrorCode.ADMIN_USER_NOT_EXIST.getMessage()));
    }

    @Override
    protected AdminAggregate findEntityById(String adminId) {
        return findByIdOrThrow(adminId);
    }

    @Override
    public AdminAggregate findByMobile(String mobile) {
        return adminUserRepository.findByMobile(mobile)
                .orElse(null);
    }

    @Override
    public List<AdminAggregate> findNormalAdminUsers() {
        return adminUserRepository.findByStatus(AdminStatus.NORMAL);
    }


    @Override
    public boolean existsByMobile(String mobile) {
        return adminUserRepository.existsByMobile(mobile);
    }

    @Override
    public boolean isValidAdminUser(String adminId) {
        try {
            AdminAggregate adminUser = findByIdOrThrow(adminId);
            return adminUser.isAvailable();
        } catch (AdminException e) {
            return false;
        }
    }


    @Override
    public boolean hasRole(String adminId, String roleId) {
        try {
            AdminAggregate adminUser = findByIdOrThrow(adminId);
            return adminUser.hasRole(roleId);
        } catch (AdminException e) {
            return false;
        }
    }

    @Override
    public List<String> getPermissionsByAdminId(String adminId) {
        // 获取管理员
        AdminAggregate adminUser = findByIdOrThrow(adminId);

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
    public boolean hasPermission(String adminId, String permissionId) {
        List<String> permissions = getPermissionsByAdminId(adminId);
        return permissions.contains(permissionId);
    }

    @Override
    public AdminAggregate login(String adminId) {
        // 1. 根据手机号查找管理员
        AdminAggregate adminUser = findByIdOrThrow(adminId);

        // 2. 验证管理员状态（必须为NORMAL状态）
        if (!adminUser.isAvailable()) {
            log.error("管理员登录失败，账号状态不可用，管理员ID: {}, 状态: {}",
                    adminUser.getId(), adminUser.getStatus());
            throw AdminException.of(AdminErrorCode.ADMIN_USER_STATUS_ERROR);
        }

        // 3. 更新最后登录时间
        return updateLastLoginTime(adminUser.getId());
    }
}
