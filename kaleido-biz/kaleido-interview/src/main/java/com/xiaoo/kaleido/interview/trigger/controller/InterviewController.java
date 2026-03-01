package com.xiaoo.kaleido.interview.trigger.controller;

import com.xiaoo.kaleido.api.interview.command.ScheduleInterviewCommand;
import com.xiaoo.kaleido.api.interview.response.InterviewInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.interview.application.command.InterviewCommandService;
import com.xiaoo.kaleido.interview.application.query.InterviewQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 面试管理控制器
 * 提供面试的增删改查接口
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewCommandService interviewCommandService;
    private final InterviewQueryService interviewQueryService;

    /**
     * 安排面试
     *
     * @param command 安排面试命令
     * @return 面试ID
     */
    @PostMapping
    public Result<String> scheduleInterview(@Valid @RequestBody ScheduleInterviewCommand command) {
        log.info("安排面试，候选人ID: {}", command.getCandidateId());
        String interviewId = interviewCommandService.scheduleInterview(command);
        return Result.success(interviewId);
    }

    /**
     * 根据ID查询面试
     *
     * @param interviewId 面试ID
     * @return 面试信息
     */
    @GetMapping("/{interviewId}")
    public Result<InterviewInfoResponse> getInterview(@PathVariable String interviewId) {
        log.info("查询面试，ID: {}", interviewId);
        InterviewInfoResponse response = interviewQueryService.findById(interviewId);
        return Result.success(response);
    }

    /**
     * 查询所有面试
     *
     * @return 面试列表
     */
    @GetMapping
    public Result<List<InterviewInfoResponse>> listInterviews() {
        log.info("查询所有面试");
        List<InterviewInfoResponse> interviews = interviewQueryService.findAll();
        return Result.success(interviews);
    }

    /**
     * 根据候选人ID查询面试列表
     *
     * @param candidateId 候选人ID
     * @return 面试列表
     */
    @GetMapping("/candidate/{candidateId}")
    public Result<List<InterviewInfoResponse>> listInterviewsByCandidate(@PathVariable String candidateId) {
        log.info("查询候选人的面试，候选人ID: {}", candidateId);
        List<InterviewInfoResponse> interviews = interviewQueryService.findByCandidateId(candidateId);
        return Result.success(interviews);
    }

    /**
     * 根据面试官查询面试列表
     *
     * @param interviewerName 面试官姓名
     * @return 面试列表
     */
    @GetMapping("/interviewer")
    public Result<List<InterviewInfoResponse>> listInterviewsByInterviewer(
            @RequestParam String interviewerName) {
        log.info("查询面试官的面试，面试官: {}", interviewerName);
        List<InterviewInfoResponse> interviews = interviewQueryService.findByInterviewerName(interviewerName);
        return Result.success(interviews);
    }

    /**
     * 根据时间范围查询面试列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 面试列表
     */
    @GetMapping("/time-range")
    public Result<List<InterviewInfoResponse>> listInterviewsByTimeRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime) {
        log.info("查询时间范围内的面试，开始: {}, 结束: {}", startTime, endTime);
        List<InterviewInfoResponse> interviews = interviewQueryService.findByTimeRange(startTime, endTime);
        return Result.success(interviews);
    }

    /**
     * 更新面试时间
     *
     * @param interviewId   面试ID
     * @param interviewTime 新面试时间
     * @return 面试ID
     */
    @PutMapping("/{interviewId}/time")
    public Result<String> updateInterviewTime(
            @PathVariable String interviewId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date interviewTime) {
        log.info("更新面试时间，面试ID: {}", interviewId);
        String updatedId = interviewCommandService.updateInterviewTime(interviewId, interviewTime);
        return Result.success(updatedId);
    }

    /**
     * 取消面试
     *
     * @param interviewId 面试ID
     * @return 成功响应
     */
    @DeleteMapping("/{interviewId}")
    public Result<Void> cancelInterview(@PathVariable String interviewId) {
        log.info("取消面试，面试ID: {}", interviewId);
        interviewCommandService.cancelInterview(interviewId);
        return Result.success();
    }
}
