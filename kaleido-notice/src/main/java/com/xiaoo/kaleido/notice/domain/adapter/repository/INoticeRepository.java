package com.xiaoo.kaleido.notice.domain.adapter.repository;

import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.api.notice.enums.NoticeStatusEnum;

import java.util.List;
import java.util.Optional;

/**
 * 通知记录仓储接口（领域层）
 * 定义通知记录聚合根的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
public interface INoticeRepository {

    /**
     * 根据ID查找通知聚合根
     *
     * @param id 通知ID
     * @return 通知聚合根（如果存在）
     */
    Optional<NoticeAggregate> findById(String id);

    /**
     * 根据ID查找通知聚合根，如果不存在则抛出异常
     *
     * @param id 通知ID
     * @return 通知聚合根
     */
    NoticeAggregate findByIdOrThrow(String id);

    /**
     * 保存通知聚合根
     *
     * @param notice 通知聚合根
     * @return 保存后的通知聚合根
     */
    NoticeAggregate save(NoticeAggregate notice);

    /**
     * 根据ID删除通知聚合根
     *
     * @param id 通知ID
     */
    void deleteById(String id);

    /**
     * 根据状态查找通知列表
     *
     * @param status 通知状态
     * @return 通知列表
     */
    List<NoticeAggregate> findByStatus(NoticeStatusEnum status);

    /**
     * 根据目标地址查找通知列表
     *
     * @param target 目标地址
     * @return 通知列表
     */
    List<NoticeAggregate> findByTarget(String target);

    /**
     * 根据业务类型查找通知列表
     *
     * @param businessType 业务类型
     * @return 通知列表
     */
    List<NoticeAggregate> findByBusinessType(String businessType);

    /**
     * 分页查询通知
     *
     * @param req 查询条件
     * @return 通知列表
     */
    List<NoticeAggregate> pageQuery(NoticePageQueryReq req);
}
