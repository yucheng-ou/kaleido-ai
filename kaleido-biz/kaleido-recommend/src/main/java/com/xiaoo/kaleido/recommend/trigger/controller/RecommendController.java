package com.xiaoo.kaleido.recommend.trigger.controller;

import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.recommend.application.command.RecommendCommandService;
import com.xiaoo.kaleido.recommend.application.query.IRecommendQueryService;
import com.xiaoo.kaleido.api.recommend.response.RecommendRecordResponse;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 推荐API
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final IRecommendQueryService recommendQueryService;
    private final RecommendCommandService recommendCommandService;

    /**
     * 创建推荐记录
     *
     * @param request 创建推荐记录请求
     * @return 创建的推荐记录ID
     */
    @PostMapping
    public Result<String> createRecommendRecord(@Valid @RequestBody CreateRecommendRecordRequest request) {
        String userId = StpUserUtil.getLoginId();
        String recommendRecordId = recommendCommandService.createRecommendRecord(userId, request.getPrompt());
        return Result.success(recommendRecordId);
    }

    /**
     * 查询单个推荐记录详情
     *
     * @param recommendRecordId 推荐记录ID
     * @return 推荐记录详情
     */
    @GetMapping("/{recommendRecordId}")
    public Result<RecommendRecordResponse> getRecommendRecord(@PathVariable String recommendRecordId) {
        String userId = StpUserUtil.getLoginId();
        RecommendRecordResponse recommendRecord = recommendQueryService.findRecommendRecordById(recommendRecordId, userId);
        return Result.success(recommendRecord);
    }

    /**
     * 查询用户的推荐记录列表
     *
     * @return 推荐记录列表
     */
    @GetMapping
    public Result<List<RecommendRecordResponse>> getRecommendRecords() {
        String userId = StpUserUtil.getLoginId();
        List<RecommendRecordResponse> recommendRecords = recommendQueryService.findRecommendRecordsByUserId(userId);
        return Result.success(recommendRecords);
    }
    /**
     * 创建推荐记录请求
     */
    @Data
    public static class CreateRecommendRecordRequest {
        /**
         * 用户输入的推荐需求提示词
         */
        @NotBlank(message = "提示词不能为空")
        private String prompt;
    }
}
