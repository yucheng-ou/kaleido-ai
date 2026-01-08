package com.xiaoo.kaleido.admin.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.admin.domain.user.constant.AdminUserStatus;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 管理员持久化对象
 * 对应数据库表：t_admin_user
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_admin_user")
public class AdminUserPO extends BasePO {

    /**
     * 管理员账号（唯一）
     */
    @TableField("username")
    private String username;

    /**
     * 密码哈希值
     */
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 管理员状态：1-正常 2-冻结 3-删除
     */
    @TableField("status")
    private AdminUserStatus status;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;
}
