package com.xiaoo.kaleido.interview.domain.candidate.model.aggregate;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.interview.domain.candidate.model.vo.CandidateStatus;
import com.xiaoo.kaleido.interview.types.exception.InterviewErrorCode;
import com.xiaoo.kaleido.interview.types.exception.InterviewException;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 候选人聚合根
 * <p>
 * 候选人领域模型的核心聚合根，封装候选人的基本信息和状态管理
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CandidateAggregate extends BaseEntity {

    /**
     * 候选人姓名
     */
    private String name;

    /**
     * 技能列表（逗号分隔）
     */
    private String skills;

    /**
     * 工作年限
     */
    private Integer experienceYears;

    /**
     * 原始简历文本（用于RAG）
     */
    private String rawResumeText;

    /**
     * 候选人状态
     */
    private CandidateStatus status;

    /**
     * 创建新候选人聚合根
     * <p>
     * 用于创建新候选人时构建聚合根
     *
     * @param name             候选人姓名，不能为空
     * @param skills           技能列表，可为空
     * @param experienceYears  工作年限，可为空
     * @param rawResumeText    原始简历文本，可为空
     * @return 候选人聚合根
     * @throws InterviewException 当参数无效时抛出
     */
    public static CandidateAggregate create(
            String name,
            String skills,
            Integer experienceYears,
            String rawResumeText) {

        if (StrUtil.isBlank(name)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人姓名不能为空");
        }

        return CandidateAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .name(name.trim())
                .skills(skills != null ? skills.trim() : null)
                .experienceYears(experienceYears)
                .rawResumeText(rawResumeText)
                .status(CandidateStatus.NEW)
                .build();
    }

    /**
     * 更新候选人信息
     * <p>
     * 更新候选人的基本信息，如果参数为空则不更新对应字段
     *
     * @param name             新候选人姓名，不能为空
     * @param skills           新技能列表，可为空（如果为空则不更新）
     * @param experienceYears  新工作年限，可为空（如果为空则不更新）
     * @param rawResumeText    新原始简历文本，可为空（如果为空则不更新）
     * @throws InterviewException 当参数无效时抛出
     */
    public void updateInfo(
            String name,
            String skills,
            Integer experienceYears,
            String rawResumeText) {

        if (StrUtil.isBlank(name)) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, "候选人姓名不能为空");
        }

        this.name = name.trim();

        // 只有参数不为空时才更新对应字段
        if (StrUtil.isNotBlank(skills)) {
            this.skills = skills.trim();
        }
        if (experienceYears != null) {
            this.experienceYears = experienceYears;
        }
        if (StrUtil.isNotBlank(rawResumeText)) {
            this.rawResumeText = rawResumeText;
        }
    }

    /**
     * 更新技能列表
     *
     * @param skills 技能列表，可为空
     */
    public void updateSkills(String skills) {
        this.skills = skills != null ? skills.trim() : null;
    }

    /**
     * 更新原始简历文本
     *
     * @param rawResumeText 原始简历文本，可为空
     */
    public void updateRawResumeText(String rawResumeText) {
        this.rawResumeText = rawResumeText;
    }

    /**
     * 开始面试
     * <p>
     * 将候选人状态设置为 INTERVIEWING
     *
     * @throws InterviewException 如果当前状态不允许开始面试
     */
    public void startInterview() {
        if (!this.status.isNew()) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, 
                    "只有新候选人才能开始面试，当前状态: " + this.status.getDescription());
        }
        this.status = CandidateStatus.INTERVIEWING;
    }

    /**
     * 录用候选人
     * <p>
     * 将候选人状态设置为 HIRED
     *
     * @throws InterviewException 如果当前状态不允许录用
     */
    public void hire() {
        if (!this.status.isInterviewing()) {
            throw InterviewException.of(InterviewErrorCode.VALIDATION_ERROR, 
                    "只有面试中的候选人才能被录用，当前状态: " + this.status.getDescription());
        }
        this.status = CandidateStatus.HIRED;
    }

    /**
     * 检查是否可以安排面试
     *
     * @return 如果可以安排面试返回true，否则返回false
     */
    public boolean canScheduleInterview() {
        return this.status.isNew() || this.status.isInterviewing();
    }
}
