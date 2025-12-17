package com.xiaoo.kaleido.notice.domain.adapter.port;

import com.xiaoo.kaleido.notice.domain.constant.NoticeStatus;
import com.xiaoo.kaleido.notice.domain.constant.NoticeType;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 通知记录仓储接口（领域层）
 * 定义通知记录聚合根的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public interface NoticeRecordRepository {

    /**
     * 保存通知记录聚合根
     *
     * @param noticeRecord 通知记录聚合根
     * @return 保存后的通知记录聚合根
     */
    NoticeRecord save(NoticeRecord noticeRecord);

    /**
     * 根据ID查找通知记录聚合根
     *
     * @param id 通知记录ID
     * @return 通知记录聚合根（如果存在）
     */
    Optional<NoticeRecord> findById(String id);

    /**
     * 根据目标（手机号/邮箱）查找通知记录
     *
     * @param target 接收目标
     * @return 通知记录列表
     */
    List<NoticeRecord> findByTarget(String target);

    /**
     * 根据业务类型查找通知记录
     *
     * @param businessType 业务类型
     * @return 通知记录列表
     */
    List<NoticeRecord> findByBusinessType(String businessType);

    /**
     * 根据状态查找通知记录
     *
     * @param status 通知状态
     * @return 通知记录列表
     */
    List<NoticeRecord> findByStatus(NoticeStatus status);

    /**
     * 根据通知类型查找通知记录
     *
     * @param noticeType 通知类型
     * @return 通知记录列表
     */
    List<NoticeRecord> findByNoticeType(NoticeType noticeType);

    /**
     * 查找需要重试的通知记录
     *
     * @param maxRetryCount 最大重试次数
     * @param beforeTime    在此之前需要重试
     * @return 需要重试的通知记录列表
     */
    List<NoticeRecord> findNeedRetryRecords(int maxRetryCount, LocalDateTime beforeTime);

    /**
     * 根据ID查找通知记录聚合根，如果不存在则抛出异常
     *
     * @param id 通知记录ID
     * @return 通知记录聚合根
     * @throws com.xiaoo.kaleido.notice.types.exception.NoticeException 如果通知记录不存在
     */
    NoticeRecord findByIdOrThrow(String id);

    /**
     * 删除通知记录
     *
     * @param id 通知记录ID
     */
    void deleteById(String id);

    /**
     * 统计某个目标的发送次数
     *
     * @param target       接收目标
     * @param businessType 业务类型
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return 发送次数
     */
    long countByTargetAndBusinessTypeAndCreatedAtBetween(String target, String businessType, 
                                                         LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 更新通知记录状态
     *
     * @param id     通知记录ID
     * @param status 新状态
     * @return 更新后的通知记录
     */
    NoticeRecord updateStatus(String id, NoticeStatus status);
}
