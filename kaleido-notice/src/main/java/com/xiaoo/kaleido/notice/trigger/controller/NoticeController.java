package com.xiaoo.kaleido.notice.trigger.controller;

import com.xiaoo.kaleido.api.notice.command.AddNoticeTemplateCommand;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.base.response.PageResp;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.command.NoticeCommandService;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import com.xiaoo.kaleido.notice.query.NoticeQueryService;
import com.xiaoo.kaleido.notice.trigger.rpc.RpcNoticeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    private final RpcNoticeServiceImpl rpcNoticeService;

    @PostMapping("/sms/verify-code/send")
    @Operation(summary = "发送短信验证码")
    public Result<String> sendSmsVerifyCode(@Valid @RequestBody SendSmsVerifyCodeCommand command) {
        return rpcNoticeService.generateAndSendSmsVerifyCode(command);
    }

    @PostMapping("/sms/verify-code/check")
    @Operation(summary = "校验短信验证码")
    public Result<Boolean> checkSmsVerifyCode(@Valid @RequestBody CheckSmsVerifyCodeCommand command) {
        return rpcNoticeService.checkSmsVerifyCode(command);
    }

    @PostMapping("/template")
    @Operation(summary = "添加通知模板")
    public Result<String> addNoticeTemplate(@Valid @RequestBody AddNoticeTemplateCommand command) {
        // TODO: 实现添加通知模板逻辑
        // 目前返回成功，待业务逻辑完善后实现
        return Result.success("模板添加成功");
    }

    @GetMapping("/template/{code}")
    @Operation(summary = "根据编码查询通知模板")
    public Result<NoticeTemplateAggregate> getTemplateByCode(@PathVariable String code) {
        return noticeQueryService.findTemplateByCode(code)
                .map(Result::success)
                .orElse(Result.error("NOTICE_TEMPLATE_NOT_FOUND", "模板不存在"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询通知")
    public Result<NoticeAggregate> getNoticeById(@PathVariable String id) {
        return noticeQueryService.findNoticeById(id)
                .map(Result::success)
                .orElse(Result.error("NOTICE_NOT_FOUND", "通知不存在"));
    }

    @GetMapping("/target/{target}")
    @Operation(summary = "根据目标地址查询通知列表")
    public Result<java.util.List<NoticeAggregate>> getNoticesByTarget(@PathVariable String target) {
        return Result.success(noticeQueryService.findNoticesByTarget(target));
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询通知")
    public Result<PageResp<NoticeAggregate>> pageQueryNotices(@Valid @RequestBody NoticePageQueryReq req) {
        return Result.success(noticeQueryService.pageQueryNotices(req));
    }

    @PostMapping("/template/page")
    @Operation(summary = "分页查询通知模板")
    public Result<PageResp<NoticeTemplateAggregate>> pageQueryTemplates(@Valid @RequestBody NoticeTemplatePageQueryReq req) {
        return Result.success(noticeQueryService.pageQueryTemplates(req));
    }
}
