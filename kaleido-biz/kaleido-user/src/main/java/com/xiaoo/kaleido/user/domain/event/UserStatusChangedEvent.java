package com.xiaoo.kaleido.user.domain.event;

import com.xiaoo.kaleido.user.domain.constant.UserStatus;
import lombok.Getter;

/**
 * 用户状态变更事件
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Getter
public class UserStatusChangedEvent extends BaseDomainEvent {

    /**
     * 用户ID
     */
    private final String userId;

    /**
     * 旧状态
     */
    private final UserStatus oldStatus;

    /**
     * 新状态
     */
    private final UserStatus newStatus;

    /**
     * 变更原因
     */
    private final String reason;

    public UserStatusChangedEvent(String userId, UserStatus oldStatus, UserStatus newStatus, String reason) {
        super(userId, "UserAggregate");
        this.userId = userId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.reason = reason;
    }

    @Override
    public String getEventType() {
        return "USER_STATUS_CHANGED";
    }

    @Override
    public String getDescription() {
        return String.format("用户状态变更事件：用户ID=%s，旧状态=%s，新状态=%s，原因=%s",
                userId, oldStatus != null ? oldStatus.name() : "null", 
                newStatus != null ? newStatus.name() : "null", reason);
    }
}
