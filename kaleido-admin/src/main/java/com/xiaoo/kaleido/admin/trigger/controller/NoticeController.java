package com.xiaoo.kaleido.admin.trigger.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.notice.command.AddNoticeTemplateCommand;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.api.notice.response.NoticeResponse;
import com.xiaoo.kaleido.api.notice.response.NoticeTemplateResponse;
import com.xiaoo.kaleido.admin.application.command.INoticeCommandService;
import com.xiaoo.kaleido.admin.application.query.INoticeQueryService;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知管理API（管理后台）
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final INoticeQueryService noticeQueryService;
    private final INoticeCommandService noticeCommandService;

    /**
     * 添加通知模板
     *
     * @param command 添加通知模板命令
     * @return 模板ID
     */
    @SaCheckPermission(value = "admin:notice:update", type = StpAdminUtil.TYPE)
    @PostMapping("/template")
    public Result<String> addNoticeTemplate(@Valid @RequestBody AddNoticeTemplateCommand command) {
        String templateId = noticeCommandService.addNoticeTemplate(command);
        return Result.success(templateId);
    }

    /**
     * 根据模板编码获取模板详情
     *
     * @param code 模板编码
     * @return 模板详情
     */
    @SaCheckPermission(value = "admin:notice:read", type = StpAdminUtil.TYPE)
    @GetMapping("/template/{code}")
    public Result<NoticeTemplateResponse> getTemplateByCode(
            @NotBlank(message = "模板编码不能为空")
            @PathVariable String code) {
        NoticeTemplateResponse templateResponse = noticeQueryService.findTemplateByCode(code);
        return Result.success(templateResponse);
    }

    /**
     * 根据ID获取通知详情
     *
     * @param id 通知ID
     * @return 通知详情
     */
    @SaCheckPermission(value = "admin:notice:read", type = StpAdminUtil.TYPE)
    @GetMapping("/{id}")
    public Result<NoticeResponse> getNoticeById(
            @NotBlank(message = "通知ID不能为空")
            @PathVariable String id) {
        NoticeResponse noticeResponse = noticeQueryService.findNoticeById(id);
        return Result.success(noticeResponse);
    }

    /**
     * 根据目标地址获取通知列表
     *
     * @param target 目标地址
     * @return 通知列表
     */
    @SaCheckPermission(value = "admin:notice:read", type = StpAdminUtil.TYPE)
    @GetMapping("/target/{target}")
    public Result<List<NoticeResponse>> getNoticesByTarget(
            @NotBlank(message = "目标地址不能为空")
            @PathVariable String target) {
        List<NoticeResponse> noticeResponses = noticeQueryService.findNoticesByTarget(target);
        return Result.success(noticeResponses);
    }

    /**
     * 分页查询通知列表
     *
     * @param req 分页查询请求
     * @return 分页通知列表
     */
    @SaCheckPermission(value = "admin:notice:read", type = StpAdminUtil.TYPE)
    @PostMapping("/page")
    public Result<PageInfo<NoticeResponse>> pageQueryNotices(@Valid @RequestBody NoticePageQueryReq req) {
        PageInfo<NoticeResponse> pageResult = noticeQueryService.pageQueryNotices(req);
        return Result.success(pageResult);
    }

    /**
     * 分页查询通知模板列表
     *
     * @param req 分页查询请求
     * @return 分页模板列表
     */
    @SaCheckPermission(value = "admin:notice:read", type = StpAdminUtil.TYPE)
    @PostMapping("/template/page")
    public Result<PageInfo<NoticeTemplateResponse>> pageQueryTemplates(@Valid @RequestBody NoticeTemplatePageQueryReq req) {
        PageInfo<NoticeTemplateResponse> pageResult = noticeQueryService.pageQueryTemplates(req);
        return Result.success(pageResult);
    }
}
