package com.xiaoo.kaleido.user.query.dto;

import com.xiaoo.kaleido.user.domain.constant.UserStatus;
import lombok.Data;

import java.util.Date;

/**
 * 用户查询DTO
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
public class UserDTO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户状态
     */
    private UserStatus status;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请人ID
     */
    private String inviterId;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
