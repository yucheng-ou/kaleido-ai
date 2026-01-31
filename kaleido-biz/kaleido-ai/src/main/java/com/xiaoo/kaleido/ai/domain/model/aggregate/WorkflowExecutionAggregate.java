package com.xiaoo.kaleido.ai.domain.model.aggregate;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.ai.domain.model.valobj.ExecutionStatus;
import com.xiaoo.kaleido.ai.types.exception.AiException;
import com.xiaoo.kaleido.ai.types.exception.AiErrorCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 工作流执行聚合根
 * <p>
 * 工作流执行领域模型的核心聚合根，封装工作流执行记录及其状态信息
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WorkflowExecutionAggregate extends BaseEntity {

    /**
     * 执行ID（业务唯一）
     */
    private String executionId;

    /**
     * 工作流ID
     */
    private String workflowId;

    /**
     * 执行状态
     */
    private ExecutionStatus status;

    /**
     * 输入数据（JSON格式）
     */
    private String inputData;

    /**
     * 输出数据（JSON格式）
     */
    private String outputData;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 开始时间
     */
    private Date startedAt;

    /**
     * 完成时间
     */
    private Date completedAt;

    /**
     * 创建新工作流执行聚合根
     * <p>
     * 用于创建工作流执行记录时构建聚合根
     *
     * @param executionId 执行ID（业务唯一），不能为空
     * @param workflowId  工作流ID，不能为空
     * @param inputData   输入数据，可为空
     * @return 工作流执行聚合根
     * @throws IllegalArgumentException 当参数无效时抛出
     */
    public static WorkflowExecutionAggregate create(
            String executionId,
            String workflowId,
            String inputData) {
        
        if (StrUtil.isBlank(executionId)) {
            throw AiException.of(AiErrorCode.EXECUTION_ID_NOT_NULL, "执行ID不能为空");
        }
        if (StrUtil.isBlank(workflowId)) {
            throw AiException.of(AiErrorCode.WORKFLOW_ID_NOT_NULL, "工作流ID不能为空");
        }

        return WorkflowExecutionAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .executionId(executionId.trim())
                .workflowId(workflowId.trim())
                .status(ExecutionStatus.RUNNING)
                .inputData(inputData)
                .startedAt(new Date())
                .build();
    }

    /**
     * 开始执行
     * <p>
     * 设置执行状态为RUNNING，记录开始时间
     */
    public void start() {
        this.status = ExecutionStatus.RUNNING;
        this.startedAt = new Date();
    }

    /**
     * 执行成功
     * <p>
     * 设置执行状态为SUCCESS，记录输出数据和完成时间
     *
     * @param outputData 输出数据，可为空
     */
    public void succeed(String outputData) {
        this.status = ExecutionStatus.SUCCESS;
        this.outputData = outputData;
        this.completedAt = new Date();
        this.errorMessage = null;
    }

    /**
     * 执行失败
     * <p>
     * 设置执行状态为FAILED，记录错误信息和完成时间
     *
     * @param errorMessage 错误信息，不能为空
     */
    public void fail(String errorMessage) {
        if (StrUtil.isBlank(errorMessage)) {
            throw AiException.of(AiErrorCode.VALIDATION_ERROR, "错误信息不能为空");
        }

        this.status = ExecutionStatus.FAILED;
        this.errorMessage = errorMessage.trim();
        this.completedAt = new Date();
    }

    /**
     * 更新执行进度
     * <p>
     * 更新执行状态和输出数据（用于增量输出）
     *
     * @param outputData 输出数据，可为空
     */
    public void updateProgress(String outputData) {
        this.outputData = outputData;
    }

    /**
     * 检查执行是否完成
     *
     * @return 如果执行状态为SUCCESS或FAILED返回true，否则返回false
     */
    public boolean isCompleted() {
        return ExecutionStatus.SUCCESS.equals(this.status) || ExecutionStatus.FAILED.equals(this.status);
    }

    /**
     * 检查执行是否成功
     *
     * @return 如果执行状态为SUCCESS返回true，否则返回false
     */
    public boolean isSuccess() {
        return ExecutionStatus.SUCCESS.equals(this.status);
    }

    /**
     * 检查执行是否失败
     *
     * @return 如果执行状态为FAILED返回true，否则返回false
     */
    public boolean isFailed() {
        return ExecutionStatus.FAILED.equals(this.status);
    }

    /**
     * 检查执行是否正在运行
     *
     * @return 如果执行状态为RUNNING返回true，否则返回false
     */
    public boolean isRunning() {
        return ExecutionStatus.RUNNING.equals(this.status);
    }

    /**
     * 获取执行时长（毫秒）
     *
     * @return 执行时长（毫秒），如果未开始或未完成返回0
     */
    public long getDurationMillis() {
        if (this.startedAt == null) {
            return 0;
        }

        Date endTime = this.completedAt != null ? this.completedAt : new Date();
        return endTime.getTime() - this.startedAt.getTime();
    }

    /**
     * 获取执行时长（秒）
     *
     * @return 执行时长（秒），如果未开始或未完成返回0
     */
    public long getDurationSeconds() {
        return getDurationMillis() / 1000;
    }

    /**
     * 检查执行是否超时
     *
     * @param timeoutMillis 超时时间（毫秒）
     * @return 如果执行时长超过超时时间返回true，否则返回false
     */
    public boolean isTimeout(long timeoutMillis) {
        if (!isRunning()) {
            return false;
        }

        return getDurationMillis() > timeoutMillis;
    }
}
