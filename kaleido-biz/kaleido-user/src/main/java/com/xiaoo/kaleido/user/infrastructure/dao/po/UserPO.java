package com.xiaoo.kaleido.user.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.base.constant.enums.UserGenderEnum;
import com.xiaoo.kaleido.ds.po.BasePO;
import com.xiaoo.kaleido.api.user.enums.UserStatus;
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
     * 用户注册和登录的主要标识，唯一索引
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 昵称
     * 用户显示名称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 状态
     * 用户当前状态，包括活跃、冻结、删除等
     */
    @TableField("status")
    private UserStatus status;

    /**
     * 邀请码
     * 用户用于邀请新用户的唯一代码
     */
    @TableField("invite_code")
    private String inviteCode;

    /**
     * 邀请人ID
     * 邀请该用户注册的用户ID
     */
    @TableField("inviter_id")
    private String inviterId;

    /**
     * 最后登录时间
     * 记录用户最后一次登录的时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 头像URL
     * 用户头像的URL地址
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 性别
     * 用户性别，使用枚举类型表示
     */
    private UserGenderEnum gender;
}
