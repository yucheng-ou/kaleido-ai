package com.xiaoo.kaleido.admin.domain.user.model.aggregate;

import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色聚合根
 * 封装角色相关的业务规则和一致性边界
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RoleAggregate extends BaseEntity {

    /**
     * 角色编码（唯一）
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色状态
     */
    private DataStatusEnum status;

    /**
     * 权限ID列表
     */
    @Builder.Default
    private List<String> permissionIds = new ArrayList<>();

    /**
     * 创建角色
     *
     * @param code        角色编码
     * @param name        角色名称
     * @param description 角色描述
     * @return 角色对象
     */
    public static RoleAggregate create(String code, String name, String description) {
        return RoleAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .code(code)
                .name(name)
                .description(description)
                .status(DataStatusEnum.ENABLE)
                .permissionIds(new ArrayList<>())
                .build();
    }

    /**
     * 更新角色信息
     *
     * @param name        角色名称
     * @param description 角色描述
     */
    public void updateInfo(String name, String description) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
    }

    /**
     * 启用角色
     */
    public void enable() {
        this.status = DataStatusEnum.ENABLE;
    }

    /**
     * 禁用角色
     */
    public void disable() {
        this.status = DataStatusEnum.DISABLE;
    }

    /**
     * 添加权限
     *
     * @param permissionId 权限ID
     */
    public void addPermission(String permissionId) {
        if (permissionId != null && !permissionId.trim().isEmpty()) {
            if (!permissionIds.contains(permissionId)) {
                permissionIds.add(permissionId);
            }
        }
    }

    /**
     * 批量添加权限
     *
     * @param permissionIds 权限ID列表
     */
    public void addPermissions(List<String> permissionIds) {
        if (permissionIds != null) {
            for (String permissionId : permissionIds) {
                addPermission(permissionId);
            }
        }
    }

    /**
     * 移除权限
     *
     * @param permissionId 权限ID
     */
    public void removePermission(String permissionId) {
        if (permissionId != null) {
            permissionIds.remove(permissionId);
        }
    }

    /**
     * 批量移除权限
     *
     * @param permissionIds 权限ID列表
     */
    public void removePermissions(List<String> permissionIds) {
        if (permissionIds != null) {
            this.permissionIds.removeAll(permissionIds);
        }
    }

    /**
     * 清空权限
     */
    public void clearPermissions() {
        permissionIds.clear();
    }

    /**
     * 判断角色是否启用
     *
     * @return 是否启用
     */
    public boolean isEnabled() {
        return status != null && status == DataStatusEnum.ENABLE;
    }

    /**
     * 判断是否拥有某个权限
     *
     * @param permissionId 权限ID
     * @return 是否拥有
     */
    public boolean hasPermission(String permissionId) {
        return permissionIds.contains(permissionId);
    }
}
