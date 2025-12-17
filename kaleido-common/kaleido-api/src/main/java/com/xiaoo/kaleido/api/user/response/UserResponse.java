package com.xiaoo.kaleido.api.user.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户RPC响应DTO
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Data
public class UserResponse implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

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
     * 用户状态（字符串形式，避免枚举依赖）
     */
    private String status;

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
