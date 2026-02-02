package com.xiaoo.kaleido.ai.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.AgentPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Agent数据访问接口
 * <p>
 * 负责Agent表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
public interface AgentDao extends BaseMapper<AgentPO> {

    /**
     * 根据ID查询Agent
     *
     * @param id Agent ID
     * @return Agent持久化对象
     */
    AgentPO findById(@Param("id") String id);

    /**
     * 根据编码查询Agent
     *
     * @param code Agent编码
     * @return Agent持久化对象
     */
    AgentPO findByCode(@Param("code") String code);

    /**
     * 检查Agent编码是否唯一
     *
     * @param code Agent编码
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
     */
    boolean existsByCode(@Param("code") String code);

    /**
     * 查找所有启用的Agent
     *
     * @return Agent持久化对象列表
     */
    List<AgentPO> findAllEnabled();

    /**
     * 根据工具类型查找Agent
     *
     * @param toolType 工具类型
     * @return Agent持久化对象列表
     */
    List<AgentPO> findByToolType(@Param("toolType") String toolType);

    /**
     * 查询所有未被删除的Agent
     *
     * @return Agent持久化对象列表
     */
    List<AgentPO> findAllNotDeleted();
}
