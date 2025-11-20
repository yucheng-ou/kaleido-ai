package com.xiaoo.kaleido.user.domain.model.aggregate;

import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ouyucheng
 * @date 2025/11/19
 * @description 用户操作聚合对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOperateAggregate {
    /**
     * 用户实体信息
     */
    private User user;

    /**
     * 用户操作流水实体信息
     */
    private UserOperateStream userOperateStream;
}
