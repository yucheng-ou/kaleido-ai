package com.xiaoo.kaleido.notice.application;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.api.notice.response.NoticeResponse;
import com.xiaoo.kaleido.api.notice.response.NoticeTemplateResponse;
import com.xiaoo.kaleido.notice.application.convertor.NoticeAppConvertor;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeRepository;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeTemplateRepository;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import com.xiaoo.kaleido.notice.domain.service.INoticeDomainService;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    private final NoticeAppConvertor noticeAppConvertor;
    private final INoticeDomainService noticeDomainService;

    /**
     * 根据ID查找通知
     *
     * @param id 通知ID
     * @return 通知响应对象
     */
    public NoticeResponse findNoticeById(String id) {
        // 1.记录查询日志
        log.debug("根据ID查询通知，id={}", id);

        // 2.调用仓储接口获取通知聚合根
        NoticeAggregate aggregate = noticeRepository.findById(id);

        // 3.校验数据是否存在
        if (aggregate == null) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_RECORD_NOT_FOUND);
        }

        // 4.转换为响应对象并返回
        return noticeAppConvertor.toResponse(aggregate);
    }

    /**
     * 根据目标地址查找通知列表
     *
     * @param target 目标地址
     * @return 通知响应对象列表
     */
    public List<NoticeResponse> findNoticesByTarget(String target) {
        // 1.记录查询日志
        log.debug("根据目标地址查询通知列表，target={}", target);

        // 2.调用仓储接口获取通知列表
        // 3.使用流处理转换为响应对象列表
        return noticeRepository.findByTarget(target).stream().map(noticeAppConvertor::toResponse).collect(Collectors.toList());
    }

    /**
     * 根据模板编码查找模板
     *
     * @param code 模板编码
     * @return 模板响应对象
     */
    public NoticeTemplateResponse findTemplateByCode(String code) {
        // 1.记录查询日志
        log.debug("根据编码查询通知模板，code={}", code);

        // 2.调用仓储接口获取模板聚合根
        NoticeTemplateAggregate aggregate = noticeTemplateRepository.findByCodeOrThrow(code);

        // 3.转换为响应对象并返回
        return noticeAppConvertor.toTemplateResponse(aggregate);
    }

    /**
     * 分页查询通知
     *
     * @param req 查询请求
     * @return 分页结果
     */
    public PageInfo<NoticeResponse> pageQueryNotices(NoticePageQueryReq req) {
        // 1.记录查询日志
        log.debug("分页查询通知，条件: {}", req);

        // 2.启动PageHelper分页
        PageHelper.startPage(req.getPageNum(), req.getPageSize());

        // 3.调用仓储接口进行分页查询
        List<NoticeAggregate> aggregates = noticeRepository.pageQuery(req);

        // 4.使用MapStruct转换器将聚合根转换为Response对象
        List<NoticeResponse> responseList = aggregates.stream().map(noticeAppConvertor::toResponse).collect(Collectors.toList());

        // 5.构建PageInfo分页响应
        return new PageInfo<>(responseList);
    }

    /**
     * 分页查询通知模板
     *
     * @param req 查询请求
     * @return 分页结果
     */
    public PageInfo<NoticeTemplateResponse> pageQueryTemplates(NoticeTemplatePageQueryReq req) {
        // 1.记录查询日志
        log.debug("分页查询通知模板，条件: {}", req);

        // 2.启动PageHelper分页
        PageHelper.startPage(req.getPageNum(), req.getPageSize());

        // 3.调用仓储接口进行分页查询
        List<NoticeTemplateAggregate> aggregates = noticeTemplateRepository.pageQuery(req);

        // 4.使用MapStruct转换器将聚合根转换为Response对象
        List<NoticeTemplateResponse> responseList = aggregates.stream().map(noticeAppConvertor::toTemplateResponse).collect(Collectors.toList());

        // 5.构建PageInfo分页响应
        return new PageInfo<>(responseList);
    }
}
