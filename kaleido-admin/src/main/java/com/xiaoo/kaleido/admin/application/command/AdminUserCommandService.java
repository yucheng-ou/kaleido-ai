package com.xiaoo.kaleido.admin.application.command;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.MD5;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminUserRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminUserAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.AdminUserDomainService;
import com.xiaoo.kaleido.api.admin.auth.command.*;
import com.xiaoo.kaleido.api.admin.auth.response.AdminLoginResponse;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理员命令服务（应用层）
 * 负责管理员相关的写操作编排
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserCommandService {

    private final IAdminUserRepository adminUserRepository;
    private final AdminUserDomainService adminUserDomainService;

    /**
     * 创建管理员
     *
     * @param command 创建管理员命令
     * @return 管理员ID
     */
    public String createAdminUser(AddAdminUserCommand command) {
        // 密码加密
        String passwordHash = MD5.create().digestHex16(command.getPassword());
        ;

        // 调用领域服务创建管理员
        AdminUserAggregate adminUserAggregate = adminUserDomainService.createAdminUser(
                command.getUsername(),
                passwordHash,
                command.getRealName(),
                command.getMobile()
        );

        // 保存管理员
        adminUserRepository.save(adminUserAggregate);

        log.info("管理员创建成功，管理员ID: {}, 账号: {}",
                adminUserAggregate.getId(), command.getUsername());
        return adminUserAggregate.getId();
    }

    /**
     * 更新管理员信息
     *
     * @param command 更新管理员信息命令
     */
    @Transactional
    public void updateAdminUser(UpdateAdminUserCommand command) {
        // 调用领域服务更新管理员信息
        AdminUserAggregate adminUserAggregate = adminUserDomainService.updateAdminUser(
                command.getAdminUserId(),
                command.getRealName(),
                command.getMobile()
        );

        // 保存管理员
        adminUserRepository.update(adminUserAggregate);

        log.info("管理员信息更新成功，管理员ID: {}", command.getAdminUserId());
    }

    /**
     * 修改管理员密码
     *
     * @param command 修改管理员密码命令
     */
    @Transactional
    public void changePassword(ChangeAdminUserPasswordCommand command) {

        // 验证旧密码是否正确
        adminUserDomainService.verifyPassword(
                command.getAdminUserId(),
                MD5.create().digestHex16(command.getOldPassword())
        );

        // 新密码加密
        String newPasswordHash = MD5.create().digestHex16(command.getNewPassword());

        // 调用领域服务更新密码
        AdminUserAggregate updatedAggregate = adminUserDomainService.updatePassword(
                command.getAdminUserId(),
                newPasswordHash
        );

        // 保存管理员
        adminUserRepository.save(updatedAggregate);

        log.info("管理员密码修改成功，管理员ID: {}", command.getAdminUserId());
    }

    /**
     * 启用管理员
     *
     * @param command 启用管理员命令
     */
    @Transactional
    public void enableAdminUser(EnableAdminUserCommand command) {
        // 调用领域服务启用管理员
        AdminUserAggregate adminUserAggregate = adminUserDomainService.enableAdminUser(command.getAdminUserId());

        // 保存管理员
        adminUserRepository.save(adminUserAggregate);

        log.info("管理员启用成功，管理员ID: {}", command.getAdminUserId());
    }

    /**
     * 冻结管理员
     *
     * @param command 冻结管理员命令
     */
    @Transactional
    public void freezeAdminUser(FreezeAdminUserCommand command) {
        // 调用领域服务冻结管理员
        AdminUserAggregate adminUserAggregate = adminUserDomainService.freezeAdminUser(command.getAdminUserId());

        // 保存管理员
        adminUserRepository.save(adminUserAggregate);

        log.info("管理员冻结成功，管理员ID: {}", command.getAdminUserId());
    }

    /**
     * 删除管理员
     *
     * @param command 删除管理员命令
     */
    @Transactional
    public void deleteAdminUser(DeleteAdminUserCommand command) {
        // 调用领域服务删除管理员
        adminUserDomainService.deleteAdminUser(command.getAdminUserId());

        log.info("管理员删除成功，管理员ID: {}", command.getAdminUserId());
    }

    /**
     * 分配角色给管理员
     *
     * @param command 分配角色命令
     */
    @Transactional
    public void assignRoles(AssignRolesToAdminUserCommand command) {
        // 调用领域服务分配角色
        AdminUserAggregate adminUserAggregate = adminUserDomainService.assignRoles(
                command.getAdminUserId(),
                command.getRoleIds()
        );

        // 保存管理员
        adminUserRepository.save(adminUserAggregate);

        log.info("角色分配成功，管理员ID: {}, 角色数量: {}",
                command.getAdminUserId(), command.getRoleIds().size());
    }

    /**
     * 从管理员移除角色
     *
     * @param adminUserId 管理员ID
     * @param roleIds     角色ID列表
     */
    @Transactional
    public void removeRoles(String adminUserId, List<String> roleIds) {
        // 调用领域服务移除角色
        AdminUserAggregate adminUserAggregate = adminUserDomainService.removeRoles(adminUserId, roleIds);

        // 保存管理员
        adminUserRepository.save(adminUserAggregate);

        log.info("角色移除成功，管理员ID: {}, 移除角色数量: {}", adminUserId, roleIds.size());
    }

    /**
     * 清空管理员角色
     *
     * @param adminUserId 管理员ID
     */
    @Transactional
    public void clearRoles(String adminUserId) {
        // 调用领域服务清空角色
        AdminUserAggregate adminUserAggregate = adminUserDomainService.clearRoles(adminUserId);

        // 保存管理员
        adminUserRepository.save(adminUserAggregate);

        log.info("角色清空成功，管理员ID: {}", adminUserId);
    }


    /**
     * 验证管理员账号是否可用
     *
     * @param username 管理员账号
     * @return 是否可用
     */
    public boolean isUsernameAvailable(String username) {
        return adminUserDomainService.isUsernameAvailable(username);
    }

    /**
     * 验证管理员是否存在且可用
     *
     * @param adminUserId 管理员ID
     * @return 是否存在且可用
     */
    public boolean isValidAdminUser(String adminUserId) {
        return adminUserDomainService.isValidAdminUser(adminUserId);
    }

    /**
     * 验证管理员是否有某个权限
     *
     * @param adminUserId  管理员ID
     * @param permissionId 权限ID
     * @return 是否有权限
     */
    public boolean hasPermission(String adminUserId, String permissionId) {
        return adminUserDomainService.hasPermission(adminUserId, permissionId);
    }


    /**
     * 管理员登录
     *
     * @param adminId 管理员id
     */
    @Transactional
    public void login(String adminId) {

        // 1. 根据手机号查找管理员
        AdminUserAggregate adminUser = adminUserDomainService.findByIdOrThrow(adminId);

        // 2. 验证管理员状态（必须为NORMAL状态）
        if (!adminUser.isAvailable()) {
            log.error("管理员登录失败，账号状态不可用，管理员ID: {}, 状态: {}",
                    adminUser.getId(), adminUser.getStatus());
            throw AdminException.of(AdminErrorCode.ADMIN_USER_STATUS_ERROR);
        }

        // 3. 更新最后登录时间
        adminUserDomainService.updateLastLoginTime(adminUser.getId());

        log.info("管理员登录成功（生成token），管理员ID: {}", adminId);
    }
}
