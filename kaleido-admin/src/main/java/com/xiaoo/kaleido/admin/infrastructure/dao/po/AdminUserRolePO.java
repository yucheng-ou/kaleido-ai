package com.xiaoo.kaleido.admin.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员角色关联持久化对象
 * 对应数据库表：admin_user_role
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("admin_user_role")
public class AdminUserRolePO extends BasePO {

    /**
     * 管理员ID
     */
    @TableField("admin_user_id")
    private String adminUserId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;
}
