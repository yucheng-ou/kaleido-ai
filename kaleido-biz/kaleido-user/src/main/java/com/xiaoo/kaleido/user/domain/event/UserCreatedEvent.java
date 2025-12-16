package com.xiaoo.kaleido.user.domain.event;

import com.xiaoo.kaleido.user.domain.model.entity.User;
import lombok.Getter;

/**
 * 用户创建事件
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Getter
public class UserCreatedEvent extends BaseDomainEvent {

    /**
     * 用户实体
     */
    private final User user;

    /**
     * 邀请人ID（可为空）
     */
    private final String inviterId;

    public UserCreatedEvent(User user, String inviterId) {
        super(user.getId(), "UserAggregate");
        this.user = user;
        this.inviterId = inviterId;
    }

    @Override
    public String getEventType() {
        return "USER_CREATED";
    }

    @Override
    public String getDescription() {
        return String.format("用户创建事件：用户ID=%s，手机号=%s，邀请人ID=%s",
                user.getId(), user.getTelephone(), inviterId != null ? inviterId : "无");
    }
}
