package com.xiaoo.kaleido.notice.query;

import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.base.response.PageResp;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeRepository;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeTemplateRepository;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 通知查询服务（应用层）
 * 负责通知相关的读操作
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeQueryService {

    private final INoticeRepository noticeRepository;
    private final INoticeTemplateRepository noticeTemplateRepository;

    /**
     * 根据ID查找通知
     *
     * @param id 通知ID
     * @return 通知聚合根
     */
    public Optional<NoticeAggregate> findNoticeById(String id) {
        return noticeRepository.findById(id);
    }

    /**
     * 根据目标地址查找通知列表
     *
     * @param target 目标地址
     * @return 通知列表
     */
    public List<NoticeAggregate> findNoticesByTarget(String target) {
        return noticeRepository.findByTarget(target);
    }

    /**
     * 根据模板编码查找模板
     *
     * @param code 模板编码
     * @return 模板聚合根
     */
    public Optional<NoticeTemplateAggregate> findTemplateByCode(String code) {
        return noticeTemplateRepository.findByCode(code);
    }

    /**
     * 分页查询通知
     *
     * @param req 查询请求
     * @return 分页结果
     */
    public PageResp<NoticeAggregate> pageQueryNotices(NoticePageQueryReq req) {
        // TODO: 实现分页查询逻辑
        // 目前返回空结果，待基础设施层完善后实现
        return PageResp.success(List.of(), 0L, req.getPageNum(), req.getPageSize());
    }

    /**
     * 分页查询通知模板
     *
     * @param req 查询请求
     * @return 分页结果
     */
    public PageResp<NoticeTemplateAggregate> pageQueryTemplates(NoticeTemplatePageQueryReq req) {
        // TODO: 实现分页查询逻辑
        // 目前返回空结果，待基础设施层完善后实现
        return PageResp.success(List.of(), 0L, req.getPageNum(), req.getPageSize());
    }
}
