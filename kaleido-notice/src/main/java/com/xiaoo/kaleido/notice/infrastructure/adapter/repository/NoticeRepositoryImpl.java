package com.xiaoo.kaleido.notice.infrastructure.adapter.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.base.response.PageResp;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeRepository;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.infrastructure.adapter.repository.convertor.NoticeConvertor;
import com.xiaoo.kaleido.notice.infrastructure.dao.NoticeDao;
import com.xiaoo.kaleido.notice.infrastructure.dao.po.NoticePO;
import com.xiaoo.kaleido.api.notice.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 通知仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements INoticeRepository {

    private final NoticeDao noticeDao;

    @Override
    public Optional<NoticeAggregate> findById(String id) {
        NoticePO noticePO = noticeDao.selectById(id);
        if (noticePO == null) {
            return Optional.empty();
        }
        return Optional.of(NoticeConvertor.INSTANCE.toAggregate(noticePO));
    }

    @Override
    public NoticeAggregate save(NoticeAggregate notice) {
        NoticePO noticePO = NoticeConvertor.INSTANCE.toPO(notice);
        if (noticePO.getId() == null) {
            // 新增
            noticeDao.insert(noticePO);
        } else {
            // 更新
            noticeDao.updateById(noticePO);
        }
        // 重新加载并返回
        return NoticeConvertor.INSTANCE.toAggregate(noticePO);
    }

    @Override
    public void deleteById(String id) {
        noticeDao.deleteById(id);
    }

    @Override
    public List<NoticeAggregate> findByStatus(NoticeStatusEnum status) {
        List<NoticePO> noticePOs = noticeDao.findByStatus(status);
        return noticePOs.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeAggregate> findByTarget(String target) {
        List<NoticePO> noticePOs = noticeDao.findByTargetAddress(target);
        return noticePOs.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeAggregate> findByBusinessType(String businessType) {
        List<NoticePO> noticePOs = noticeDao.findByBusinessType(businessType);
        return noticePOs.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    /**
     * 根据ID查找通知聚合根，如果不存在则抛出异常
     *
     * @param id 通知ID
     * @return 通知聚合根
     */
    public NoticeAggregate findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(() -> NoticeException.of(NoticeErrorCode.NOTICE_RECORD_NOT_FOUND));
    }

    /**
     * 分页查询通知
     *
     * @param req 查询条件
     * @return 分页结果
     */
    public PageResp<NoticeAggregate> pageQuery(NoticePageQueryReq req) {
        // 使用PageHelper进行分页
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        
        // 执行查询
        List<NoticePO> noticePOList = noticeDao.selectByCondition(req);
        
        // 转换为PageInfo获取分页信息
        PageInfo<NoticePO> pageInfo = new PageInfo<>(noticePOList);
        
        // 转换为聚合根列表
        List<NoticeAggregate> aggregateList = noticePOList.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
        
        // 构建分页响应
        return PageResp.of(
                aggregateList,
                pageInfo.getTotal(),
                pageInfo.getPageNum(),
                pageInfo.getPageSize()
        );
    }

    /**
     * 查找需要重试的通知列表
     *
     * @param maxRetryNum 最大重试次数
     * @return 需要重试的通知列表
     */
    public List<NoticeAggregate> findRetryNotices(Integer maxRetryNum) {
        List<NoticePO> noticePOs = noticeDao.findRetryNotices(maxRetryNum);
        return noticePOs.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }
}
