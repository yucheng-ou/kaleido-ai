package com.xiaoo.kaleido.admin.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.xiaoo.kaleido.admin.domain.user.adapter.event.AuthChangeEvent;
import com.xiaoo.kaleido.mq.event.BaseEvent;
import com.xiaoo.kaleido.redis.service.RedissonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 权限变更消费者
 *
 * @author ouyucheng
 * @date 2025/1/21
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthChangeCustomer {

    @Value("${spring.rabbitmq.topic.auth-change}")
    private String topic;

    private final RedissonService redissonService;

    public static final String ADMIN_ROLE_LIST_KEY = "kaleido:admin:roles:";
    public static final String ADMIN_PERM_LIST_KEY = "kaleido:admin:perms:";


    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.auth-change}"))
    public void listener(String message) {
        try {
            log.info("监听用户权限变更发送消息，开始清空缓存 topic: {} message: {}", topic, message);
            BaseEvent.EventMessage<AuthChangeEvent.AuthChangeMessage> eventMessage =
                    JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<AuthChangeEvent.AuthChangeMessage>>() {
                    }.getType());

            AuthChangeEvent.AuthChangeMessage authChangeMessage = eventMessage.getData();

            // 清空管理员角色缓存
            redissonService.remove(ADMIN_ROLE_LIST_KEY + authChangeMessage.getAdminId());
            // 清空管理员权限缓存
            redissonService.remove(ADMIN_PERM_LIST_KEY + authChangeMessage.getAdminId());

            log.info("监听用户权限变更发送消息，缓存清除完成 topic: {} message: {}", topic, message);
        } catch (Exception e) {
            log.error("监听用户权限变更发送消息，消费失败 topic: {} message: {}", topic, message);
            throw e;
        }
    }
}
