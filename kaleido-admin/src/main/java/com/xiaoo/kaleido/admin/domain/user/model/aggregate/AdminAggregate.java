package com.xiaoo.kaleido.admin.domain.user.model.aggregate;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.admin.domain.user.constant.AdminStatus;
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
public class AdminAggregate extends BaseEntity {

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
    private AdminStatus status;

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
     * @param mobile 手机号
     * @return 管理员对象
     */
    public static AdminAggregate create(String mobile) {
        return AdminAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .mobile(mobile)
                .status(AdminStatus.NORMAL)
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
        if (StrUtil.isNotBlank(realName)) {
            this.realName = realName;
        }
        if (StrUtil.isNotBlank(mobile)) {
            this.mobile = mobile;
        }
    }


    /**
     * 启用管理员
     */
    public void enable() {
        this.status = AdminStatus.NORMAL;
    }

    /**
     * 冻结管理员
     */
    public void freeze() {
        this.status = AdminStatus.FROZEN;
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
     * 判断是否拥有某个角色
     *
     * @param roleId 角色ID
     * @return 是否拥有
     */
    public boolean hasRole(String roleId) {
        return roleIds.contains(roleId);
    }

}
