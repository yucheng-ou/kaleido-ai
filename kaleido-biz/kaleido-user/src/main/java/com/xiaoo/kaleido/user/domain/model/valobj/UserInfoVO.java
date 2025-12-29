package com.xiaoo.kaleido.user.domain.model.valobj;

import com.xiaoo.kaleido.base.constant.enums.UserGenderEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author ouyucheng
 * @date 2025/12/26
 * @description
 */
@Data
public class UserInfoVO {

    /**
     * 用户id
     */
    private String id;

    /**
     * 手机号（唯一）
     */
    private String mobile;

    /**
     * 密码哈希值
     */
    private String passwordHash;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户邀请码（唯一）
     */
    private String inviteCode;

    /**
     * 邀请人ID（可为空）
     */
    private String inviterId;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 性别
     */
    private UserGenderEnum gender;
}
