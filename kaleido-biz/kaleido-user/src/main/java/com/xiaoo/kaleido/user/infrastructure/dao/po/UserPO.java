package com.xiaoo.kaleido.user.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户持久化对象
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class UserPO extends BasePO {

    /**
     * 用户ID（业务主键）
     */
    @TableField("user_id")
    private String userId;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 密码哈希
     */
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 状态：0-正常，1-冻结，2-删除
     */
    private Integer status;

    /**
     * 邀请码
     */
    @TableField("invite_code")
    private String inviteCode;

    /**
     * 邀请人ID
     */
    @TableField("inviter_id")
    private String inviterId;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;
}
