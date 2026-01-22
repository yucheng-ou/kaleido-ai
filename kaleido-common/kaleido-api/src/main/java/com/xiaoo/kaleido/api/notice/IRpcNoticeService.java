package com.xiaoo.kaleido.api.notice;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.notice.command.AddNoticeTemplateCommand;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.api.notice.response.NoticeResponse;
import com.xiaoo.kaleido.api.notice.response.NoticeTemplateResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * 通知RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@DubboService
public interface IRpcNoticeService {

    /**
     * 生成并发送短信验证码
     *
     * @param command 发送短信验证码命令
     * @return 验证码
     */
    Result<String> generateAndSendSmsVerifyCode(@Valid SendSmsVerifyCodeCommand command);

    /**
     * 校验短信验证码
     *
     * @param command 校验短信验证码命令
     * @return 校验结果
     */
    Result<Boolean> checkSmsVerifyCode(@Valid CheckSmsVerifyCodeCommand command);

    /**
     * 添加通知模板
     *
     * @param command 添加通知模板命令
     * @return 模板ID
     */
    Result<String> addNoticeTemplate(@Valid AddNoticeTemplateCommand command);

    /**
     * 根据模板编码获取模板详情
     *
     * @param code 模板编码
     * @return 模板详情
     */
    Result<NoticeTemplateResponse> getTemplateByCode(String code);

    /**
     * 根据ID获取通知详情
     *
     * @param id 通知ID
     * @return 通知详情
     */
    Result<NoticeResponse> getNoticeById(String id);

    /**
     * 根据目标地址获取通知列表
     *
     * @param target 目标地址
     * @return 通知列表
     */
    Result<List<NoticeResponse>> getNoticesByTarget(String target);

    /**
     * 分页查询通知列表
     *
     * @param req 分页查询请求
     * @return 分页通知列表
     */
    Result<PageInfo<NoticeResponse>> pageQueryNotices(@Valid NoticePageQueryReq req);

    /**
     * 分页查询通知模板列表
     *
     * @param req 分页查询请求
     * @return 分页模板列表
     */
    Result<PageInfo<NoticeTemplateResponse>> pageQueryTemplates(@Valid NoticeTemplatePageQueryReq req);
}
