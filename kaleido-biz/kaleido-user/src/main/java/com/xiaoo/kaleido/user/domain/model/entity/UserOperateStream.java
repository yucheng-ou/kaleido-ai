package com.xiaoo.kaleido.user.domain.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.user.domain.constant.UserOperateType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户操作流水实体 该日志只记录用户自己的操作流水信息
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserOperateStream extends BaseEntity {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 操作类型
     */
    private UserOperateType operateType;

    /**
     * 操作详情
     */
    private String operateDetail;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 创建用户操作流水实体
     *
     * @param userId        用户ID
     * @param operateType   操作类型
     * @param operateDetail 操作详情
     * @return 用户操作流水实体
     */
    public static UserOperateStream create(String userId, UserOperateType operateType,
                                           String operateDetail) {
        return UserOperateStream.builder()
                .userId(userId)
                .operateType(operateType)
                .operateDetail(operateDetail)
                .operateTime(new Date())
                .build();
    }

}
