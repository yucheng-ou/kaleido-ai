package com.xiaoo.kaleido.api.user.query;

import com.xiaoo.kaleido.base.constant.enums.UserGenderEnum;
import com.xiaoo.kaleido.base.request.BasePageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户分页查询请求")
public class UserPageQueryReq extends BasePageReq {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户昵称（模糊查询）")
    private String nickName;

    @Schema(description = "手机号（模糊查询）")
    private String telephone;

    @Schema(description = "用户状态（1-正常，2-冻结，3-已删除）")
    private Integer status;

    @Schema(description = "性别")
    private UserGenderEnum gender;
}
