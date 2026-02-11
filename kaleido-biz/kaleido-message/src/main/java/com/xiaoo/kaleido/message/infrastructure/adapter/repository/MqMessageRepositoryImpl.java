package com.xiaoo.kaleido.message.infrastructure.adapter.repository;

import com.xiaoo.kaleido.message.domain.mq.adapter.repository.IMqMessageRepository;
import com.xiaoo.kaleido.message.domain.mq.model.aggregate.MqMessageAggregate;
import com.xiaoo.kaleido.message.infrastructure.adapter.repository.convertor.MqMessageInfraConvertor;
import com.xiaoo.kaleido.message.infrastructure.dao.MqMessageDao;
import com.xiaoo.kaleido.message.infrastructure.dao.po.MqMessagePO;
import com.xiaoo.kaleido.message.types.exception.MessageException;
import com.xiaoo.kaleido.message.types.exception.MessageErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MQ消息仓储实现（基础设施层）
 * <p>
 * MQ消息仓储接口的具体实现，负责MQ消息聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class MqMessageRepositoryImpl implements IMqMessageRepository {

    private final MqMessageDao mqMessageDao;

    @Override
    public void save(MqMessageAggregate messageAggregate) {
        try {
            // 1.转换MqMessageAggregate为MqMessagePO
            MqMessagePO messagePO = MqMessageInfraConvertor.INSTANCE.toPO(messageAggregate);

            // 2.保存消息基本信息
            mqMessageDao.insert(messagePO);

            log.info("MQ消息保存成功，消息ID: {}, 用户ID: {}, 主题: {}",
                    messageAggregate.getId(), messageAggregate.getUserId(), messageAggregate.getTopic());
        } catch (Exception e) {
            log.error("保存MQ消息失败，消息ID: {}, 原因: {}", messageAggregate.getId(), e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.OPERATE_FAILED, "保存MQ消息失败");
        }
    }

    @Override
    public void update(MqMessageAggregate messageAggregate) {
        try {
            // 1.转换MqMessageAggregate为MqMessagePO
            MqMessagePO messagePO = MqMessageInfraConvertor.INSTANCE.toPO(messageAggregate);

            // 2.更新消息基本信息
            mqMessageDao.updateById(messagePO);

            log.info("MQ消息更新成功，消息ID: {}, 用户ID: {}, 主题: {}",
                    messageAggregate.getId(), messageAggregate.getUserId(), messageAggregate.getTopic());
        } catch (Exception e) {
            log.error("更新MQ消息失败，消息ID: {}, 原因: {}", messageAggregate.getId(), e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.OPERATE_FAILED, "更新MQ消息失败");
        }
    }

    @Override
    public MqMessageAggregate findById(String messageId) {
        try {
            // 1.查询消息基本信息
            MqMessagePO messagePO = mqMessageDao.findById(messageId);
            if (messagePO == null) {
                return null;
            }

            // 2.转换为MqMessageAggregate
            return MqMessageInfraConvertor.INSTANCE.toAggregate(messagePO);
        } catch (Exception e) {
            log.error("查询MQ消息失败，消息ID: {}, 原因: {}", messageId, e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.QUERY_FAIL, "查询MQ消息失败");
        }
    }

    @Override
    public MqMessageAggregate findByIdOrThrow(String messageId) {
        MqMessageAggregate message = findById(messageId);
        if (message == null) {
            throw MessageException.of(MessageErrorCode.DATA_NOT_FOUND, "MQ消息不存在，消息ID: " + messageId);
        }
        return message;
    }

    @Override
    public List<MqMessageAggregate> findByUserId(String userId) {
        try {
            // 1.查询消息列表
            List<MqMessagePO> messagePOs = mqMessageDao.findByUserId(userId);

            // 2.转换为MqMessageAggregate列表
            return messagePOs.stream()
                    .map(MqMessageInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("根据用户ID查询MQ消息失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.QUERY_FAIL, "根据用户ID查询MQ消息失败");
        }
    }

    @Override
    public List<MqMessageAggregate> findByTopic(String topic) {
        try {
            // 1.查询消息列表
            List<MqMessagePO> messagePOs = mqMessageDao.findByTopic(topic);

            // 2.转换为MqMessageAggregate列表
            return messagePOs.stream()
                    .map(MqMessageInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("根据主题查询MQ消息失败，主题: {}, 原因: {}", topic, e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.QUERY_FAIL, "根据主题查询MQ消息失败");
        }
    }

    @Override
    public List<MqMessageAggregate> findByUserIdAndTopic(String userId, String topic) {
        try {
            // 1.查询消息列表
            List<MqMessagePO> messagePOs = mqMessageDao.findByUserIdAndTopic(userId, topic);

            // 2.转换为MqMessageAggregate列表
            return messagePOs.stream()
                    .map(MqMessageInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("根据用户ID和主题查询MQ消息失败，用户ID: {}, 主题: {}, 原因: {}", userId, topic, e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.QUERY_FAIL, "根据用户ID和主题查询MQ消息失败");
        }
    }

    @Override
    public List<MqMessageAggregate> findByState(String state) {
        try {
            // 1.查询消息列表
            List<MqMessagePO> messagePOs = mqMessageDao.findByState(state);

            // 2.转换为MqMessageAggregate列表
            return messagePOs.stream()
                    .map(MqMessageInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("根据状态查询MQ消息失败，状态: {}, 原因: {}", state, e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.QUERY_FAIL, "根据状态查询MQ消息失败");
        }
    }

    @Override
    public boolean exists(String messageId) {
        try {
            return mqMessageDao.exists(messageId);
        } catch (Exception e) {
            log.error("检查MQ消息是否存在失败，消息ID: {}, 原因: {}", messageId, e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.QUERY_FAIL, "检查MQ消息是否存在失败");
        }
    }

    @Override
    public List<MqMessageAggregate> findAllNotDeleted() {
        try {
            // 1.查询所有未被删除的消息
            List<MqMessagePO> messagePOs = mqMessageDao.findAllNotDeleted();

            // 2.转换为MqMessageAggregate列表
            return messagePOs.stream()
                    .map(MqMessageInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询所有未被删除的MQ消息失败，原因: {}", e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.QUERY_FAIL, "查询所有未被删除的MQ消息失败");
        }
    }

    @Override
    public List<MqMessageAggregate> findProcessingMessages() {
        try {
            // 1.查询处理中的消息
            List<MqMessagePO> messagePOs = mqMessageDao.findProcessingMessages();

            // 2.转换为MqMessageAggregate列表
            return messagePOs.stream()
                    .map(MqMessageInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询处理中的MQ消息失败，原因: {}", e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.QUERY_FAIL, "查询处理中的MQ消息失败");
        }
    }

    @Override
    public List<MqMessageAggregate> findFinalStateMessages() {
        try {
            // 1.查询终态的消息
            List<MqMessagePO> messagePOs = mqMessageDao.findFinalStateMessages();

            // 2.转换为MqMessageAggregate列表
            return messagePOs.stream()
                    .map(MqMessageInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询终态的MQ消息失败，原因: {}", e.getMessage(), e);
            throw MessageException.of(MessageErrorCode.QUERY_FAIL, "查询终态的MQ消息失败");
        }
    }
}
