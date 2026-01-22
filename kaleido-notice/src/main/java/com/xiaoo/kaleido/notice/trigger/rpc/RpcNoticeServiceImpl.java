package com.xiaoo.kaleido.notice.trigger.rpc;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.AddNoticeTemplateCommand;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.api.notice.response.NoticeResponse;
import com.xiaoo.kaleido.api.notice.response.NoticeTemplateResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.application.NoticeCommandService;
import com.xiaoo.kaleido.notice.application.NoticeQueryService;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 通知RPC服务实现
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
public class RpcNoticeServiceImpl implements IRpcNoticeService {

    private final NoticeCommandService noticeCommandService;
    private final NoticeQueryService noticeQueryService;

    @Override
    public Result<String> generateAndSendSmsVerifyCode(
            @Valid SendSmsVerifyCodeCommand command) {
        return Result.success(noticeCommandService.sendSmsVerifyCode(command));
    }

    @Override
    public Result<Boolean> checkSmsVerifyCode(@Valid CheckSmsVerifyCodeCommand command) {
        return Result.success(noticeCommandService.checkSmsVerifyCode(command));
    }

    @Override
    public Result<String> addNoticeTemplate(@Valid AddNoticeTemplateCommand command) {
        return Result.success(noticeCommandService.addNoticeTemplate(command));
    }

    @Override
    public Result<NoticeTemplateResponse> getTemplateByCode(String code) {
        return Result.success(noticeQueryService.findTemplateByCode(code));
    }

    @Override
    public Result<NoticeResponse> getNoticeById(String id) {
        return Result.success(noticeQueryService.findNoticeById(id));
    }

    @Override
    public Result<List<NoticeResponse>> getNoticesByTarget(String target) {
        return Result.success(noticeQueryService.findNoticesByTarget(target));
    }

    @Override
    public Result<PageInfo<NoticeResponse>> pageQueryNotices(@Valid NoticePageQueryReq req) {
        return Result.success(noticeQueryService.pageQueryNotices(req));
    }

    @Override
    public Result<PageInfo<NoticeTemplateResponse>> pageQueryTemplates(@Valid NoticeTemplatePageQueryReq req) {
        return Result.success(noticeQueryService.pageQueryTemplates(req));
    }
}
