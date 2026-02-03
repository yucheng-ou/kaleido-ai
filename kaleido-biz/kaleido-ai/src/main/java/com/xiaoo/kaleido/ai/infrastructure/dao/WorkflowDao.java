package com.xiaoo.kaleido.ai.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.WorkflowPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作流数据访问接口
 * <p>
 * 负责工作流表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper
public interface WorkflowDao extends BaseMapper<WorkflowPO> {

    /**
     * 根据ID查询工作流
     *
     * @param id 工作流ID
     * @return 工作流持久化对象
     */
    WorkflowPO findById(@Param("id") String id);

    /**
     * 根据编码查询工作流
     *
     * @param code 工作流编码
     * @return 工作流持久化对象
     */
    WorkflowPO findByCode(@Param("code") String code);

    /**
     * 检查工作流编码是否唯一
     *
     * @param code 工作流编码
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
     */
    boolean existsByCode(@Param("code") String code);
}
