package com.xiaoo.kaleido.interview.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 面试持久化对象
 * <p>
 * 对应数据库表：t_interviews
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_interviews")
public class InterviewPO extends BasePO {

    /**
     * 候选人ID
     */
    @TableField("candidate_id")
    private String candidateId;

    /**
     * 面试时间
     */
    @TableField("interview_time")
    private Date interviewTime;

    /**
     * 面试官姓名
     */
    @TableField("interviewer_name")
    private String interviewerName;
}
