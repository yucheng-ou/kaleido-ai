package com.xiaoo.kaleido.user.domain.event;

import lombok.Getter;

/**
 * 用户昵称修改事件
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Getter
public class UserNickNameChangedEvent extends BaseDomainEvent {

    /**
     * 用户ID
     */
    private final String userId;

    /**
     * 旧昵称
     */
    private final String oldNickName;

    /**
     * 新昵称
     */
    private final String newNickName;

    public UserNickNameChangedEvent(String userId, String oldNickName, String newNickName) {
        super(userId, "UserAggregate");
        this.userId = userId;
        this.oldNickName = oldNickName;
        this.newNickName = newNickName;
    }

    @Override
    public String getEventType() {
        return "USER_NICKNAME_CHANGED";
    }

    @Override
    public String getDescription() {
        return String.format("用户昵称修改事件：用户ID=%s，旧昵称=%s，新昵称=%s",
                userId, oldNickName, newNickName);
    }
}
