package com.xiaoo.kaleido.user.domain.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.user.domain.constant.UserOperateType;
import com.xiaoo.kaleido.user.types.exception.UserException;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户操作流水实体
 * <p>
 * 记录用户所有操作的流水信息，用于审计、追溯和监控
 * 该日志只记录用户自己的操作流水信息，不记录系统自动操作
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
     * 操作所属用户的唯一标识
     */
    private String userId;

    /**
     * 操作类型
     * 用户操作的类型，使用UserOperateType枚举定义
     */
    private UserOperateType operateType;

    /**
     * 操作详情
     * 操作的详细描述，包含操作的具体内容和参数
     */
    private String operateDetail;

    /**
     * 操作时间
     * 操作发生的时间戳
     */
    private Date operateTime;

    /**
     * 创建用户操作流水实体

     * 用于记录用户操作时创建操作流水实体
     *
     * @param userId        用户ID，不能为空
     * @param operateType   操作类型，不能为空
     * @param operateDetail 操作详情，不能为空
     * @return 用户操作流水实体
     */
    public static UserOperateStream create(String userId, UserOperateType operateType,
                                           String operateDetail) {
        if (userId == null || userId.isEmpty()) {
            throw UserException.of(UserErrorCode.USER_ID_EMPTY);
        }
        if (operateType == null) {
            throw UserException.of(UserErrorCode.REQUEST_PARAM_NULL, "操作类型不能为空");
        }
        if (operateDetail == null || operateDetail.isEmpty()) {
            throw UserException.of(UserErrorCode.REQUEST_PARAM_NULL, "操作详情不能为空");
        }
        
        return UserOperateStream.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .operateType(operateType)
                .operateDetail(operateDetail)
                .operateTime(new Date())
                .build();
    }

}
