package com.xiaoo.kaleido.message.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.message.infrastructure.dao.po.MqMessagePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MQ消息数据访问接口
 * <p>
 * 负责t_mq_message表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
@Mapper
public interface MqMessageDao extends BaseMapper<MqMessagePO> {

    /**
     * 根据ID查询消息
     *
     * @param id 消息ID
     * @return 消息持久化对象
     */
    MqMessagePO findById(@Param("id") String id);

    /**
     * 根据用户ID查询消息列表
     *
     * @param userId 用户ID
     * @return 消息持久化对象列表
     */
    List<MqMessagePO> findByUserId(@Param("userId") String userId);

    /**
     * 根据主题查询消息列表
     *
     * @param topic 消息主题
     * @return 消息持久化对象列表
     */
    List<MqMessagePO> findByTopic(@Param("topic") String topic);

    /**
     * 根据状态查询消息列表
     *
     * @param state 消息状态
     * @return 消息持久化对象列表
     */
    List<MqMessagePO> findByState(@Param("state") String state);

    /**
     * 检查消息是否存在
     *
     * @param id 消息ID
     * @return 是否存在，true表示存在，false表示不存在
     */
    boolean exists(@Param("id") String id);

    /**
     * 查询处理中的消息（创建状态）
     *
     * @return 消息持久化对象列表
     */
    List<MqMessagePO> findProcessingMessages();

    /**
     * 查询终态的消息（已完成或失败）
     *
     * @return 消息持久化对象列表
     */
    List<MqMessagePO> findFinalStateMessages();
}
