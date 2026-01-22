package com.xiaoo.kaleido.admin.application.query.impl;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.admin.application.query.INoticeQueryService;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.api.notice.response.NoticeResponse;
import com.xiaoo.kaleido.api.notice.response.NoticeTemplateResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知查询服务实现
 * 负责通知相关的读操作编排，通过RPC调用通知服务
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeQueryServiceImpl implements INoticeQueryService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcNoticeService rpcNoticeService;

    @Override
    public NoticeTemplateResponse findTemplateByCode(String code) {
        Result<NoticeTemplateResponse> result = rpcNoticeService.getTemplateByCode(code);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("查询通知模板失败，模板编码: {}, 错误信息: {}", code, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public NoticeResponse findNoticeById(String id) {
        Result<NoticeResponse> result = rpcNoticeService.getNoticeById(id);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("查询通知详情失败，通知ID: {}, 错误信息: {}", id, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public List<NoticeResponse> findNoticesByTarget(String target) {
        Result<List<NoticeResponse>> result = rpcNoticeService.getNoticesByTarget(target);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("根据目标地址查询通知列表失败，目标地址: {}, 错误信息: {}", target, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public PageInfo<NoticeResponse> pageQueryNotices(NoticePageQueryReq req) {
        Result<PageInfo<NoticeResponse>> result = rpcNoticeService.pageQueryNotices(req);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("分页查询通知列表失败，错误信息: {}", result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public PageInfo<NoticeTemplateResponse> pageQueryTemplates(NoticeTemplatePageQueryReq req) {
        Result<PageInfo<NoticeTemplateResponse>> result = rpcNoticeService.pageQueryTemplates(req);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("分页查询通知模板列表失败，错误信息: {}", result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }
}
