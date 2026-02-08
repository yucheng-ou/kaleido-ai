package com.xiaoo.kaleido.message.domain.mq.model.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.message.domain.mq.model.vo.MessageState;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * MQ消息聚合根
 * <p>
 * MQ消息领域模型的核心聚合根，封装消息实体及其业务规则，确保业务完整性
 * 遵循聚合根设计原则：包含最核心的业务逻辑，不包含参数校验
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MqMessageAggregate extends BaseEntity {

    /**
     * 用户ID
     * 消息所属的用户
     */
    private String userId;

    /**
     * 消息主体
     * 消息的具体内容
     */
    private String message;

    /**
     * 消息主题
     * 消息所属的主题
     */
    private String topic;

    /**
     * 消息状态
     */
    private MessageState state;

    /**
     * 创建新消息聚合根
     * <p>
     * 用于创建新消息时构建聚合根
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param userId  用户ID，不能为空
     * @param message 消息主体，不能为空
     * @param topic   消息主题，不能为空
     * @return 消息聚合根
     */
    public static MqMessageAggregate create(String userId, String message, String topic) {
        return MqMessageAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .message(message)
                .topic(topic)
                .state(MessageState.CREATE) // 默认状态为创建
                .build();
    }

    /**
     * 更新消息状态
     *
     * @param state 新的状态
     */
    public void updateState(MessageState state) {
        this.state = state;
    }

    /**
     * 标记为完成状态
     */
    public void markAsCompleted() {
        this.state = MessageState.COMPLETED;
    }

    /**
     * 标记为失败状态
     */
    public void markAsFailed() {
        this.state = MessageState.FAIL;
    }

    /**
     * 判断是否为终态（已完成或失败）
     *
     * @return 是否为终态
     */
    public boolean isFinalState() {
        return state != null && state.isFinalState();
    }

    /**
     * 判断是否为处理中状态（创建状态）
     *
     * @return 是否为处理中状态
     */
    public boolean isProcessing() {
        return state != null && state.isProcessing();
    }

    /**
     * 判断是否为创建状态
     *
     * @return 是否为创建状态
     */
    public boolean isCreate() {
        return state != null && state.isCreate();
    }

    /**
     * 判断是否为完成状态
     *
     * @return 是否为完成状态
     */
    public boolean isCompleted() {
        return state != null && state.isCompleted();
    }

    /**
     * 判断是否为失败状态
     *
     * @return 是否为失败状态
     */
    public boolean isFail() {
        return state != null && state.isFail();
    }

    /**
     * 获取消息长度
     *
     * @return 消息长度
     */
    public int getMessageLength() {
        return message != null ? message.length() : 0;
    }

    /**
     * 更新消息内容
     *
     * @param newMessage 新的消息内容
     */
    public void updateMessage(String newMessage) {
        this.message = newMessage;
    }

    /**
     * 更新主题
     *
     * @param newTopic 新的主题
     */
    public void updateTopic(String newTopic) {
        this.topic = newTopic;
    }
}
