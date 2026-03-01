package com.xiaoo.kaleido.interview.trigger.controller;

import com.xiaoo.kaleido.api.interview.command.CreateCandidateCommand;
import com.xiaoo.kaleido.api.interview.response.CandidateInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.interview.application.command.CandidateCommandService;
import com.xiaoo.kaleido.interview.application.query.CandidateQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 候选人管理控制器
 * 提供候选人的增删改查接口
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateCommandService candidateCommandService;
    private final CandidateQueryService candidateQueryService;


    /**
     * 根据ID查询候选人
     *
     * @param candidateId 候选人ID
     * @return 候选人信息
     */
    @GetMapping("/{candidateId}")
    public Result<CandidateInfoResponse> getCandidate(@PathVariable String candidateId) {
        log.info("查询候选人，ID: {}", candidateId);
        CandidateInfoResponse response = candidateQueryService.findById(candidateId);
        return Result.success(response);
    }

    /**
     * 查询所有候选人
     *
     * @return 候选人列表
     */
    @GetMapping
    public Result<List<CandidateInfoResponse>> listCandidates() {
        log.info("查询所有候选人");
        List<CandidateInfoResponse> candidates = candidateQueryService.findAll();
        return Result.success(candidates);
    }

    /**
     * 根据状态查询候选人
     *
     * @param status 候选人状态
     * @return 候选人列表
     */
    @GetMapping("/status/{status}")
    public Result<List<CandidateInfoResponse>> listCandidatesByStatus(@PathVariable String status) {
        log.info("查询候选人，状态: {}", status);
        List<CandidateInfoResponse> candidates = candidateQueryService.findByStatus(status);
        return Result.success(candidates);
    }

    /**
     * 根据技能关键词搜索候选人
     *
     * @param skillKeyword 技能关键词
     * @return 候选人列表
     */
    @GetMapping("/search")
    public Result<List<CandidateInfoResponse>> searchCandidates(@RequestParam String skillKeyword) {
        log.info("搜索候选人，技能关键词: {}", skillKeyword);
        List<CandidateInfoResponse> candidates = candidateQueryService.findBySkillKeyword(skillKeyword);
        return Result.success(candidates);
    }

    /**
     * 开始面试
     *
     * @param candidateId 候选人ID
     * @return 更新后的候选人ID
     */
    @PutMapping("/{candidateId}/start-interview")
    public Result<String> startInterview(@PathVariable String candidateId) {
        log.info("开始面试，候选人ID: {}", candidateId);
        String updatedId = candidateCommandService.startInterview(candidateId);
        return Result.success(updatedId);
    }

    /**
     * 录用候选人
     *
     * @param candidateId 候选人ID
     * @return 更新后的候选人ID
     */
    @PutMapping("/{candidateId}/hire")
    public Result<String> hireCandidate(@PathVariable String candidateId) {
        log.info("录用候选人，候选人ID: {}", candidateId);
        String updatedId = candidateCommandService.hireCandidate(candidateId);
        return Result.success(updatedId);
    }

    /**
     * 更新候选人技能
     *
     * @param candidateId 候选人ID
     * @param skills      技能列表
     * @return 更新后的候选人ID
     */
    @PutMapping("/{candidateId}/skills")
    public Result<String> updateSkills(
            @PathVariable String candidateId,
            @RequestParam String skills) {
        log.info("更新候选人技能，候选人ID: {}", candidateId);
        String updatedId = candidateCommandService.updateSkills(candidateId, skills);
        return Result.success(updatedId);
    }

    /**
     * 删除候选人
     *
     * @param candidateId 候选人ID
     * @return 成功响应
     */
    @DeleteMapping("/{candidateId}")
    public Result<Void> deleteCandidate(@PathVariable String candidateId) {
        log.info("删除候选人，候选人ID: {}", candidateId);
        candidateCommandService.deleteCandidate(candidateId);
        return Result.success();
    }
}
