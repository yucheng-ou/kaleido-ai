package com.xiaoo.kaleido.api.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户信息VO
 * 扩展用户基础信息，包含邀请人昵称等业务字段
 *
 * @author ouyucheng
 * @date 2025/11/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserInfoVO extends UserBasicInfoVO {

    /**
     * 邀请人昵称
     */
    private String inviterNickName;

}
