package com.xiaoo.kaleido.admin.application.query;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.api.notice.response.NoticeResponse;
import com.xiaoo.kaleido.api.notice.response.NoticeTemplateResponse;

import java.util.List;

/**
 * 通知查询服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface INoticeQueryService {
    
    /**
     * 根据模板编码获取模板详情
     *
     * @param code 模板编码
     * @return 模板详情
     */
    NoticeTemplateResponse findTemplateByCode(String code);
    
    /**
     * 根据ID获取通知详情
     *
     * @param id 通知ID
     * @return 通知详情
     */
    NoticeResponse findNoticeById(String id);

    /**
     * 根据目标地址获取通知列表
     *
     * @param target 目标地址
     * @return 通知列表
     */
    List<NoticeResponse> findNoticesByTarget(String target);

    /**
     * 分页查询通知列表
     *
     * @param req 分页查询请求
     * @return 分页通知列表
     */
    PageInfo<NoticeResponse> pageQueryNotices(NoticePageQueryReq req);

    /**
     * 分页查询通知模板列表
     *
     * @param req 分页查询请求
     * @return 分页模板列表
     */
    PageInfo<NoticeTemplateResponse> pageQueryTemplates(NoticeTemplatePageQueryReq req);
}
