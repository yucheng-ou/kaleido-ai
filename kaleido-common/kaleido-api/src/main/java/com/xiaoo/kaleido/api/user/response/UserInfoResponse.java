package com.xiaoo.kaleido.api.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息响应
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@Schema(description = "用户信息响应")
public class UserInfoResponse {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "1234567890123456789")
    private String userId;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    private String telephone;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    private String nickName;

    /**
     * 用户状态
     */
    @Schema(description = "用户状态", example = "ACTIVE")
    private String status;

    /**
     * 邀请码
     */
    @Schema(description = "邀请码", example = "INVITE123")
    private String inviteCode;

    /**
     * 邀请人ID
     */
    @Schema(description = "邀请人ID", example = "1234567890123456789")
    private String inviterId;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间", example = "2025-12-29T15:20:00.000+08:00")
    private Date lastLoginTime;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;
}
