package com.xiaoo.kaleido.user.domain.adapter.event;

import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.mq.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 用户注册事件
 * 用于在用户注册成功后通知其他服务进行相关初始化操作
 * 例如：初始化金币账户、处理邀请奖励等
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Component
public class UserRegisteredEvent extends BaseEvent<UserRegisteredEvent.UserRegisteredMessage> {

    @Value("${spring.rabbitmq.topic.user-registered}")
    private String topic;

    @Override
    public EventMessage<UserRegisteredMessage> buildEventMessage(UserRegisteredMessage data) {
        return EventMessage.<UserRegisteredMessage>builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .timestamp(new Date())
                .data(data)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRegisteredMessage {
        /**
         * 用户ID
         */
        private String userId;

        /**
         * 手机号
         */
        private String telephone;

        /**
         * 邀请人ID（可为空，表示无邀请人）
         */
        private String inviterId;

        /**
         * 用户自己的邀请码
         */
        private String inviteCode;
    }
}
