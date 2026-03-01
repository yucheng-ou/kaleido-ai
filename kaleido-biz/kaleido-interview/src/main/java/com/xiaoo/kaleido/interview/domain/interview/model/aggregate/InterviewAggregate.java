package com.xiaoo.kaleido.interview.domain.interview.model.aggregate;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.interview.types.exception.InterviewErrorCode;
import com.xiaoo.kaleido.interview.types.exception.InterviewException;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 面试聚合根
 * <p>
 * 面试领域模型的核心聚合根，封装面试的基本信息和面试官管理
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class InterviewAggregate extends BaseEntity {

    /**
     * 候选人ID
     */
    private String candidateId;

    /**
     * 面试时间
     */
    private Date interviewTime;

    /**
     * 面试官姓名
     */
    private String interviewerName;

    /**
     * 创建新面试聚合根
     * <p>
     * 用于创建新面试时构建聚合根
     *
     * @param candidateId     候选人ID，不能为空
     * @param interviewTime   面试时间，不能为空
     * @param interviewerName 面试官姓名，不能为空
     * @return 面试聚合根
     * @throws InterviewException 当参数无效时抛出
     */
    public static InterviewAggregate create(
            String candidateId,
            Date interviewTime,
            String interviewerName) {

        if (StrUtil.isBlank(candidateId)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人ID不能为空");
        }
        if (interviewTime == null) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "面试时间不能为空");
        }
        if (StrUtil.isBlank(interviewerName)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "面试官姓名不能为空");
        }

        return InterviewAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .candidateId(candidateId.trim())
                .interviewTime(interviewTime)
                .interviewerName(interviewerName.trim())
                .build();
    }

    /**
     * 更新面试信息
     * <p>
     * 更新面试的时间和面试官信息，如果参数为空则不更新对应字段
     *
     * @param interviewTime   新面试时间，可为空（如果为空则不更新）
     * @param interviewerName 新面试官姓名，可为空（如果为空则不更新）
     */
    public void updateInfo(Date interviewTime, String interviewerName) {
        if (interviewTime != null) {
            this.interviewTime = interviewTime;
        }
        if (StrUtil.isNotBlank(interviewerName)) {
            this.interviewerName = interviewerName.trim();
        }
    }

    /**
     * 更新面试时间
     *
     * @param interviewTime 新面试时间，不能为空
     * @throws InterviewException 当参数无效时抛出
     */
    public void updateInterviewTime(Date interviewTime) {
        if (interviewTime == null) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "面试时间不能为空");
        }
        this.interviewTime = interviewTime;
    }

    /**
     * 更新面试官
     *
     * @param interviewerName 新面试官姓名，不能为空
     * @throws InterviewException 当参数无效时抛出
     */
    public void updateInterviewer(String interviewerName) {
        if (StrUtil.isBlank(interviewerName)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "面试官姓名不能为空");
        }
        this.interviewerName = interviewerName.trim();
    }

    /**
     * 检查面试是否已完成
     * <p>
     * 根据面试时间判断面试是否已经完成（面试时间早于当前时间）
     *
     * @return 如果面试已完成返回true，否则返回false
     */
    public boolean isCompleted() {
        if (interviewTime == null) {
            return false;
        }
        return interviewTime.before(new Date());
    }

    /**
     * 检查面试是否即将到来
     * <p>
     * 判断面试是否在未来24小时内
     *
     * @return 如果面试即将到来返回true，否则返回false
     */
    public boolean isUpcoming() {
        if (interviewTime == null) {
            return false;
        }
        Date now = new Date();
        long diffMillis = interviewTime.getTime() - now.getTime();
        long hours = diffMillis / (1000 * 60 * 60);
        return hours > 0 && hours <= 24;
    }
}
