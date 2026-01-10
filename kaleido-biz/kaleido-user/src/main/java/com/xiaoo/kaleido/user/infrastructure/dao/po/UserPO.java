package com.xiaoo.kaleido.user.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.base.constant.enums.UserGenderEnum;
import com.xiaoo.kaleido.ds.po.BasePO;
import com.xiaoo.kaleido.user.domain.constant.UserStatus;
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
@TableName("t_user")
public class UserPO extends BasePO {

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 状态
     */
    @TableField("status")
    private UserStatus status;

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
    @TableField("avatar")
    private String avatar;

    /**
     * 性别
     */
    private UserGenderEnum gender;
}
