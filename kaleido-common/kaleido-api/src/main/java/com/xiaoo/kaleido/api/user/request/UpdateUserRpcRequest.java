package com.xiaoo.kaleido.api.user.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 更新用户信息RPC请求
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Data
public class UpdateUserRpcRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 昵称（可选）
     */
    private String nickName;

    /**
     * 头像URL（可选）
     */
    private String avatarUrl;

    /**
     * 邮箱（可选）
     */
    private String email;
}
