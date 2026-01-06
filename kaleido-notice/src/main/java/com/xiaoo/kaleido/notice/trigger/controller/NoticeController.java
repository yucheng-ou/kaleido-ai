package com.xiaoo.kaleido.notice.trigger.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.notice.command.AddNoticeTemplateCommand;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.api.notice.response.NoticeResponse;
import com.xiaoo.kaleido.api.notice.response.NoticeTemplateResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.application.NoticeCommandService;
import com.xiaoo.kaleido.notice.application.NoticeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知控制器
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Tag(name = "通知管理", description = "通知相关接口")
public class NoticeController {

    private final NoticeCommandService noticeCommandService;
    private final NoticeQueryService noticeQueryService;


    @PostMapping("/template")
    @Operation(summary = "添加通知模板")
    public Result<String> addNoticeTemplate(@Valid @RequestBody AddNoticeTemplateCommand command) {
        String templateId = noticeCommandService.addNoticeTemplate(command);
        return Result.success(templateId);
    }

    @GetMapping("/template/{code}")
    @Operation(summary = "根据编码查询通知模板")
    public Result<NoticeTemplateResponse> getTemplateByCode(@PathVariable String code) {
        return Result.success(noticeQueryService.findTemplateByCode(code));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询通知")
    public Result<NoticeResponse> getNoticeById(@PathVariable String id) {
        return Result.success(noticeQueryService.findNoticeById(id));
    }

    @GetMapping("/target/{target}")
    @Operation(summary = "根据目标地址查询通知列表")
    public Result<List<NoticeResponse>> getNoticesByTarget(@PathVariable String target) {
        return Result.success(noticeQueryService.findNoticesByTarget(target));
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询通知")
    public Result<PageInfo<NoticeResponse>> pageQueryNotices(@Valid @RequestBody NoticePageQueryReq req) {
        return Result.success(noticeQueryService.pageQueryNotices(req));
    }

    @PostMapping("/template/page")
    @Operation(summary = "分页查询通知模板")
    public Result<PageInfo<NoticeTemplateResponse>> pageQueryTemplates(@Valid @RequestBody NoticeTemplatePageQueryReq req) {
        return Result.success(noticeQueryService.pageQueryTemplates(req));
    }
}
