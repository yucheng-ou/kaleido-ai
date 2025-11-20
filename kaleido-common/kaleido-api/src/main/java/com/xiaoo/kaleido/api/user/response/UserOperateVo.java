package com.xiaoo.kaleido.api.user.response;

import com.xiaoo.kaleido.api.user.constant.UserStatusEnum;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ouyucheng
 * @date 2025/11/18
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOperateVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码（hash）
     */
    private String passwordHash;

    /**
     * 用户状态 活跃、冻结
     */
    private UserStatusEnum status;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请人ID
     */
    private String inviterId;

    /**
     * 用户手机号
     */
    private String telephone;


    /**
     * 用户头像URL地址
     */
    private String avatar;
}
