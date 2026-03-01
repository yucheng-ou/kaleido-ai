package com.xiaoo.kaleido.interview.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 候选人持久化对象
 * <p>
 * 对应数据库表：t_candidates
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_candidates")
public class CandidatePO extends BasePO {

    /**
     * 候选人姓名
     */
    @TableField("name")
    private String name;

    /**
     * 技能列表（逗号分隔）
     */
    @TableField("skills")
    private String skills;

    /**
     * 工作年限
     */
    @TableField("experience_years")
    private Integer experienceYears;

    /**
     * 原始简历文本（用于RAG）
     */
    @TableField("raw_resume_text")
    private String rawResumeText;

    /**
     * 候选人状态：NEW-新建，INTERVIEWING-面试中，HIRED-已录用
     */
    @TableField("status")
    private String status;
}
