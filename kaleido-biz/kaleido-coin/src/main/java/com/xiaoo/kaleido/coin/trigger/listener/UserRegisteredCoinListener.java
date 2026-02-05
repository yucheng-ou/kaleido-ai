package com.xiaoo.kaleido.coin.trigger.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xiaoo.kaleido.api.coin.command.InitAccountCommand;
import com.xiaoo.kaleido.api.coin.command.ProcessInviteRewardCommand;
import com.xiaoo.kaleido.coin.application.command.CoinCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用户注册金币服务监听器
 * 监听用户注册事件，初始化用户金币账户并处理邀请奖励
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserRegisteredCoinListener {

    @Value("${spring.rabbitmq.topic.user-registered}")
    private String topic;

    private final CoinCommandService coinCommandService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.user-registered}"))
    public void listener(String message) {
        try {
            log.info("监听用户注册事件，开始初始化金币账户 topic: {} message: {}", topic, message);
            
            // 解析消息 - 使用通用的JSON解析
            JSONObject jsonObject = JSON.parseObject(message);
            JSONObject data = jsonObject.getJSONObject("data");
            
            String userId = data.getString("userId");
            String inviterUserId = data.getString("inviterId");
            
            if (StrUtil.isEmpty(userId)) {
                log.error("用户注册事件消息格式错误，缺少userId字段: {}", message);
                return;
            }

            // 1. 初始化用户金币账户
            initUserCoinAccount(userId);

            // 2. 如果有邀请人，处理邀请奖励
            if (StrUtil.isNotBlank(inviterUserId)) {
                processInviteReward(inviterUserId, userId);
            }

            log.info("监听用户注册事件，金币账户初始化完成 topic: {} userId: {} inviterUserId: {}", 
                    topic, userId, inviterUserId);
        } catch (Exception e) {
            log.error("监听用户注册事件，消费失败 topic: {} message: {}", topic, message, e);
            throw e;
        }
    }

    /**
     * 初始化用户金币账户
     */
    private void initUserCoinAccount(String userId) {
        try {
            InitAccountCommand command = InitAccountCommand.builder()
                    .userId(userId)
                    .build();
            coinCommandService.initAccount(command);
            log.info("用户金币账户初始化成功，userId: {}", userId);
        } catch (Exception e) {
            log.error("用户金币账户初始化失败，userId: {}", userId, e);
            throw e;
        }
    }

    /**
     * 处理邀请奖励
     */
    private void processInviteReward(String inviterUserId, String newUserId) {
        try {
            ProcessInviteRewardCommand command = ProcessInviteRewardCommand.builder()
                    .inviterUserId(inviterUserId)
                    .newUserId(newUserId)
                    .build();
            coinCommandService.processInviteReward(command);
            log.info("邀请奖励处理成功，inviterUserId: {}, newUserId: {}", inviterUserId, newUserId);
        } catch (Exception e) {
            log.error("邀请奖励处理失败，inviterUserId: {}, newUserId: {}", inviterUserId, newUserId, e);
            throw e;
        }
    }
}
