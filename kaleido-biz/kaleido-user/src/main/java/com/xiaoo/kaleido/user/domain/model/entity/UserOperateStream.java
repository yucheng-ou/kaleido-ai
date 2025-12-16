package com.xiaoo.kaleido.user.domain.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.user.domain.constant.UserOperateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 用户操作流水实体
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
     * 操作详情（JSON格式）
     */
    private String operateDetail;

    /**
     * 操作者ID
     */
    private String operatorId;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 创建用户操作流水实体
     *
     * @param userId        用户ID
     * @param operateType   操作类型
     * @param operateDetail 操作详情
     * @param operatorId    操作者ID
     * @return 用户操作流水实体
     */
    public static UserOperateStream create(String userId, UserOperateType operateType,
                                           String operateDetail, String operatorId) {
        UserOperateStream stream = UserOperateStream.builder()
                .userId(userId)
                .operateType(operateType)
                .operateDetail(operateDetail)
                .operatorId(operatorId)
                .operateTime(LocalDateTime.now())
                .build();
        stream.setCreatedAt(new java.util.Date());
        return stream;
    }

    /**
     * 创建用户操作流水实体（操作者为用户自己）
     *
     * @param userId        用户ID
     * @param operateType   操作类型
     * @param operateDetail 操作详情
     * @return 用户操作流水实体
     */
    public static UserOperateStream createBySelf(String userId, UserOperateType operateType,
                                                 String operateDetail) {
        return create(userId, operateType, operateDetail, userId);
    }

    /**
     * 验证操作流水是否有效
     */
    public void validate() {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        if (operateType == null) {
            throw new IllegalArgumentException("操作类型不能为空");
        }
        if (operateTime == null) {
            throw new IllegalArgumentException("操作时间不能为空");
        }
    }
}
