package com.xiaoo.kaleido.admin.domain.user.model.aggregate;

import com.xiaoo.kaleido.admin.domain.user.constant.AdminUserStatus;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管理员聚合根
 * 封装管理员相关的业务规则和一致性边界
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AdminUserAggregate extends BaseEntity {

    /**
     * 管理员账号（唯一）
     */
    private String username;

    /**
     * 密码哈希值
     */
    private String passwordHash;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 管理员状态
     */
    private AdminUserStatus status;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 角色ID列表
     */
    @Builder.Default
    private List<String> roleIds = new ArrayList<>();

    /**
     * 创建管理员
     *
     * @param username     管理员账号
     * @param passwordHash 密码哈希值
     * @param realName     真实姓名
     * @param mobile       手机号
     * @return 管理员对象
     */
    public static AdminUserAggregate create(String username, String passwordHash,
                                            String realName, String mobile) {
        return AdminUserAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .username(username)
                .passwordHash(passwordHash)
                .realName(realName)
                .mobile(mobile)
                .status(AdminUserStatus.NORMAL)
                .lastLoginTime(null)
                .roleIds(new ArrayList<>())
                .build();
    }

    /**
     * 更新管理员信息
     *
     * @param realName 真实姓名
     * @param mobile   手机号
     */
    public void updateInfo(String realName, String mobile) {
        if (realName != null && !realName.trim().isEmpty()) {
            this.realName = realName;
        }
        if (mobile != null) {
            this.mobile = mobile;
        }
    }

    /**
     * 更新密码
     *
     * @param passwordHash 密码哈希值
     */
    public void updatePassword(String passwordHash) {
        if (passwordHash != null && !passwordHash.trim().isEmpty()) {
            this.passwordHash = passwordHash;
        }
    }

    /**
     * 启用管理员
     */
    public void enable() {
        this.status = AdminUserStatus.NORMAL;
    }

    /**
     * 冻结管理员
     */
    public void freeze() {
        this.status = AdminUserStatus.FROZEN;
    }

    /**
     * 删除管理员
     */
    public void delete() {
        this.status = AdminUserStatus.DELETED;
    }

    /**
     * 更新最后登录时间
     */
    public void updateLastLoginTime() {
        this.lastLoginTime = new Date();
    }

    /**
     * 添加角色
     *
     * @param roleId 角色ID
     */
    public void addRole(String roleId) {
        if (roleId != null && !roleId.trim().isEmpty()) {
            if (!roleIds.contains(roleId)) {
                roleIds.add(roleId);
            }
        }
    }

    /**
     * 批量添加角色
     *
     * @param roleIds 角色ID列表
     */
    public void addRoles(List<String> roleIds) {
        if (roleIds != null) {
            for (String roleId : roleIds) {
                addRole(roleId);
            }
        }
    }

    /**
     * 移除角色
     *
     * @param roleId 角色ID
     */
    public void removeRole(String roleId) {
        if (roleId != null) {
            roleIds.remove(roleId);
        }
    }

    /**
     * 批量移除角色
     *
     * @param roleIds 角色ID列表
     */
    public void removeRoles(List<String> roleIds) {
        if (roleIds != null) {
            this.roleIds.removeAll(roleIds);
        }
    }

    /**
     * 清空角色
     */
    public void clearRoles() {
        roleIds.clear();
    }

    /**
     * 判断管理员是否正常
     *
     * @return 是否正常
     */
    public boolean isNormal() {
        return status != null && status.isNormal();
    }

    /**
     * 判断管理员是否冻结
     *
     * @return 是否冻结
     */
    public boolean isFrozen() {
        return status != null && status.isFrozen();
    }

    /**
     * 判断管理员是否删除
     *
     * @return 是否删除
     */
    public boolean isDeleted() {
        return status != null && status.isDeleted();
    }

    /**
     * 判断管理员是否可用
     *
     * @return 是否可用
     */
    public boolean isAvailable() {
        return isNormal();
    }

    /**
     * 判断管理员是否可操作
     *
     * @return 是否可操作
     */
    public boolean isOperable() {
        return status != null && status.isOperable();
    }

    /**
     * 验证管理员账号是否匹配
     *
     * @param username 管理员账号
     * @return 是否匹配
     */
    public boolean matches(String username) {
        return this.username != null && this.username.equals(username);
    }

    /**
     * 验证密码
     *
     * @param passwordHash 密码哈希值
     */
    public void verifyPassword(String passwordHash) {
        boolean verify = this.passwordHash != null && this.passwordHash.equals(passwordHash);
        if (verify) {
            throw AdminException.of(AdminErrorCode.ADMIN_USER_PASSWORD_ERROR);
        }
    }

    /**
     * 判断是否拥有某个角色
     *
     * @param roleId 角色ID
     * @return 是否拥有
     */
    public boolean hasRole(String roleId) {
        return roleIds.contains(roleId);
    }

    /**
     * 获取角色数量
     *
     * @return 角色数量
     */
    public int getRoleCount() {
        return roleIds.size();
    }

}
