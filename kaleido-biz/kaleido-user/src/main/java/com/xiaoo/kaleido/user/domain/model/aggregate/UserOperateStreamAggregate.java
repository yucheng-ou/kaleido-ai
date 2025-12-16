package com.xiaoo.kaleido.user.domain.model.aggregate;

import com.xiaoo.kaleido.user.domain.constant.UserOperateType;
import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 用户操作流水聚合根
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Getter
public class UserOperateStreamAggregate {

    /**
     * 用户操作流水实体
     */
    private final UserOperateStream userOperateStream;

    public UserOperateStreamAggregate(UserOperateStream userOperateStream) {
        this.userOperateStream = userOperateStream;
    }

    /**
     * 创建用户操作流水聚合根
     *
     * @param userId        用户ID
     * @param operateType   操作类型
     * @param operateDetail 操作详情
     * @param operatorId    操作者ID
     * @return 用户操作流水聚合根
     */
    public static UserOperateStreamAggregate create(String userId, UserOperateType operateType,
                                                    String operateDetail, String operatorId) {
        UserOperateStream stream = UserOperateStream.create(userId, operateType, operateDetail, operatorId);
        return new UserOperateStreamAggregate(stream);
    }

    /**
     * 创建用户操作流水聚合根（操作者为用户自己）
     *
     * @param userId        用户ID
     * @param operateType   操作类型
     * @param operateDetail 操作详情
     * @return 用户操作流水聚合根
     */
    public static UserOperateStreamAggregate createBySelf(String userId, UserOperateType operateType,
                                                          String operateDetail) {
        UserOperateStream stream = UserOperateStream.createBySelf(userId, operateType, operateDetail);
        return new UserOperateStreamAggregate(stream);
    }

    /**
     * 验证操作流水是否有效
     */
    public void validate() {
        if (userOperateStream.getUserId() == null || userOperateStream.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        if (userOperateStream.getOperateType() == null) {
            throw new IllegalArgumentException("操作类型不能为空");
        }
        if (userOperateStream.getOperateTime() == null) {
            throw new IllegalArgumentException("操作时间不能为空");
        }
    }

    /**
     * 获取操作流水ID
     */
    public String getId() {
        return userOperateStream.getId();
    }

    /**
     * 设置操作流水ID（通常在持久化时由数据库生成）
     */
    public void setId(String id) {
        userOperateStream.setId(id);
    }
}
