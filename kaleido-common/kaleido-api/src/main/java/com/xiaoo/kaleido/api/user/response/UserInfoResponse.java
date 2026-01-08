package com.xiaoo.kaleido.api.user.response;
import lombok.Data;
import java.util.Date;
/**
 * 用户信息响应
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
public class UserInfoResponse {
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
    private String avatar;
}
