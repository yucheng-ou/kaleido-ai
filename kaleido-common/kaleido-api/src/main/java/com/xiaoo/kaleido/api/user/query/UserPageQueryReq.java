package com.xiaoo.kaleido.api.user.query;

import com.xiaoo.kaleido.base.constant.enums.UserGenderEnum;
import com.xiaoo.kaleido.base.request.BasePageReq;
import lombok.*;

import java.io.Serial;

/**
 * 用户分页查询请求
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPageQueryReq extends BasePageReq {

    private String nickName;

    private String telephone;

    private Integer status;

    private UserGenderEnum gender;
}
