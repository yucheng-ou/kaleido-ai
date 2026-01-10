package com.xiaoo.kaleido.admin.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.admin.domain.user.constant.AdminStatus;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 管理员持久化对象
 * 对应数据库表：t_admin
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_admin")
public class AdminPO extends BasePO {

    /**
     * 管理员真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 管理员状态
     */
    @TableField("status")
    private AdminStatus status;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;
}
