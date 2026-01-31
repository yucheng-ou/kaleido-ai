package com.xiaoo.kaleido.ai.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.ai.infrastructure.dao.po.WorkflowExecutionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 工作流执行数据访问接口
 * <p>
 * 负责工作流执行表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Mapper
public interface WorkflowExecutionDao extends BaseMapper<WorkflowExecutionPO> {

    /**
     * 根据执行ID查询工作流执行记录
     *
     * @param executionId 执行ID
     * @return 工作流执行持久化对象
     */
    WorkflowExecutionPO findByExecutionId(@Param("executionId") String executionId);

    /**
     * 根据工作流ID查询执行记录
     *
     * @param workflowId 工作流ID
     * @return 工作流执行持久化对象列表
     */
    List<WorkflowExecutionPO> findByWorkflowId(@Param("workflowId") String workflowId);

    /**
     * 根据状态查询工作流执行记录
     *
     * @param status 执行状态
     * @return 工作流执行持久化对象列表
     */
    List<WorkflowExecutionPO> findByStatus(@Param("status") String status);

    /**
     * 查找超时的工作流执行记录
     *
     * @param timeoutMillis 超时时间（毫秒）
     * @param currentTime   当前时间
     * @return 超时的工作流执行持久化对象列表
     */
    List<WorkflowExecutionPO> findTimeoutExecutions(@Param("timeoutMillis") long timeoutMillis, @Param("currentTime") Date currentTime);

    /**
     * 删除超时的工作流执行记录
     *
     * @param timeoutMillis 超时时间（毫秒）
     * @param currentTime   当前时间
     * @return 删除的执行记录数量
     */
    int deleteTimeoutExecutions(@Param("timeoutMillis") long timeoutMillis, @Param("currentTime") Date currentTime);
}
