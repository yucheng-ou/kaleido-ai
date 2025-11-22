package com.xiaoo.kaleido.api.user.response;

import com.xiaoo.kaleido.api.user.constant.UserStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户基础信息VO
 * 包含用户的基本信息字段，如ID、昵称、手机号等
 *
 * @author ouyucheng
 * @date 2025/11/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserBasicInfoVO implements Serializable {

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
    private Long inviterId;

    /**
     * 用户手机号
     */
    private String telephone;

    /**
     * 用户头像URL地址
     */
    private String avatar;
}
