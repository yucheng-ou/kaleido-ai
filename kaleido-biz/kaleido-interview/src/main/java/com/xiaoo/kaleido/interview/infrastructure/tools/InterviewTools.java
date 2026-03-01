package com.xiaoo.kaleido.interview.infrastructure.tools;

import com.xiaoo.kaleido.interview.domain.candidate.adapter.repository.ICandidateRepository;
import com.xiaoo.kaleido.interview.domain.candidate.model.aggregate.CandidateAggregate;
import com.xiaoo.kaleido.interview.domain.candidate.service.ICandidateDomainService;
import com.xiaoo.kaleido.interview.domain.interview.adapter.repository.IInterviewRepository;
import com.xiaoo.kaleido.interview.domain.interview.model.aggregate.InterviewAggregate;
import com.xiaoo.kaleido.interview.domain.interview.service.IInterviewDomainService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 面试相关工具类
 * <p>
 * 提供面试安排、候选人查询等功能供AI调用
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InterviewTools {

    private final ICandidateDomainService candidateDomainService;
    private final IInterviewDomainService interviewDomainService;
    private final ICandidateRepository candidateRepository;
    private final IInterviewRepository interviewRepository;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 安排面试
     *
     * @param candidateId     候选人ID
     * @param interviewTime   面试时间（格式：yyyy-MM-dd HH:mm）
     * @param interviewerName 面试官姓名
     * @return 操作结果
     */
    @Tool(value = "安排候选人面试，将面试日程写入数据库", name = "scheduleInterview")
    public String scheduleInterview(
            @P("候选人ID") String candidateId,
            @P("面试时间，格式为yyyy-MM-dd HH:mm，例如：2026-03-01 14:00") String interviewTime,
            @P("面试官姓名") String interviewerName) {

        log.info("AI调用安排面试工具，候选人ID: {}, 面试时间: {}, 面试官: {}",
                candidateId, interviewTime, interviewerName);

        try {
            // 解析时间
            Date time = DATE_FORMAT.parse(interviewTime);

            // 创建面试
            InterviewAggregate interview = interviewDomainService.createInterview(
                    candidateId, time, interviewerName);

            // 保存面试到数据库
            interviewRepository.save(interview);
            log.info("面试已保存到数据库，面试ID: {}", interview.getId());

            return String.format("面试安排成功！面试ID: %s，候选人ID: %s，面试时间: %s，面试官: %s",
                    interview.getId(), candidateId, interviewTime, interviewerName);
        } catch (Exception e) {
            log.error("安排面试失败: {}", e.getMessage(), e);
            return "安排面试失败: " + e.getMessage();
        }
    }

    /**
     * 根据姓名安排面试
     *
     * @param candidateName   候选人姓名
     * @param interviewTime   面试时间（格式：yyyy-MM-dd HH:mm）
     * @param interviewerName 面试官姓名
     * @return 操作结果
     */
    @Tool(value = "根据候选人姓名安排面试，无需ID", name = "scheduleInterviewByName")
    public String scheduleInterviewByName(
            @P("候选人姓名") String candidateName,
            @P("面试时间，格式为yyyy-MM-dd HH:mm，例如：2026-03-01 14:00") String interviewTime,
            @P("面试官姓名") String interviewerName) {

        log.info("AI调用根据姓名安排面试工具，候选人姓名: {}, 面试时间: {}, 面试官: {}",
                candidateName, interviewTime, interviewerName);

        try {
            List<CandidateAggregate> candidates = candidateDomainService.findByName(candidateName);

            if (candidates == null || candidates.isEmpty()) {
                return "未找到名为 " + candidateName + " 的候选人。";
            }

            if (candidates.size() > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("找到多名名为 ").append(candidateName).append(" 的候选人，请使用ID进行操作：\n");
                for (CandidateAggregate candidate : candidates) {
                    sb.append(String.format("- ID: %s, 状态: %s\n", candidate.getId(), candidate.getStatus().getDescription()));
                }
                return sb.toString();
            }

            String candidateId = candidates.get(0).getId();
            log.info("根据姓名 {} 找到唯一候选人ID: {}，即将安排面试", candidateName, candidateId);

            return scheduleInterview(candidateId, interviewTime, interviewerName);
        } catch (Exception e) {
            log.error("根据姓名安排面试失败: {}", e.getMessage(), e);
            return "根据姓名安排面试失败: " + e.getMessage();
        }
    }

    /**
     * 查询候选人信息
     *
     * @param candidateId 候选人ID
     * @return 候选人信息
     */
    @Tool(value = "根据候选人ID查询候选人详细信息", name = "queryCandidate")
    public String queryCandidate(@P("候选人ID") String candidateId) {
        log.info("AI调用查询候选人工具，候选人ID: {}", candidateId);

        try {
            CandidateAggregate candidate = candidateDomainService.findByIdOrThrow(candidateId);

            return String.format("候选人信息：姓名: %s，技能: %s，工作年限: %d年，状态: %s",
                    candidate.getName(),
                    candidate.getSkills() != null ? candidate.getSkills() : "未填写",
                    candidate.getExperienceYears() != null ? candidate.getExperienceYears() : 0,
                    candidate.getStatus().getDescription());
        } catch (Exception e) {
            log.error("查询候选人失败: {}", e.getMessage(), e);
            return "查询候选人失败: " + e.getMessage();
        }
    }

    /**
     * 更新候选人状态为面试中
     *
     * @param candidateId 候选人ID
     * @return 操作结果
     */
    @Tool(value = "将候选人状态更新为面试中", name = "startInterviewStatus")
    public String startInterviewStatus(@P("候选人ID") String candidateId) {
        log.info("AI调用开始面试状态工具，候选人ID: {}", candidateId);

        try {
            CandidateAggregate candidate = candidateDomainService.startInterview(candidateId);
            
            // 保存候选人状态更新到数据库
            candidateRepository.update(candidate);
            log.info("候选人状态已更新并保存到数据库，候选人ID: {}, 新状态: {}", 
                     candidateId, candidate.getStatus().getDescription());

            return String.format("候选人 %s 状态已更新为面试中", candidate.getName());
        } catch (Exception e) {
            log.error("更新候选人状态失败: {}", e.getMessage(), e);
            return "更新候选人状态失败: " + e.getMessage();
        }
    }

    /**
     * 根据姓名将候选人状态更新为面试中
     *
     * @param candidateName 候选人姓名
     * @return 操作结果
     */
    @Tool(value = "根据候选人姓名将状态更新为面试中", name = "startInterviewStatusByName")
    public String startInterviewStatusByName(@P("候选人姓名") String candidateName) {
        log.info("AI调用根据姓名开始面试状态工具，候选人姓名: {}", candidateName);

        try {
            List<CandidateAggregate> candidates = candidateDomainService.findByName(candidateName);

            if (candidates == null || candidates.isEmpty()) {
                return "未找到名为 " + candidateName + " 的候选人。";
            }

            if (candidates.size() > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("找到多名名为 ").append(candidateName).append(" 的候选人，请使用ID进行操作：\n");
                for (CandidateAggregate candidate : candidates) {
                    sb.append(String.format("- ID: %s, 状态: %s\n", candidate.getId(), candidate.getStatus().getDescription()));
                }
                return sb.toString();
            }

            String candidateId = candidates.get(0).getId();
            log.info("根据姓名 {} 找到唯一候选人ID: {}，即将更新状态为面试中", candidateName, candidateId);

            return startInterviewStatus(candidateId);
        } catch (Exception e) {
            log.error("根据姓名更新候选人状态失败: {}", e.getMessage(), e);
            return "根据姓名更新候选人状态失败: " + e.getMessage();
        }
    }

    /**
     * 录用候选人
     *
     * @param candidateId 候选人ID
     * @return 操作结果
     */
    @Tool(value = "将候选人状态更新为已录用", name = "hireCandidate")
    public String hireCandidate(@P("候选人ID") String candidateId) {
        log.info("AI调用录用候选人工具，候选人ID: {}", candidateId);

        try {
            CandidateAggregate candidate = candidateDomainService.hireCandidate(candidateId);
            
            // 保存候选人录用状态到数据库
            candidateRepository.update(candidate);
            log.info("候选人录用状态已保存到数据库，候选人ID: {}, 新状态: {}", 
                     candidateId, candidate.getStatus().getDescription());

            return String.format("候选人 %s 已成功录用！", candidate.getName());
        } catch (Exception e) {
            log.error("录用候选人失败: {}", e.getMessage(), e);
            return "录用候选人失败: " + e.getMessage();
        }
    }

    /**
     * 根据姓名搜索候选人
     *
     * @param name 候选人姓名
     * @return 候选人信息列表
     */
    @Tool(value = "根据姓名搜索候选人，返回候选人ID和详细信息", name = "searchCandidateByName")
    public String searchCandidateByName(@P("候选人姓名") String name) {
        log.info("AI调用搜索候选人工具，姓名: {}", name);

        try {
            List<CandidateAggregate> candidates = candidateDomainService.findByName(name);
            
            if (candidates == null || candidates.isEmpty()) {
                return "未找到名为 " + name + " 的候选人。";
            }

            StringBuilder sb = new StringBuilder();
            sb.append("找到以下候选人：\n");
            for (CandidateAggregate candidate : candidates) {
                sb.append(String.format("- ID: %s, 姓名: %s, 技能: %s, 经验: %d年, 状态: %s\n",
                        candidate.getId(),
                        candidate.getName(),
                        candidate.getSkills() != null ? candidate.getSkills() : "未填写",
                        candidate.getExperienceYears() != null ? candidate.getExperienceYears() : 0,
                        candidate.getStatus().getDescription()));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("搜索候选人失败: {}", e.getMessage(), e);
            return "搜索候选人失败: " + e.getMessage();
        }
    }
}
