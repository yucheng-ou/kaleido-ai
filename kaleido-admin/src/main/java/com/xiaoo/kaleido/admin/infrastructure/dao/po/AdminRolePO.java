package com.xiaoo.kaleido.admin.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员角色关联持久化对象
 * 对应数据库表：t_admin_role
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_admin_role")
public class AdminRolePO extends BasePO {

    /**
     * 管理员ID
     */
    @TableField("admin_id")
    private String adminId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;
}
