package com.xiaoo.kaleido.user.domain.model.entity;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.api.user.constant.UserOperateTypeEnum;
import com.xiaoo.kaleido.ds.entity.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * @author ouyucheng
 * @date 2025/11/19
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("t_user_operate_stream")
public class UserOperateStream extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 操作类型
     */
    private UserOperateTypeEnum operateType;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作参数
     */
    private String operateParam;

    /**
     * 扩展字段
     */
    private String extendInfo;

    public static UserOperateStream operateStream(User user, UserOperateTypeEnum operateTypeEnum) {
        return UserOperateStream.builder()
                .userId(user.getId())
                .operateType(operateTypeEnum)
                .operateTime(new Date())
                .operateParam(JSONUtil.toJsonStr(user))
                .build();
    }
}
