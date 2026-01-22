package com.xiaoo.kaleido.admin.domain.user.adapter.event;

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
 * 权限变更事件
 * 监听到权限变更后要清理管理员权限缓存
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Component
public class AuthChangeEvent extends BaseEvent<AuthChangeEvent.AuthChangeMessage> {

    @Value("${spring.rabbitmq.topic.auth-change}")
    private String topic;

    @Override
    public EventMessage<AuthChangeMessage> buildEventMessage(AuthChangeMessage data) {
        return EventMessage.<AuthChangeMessage>builder()
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
    public static class AuthChangeMessage {
        /**
         * 管理员ID
         */
        private String adminId;
    }
}
