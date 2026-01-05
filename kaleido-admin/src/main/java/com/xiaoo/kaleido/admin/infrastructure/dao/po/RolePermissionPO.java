package com.xiaoo.kaleido.admin.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限关联持久化对象
 * 对应数据库表：role_permission
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("role_permission")
public class RolePermissionPO extends BasePO {

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 权限ID
     */
    @TableField("permission_id")
    private String permissionId;
}
