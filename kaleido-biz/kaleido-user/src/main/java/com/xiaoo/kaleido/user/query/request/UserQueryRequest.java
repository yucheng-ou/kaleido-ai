package com.xiaoo.kaleido.user.query.request;

import com.xiaoo.kaleido.user.domain.constant.UserStatus;
import lombok.Data;

/**
 * 用户查询请求
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
public class UserQueryRequest {

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
     * 邀请人ID
     */
    private String inviterId;

    /**
     * 邀请码
     */
    private String inviteCode;
}
