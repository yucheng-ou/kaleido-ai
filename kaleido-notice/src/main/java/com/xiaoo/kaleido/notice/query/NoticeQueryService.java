package com.xiaoo.kaleido.sms.query;

import com.xiaoo.kaleido.sms.domain.adapter.repository.NoticeRecordRepository;
import com.xiaoo.kaleido.sms.domain.adapter.repository.NoticeTemplateRepository;
import com.xiaoo.kaleido.sms.types.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.sms.types.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.sms.domain.model.entity.NoticeTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
public class NoticeQueryService {

    private final NoticeRecordRepository noticeRecordRepository;
    private final NoticeTemplateRepository noticeTemplateRepository;

    /**
     * 根据ID查询通知记录
     *
     * @param noticeId 通知记录ID
     * @return 通知记录DTO
     */
    public NoticeRecordDTO getNoticeRecordById(String noticeId) {
        NoticeRecord noticeRecord = noticeRecordRepository.findByIdOrThrow(noticeId);
        return NoticeRecordDTO.from(noticeRecord);
    }

    /**
     * 分页查询通知记录
     *
     * @param target       接收目标
     * @param businessType 业务类型
     * @param noticeTypeEnum   通知类型
     * @param status       通知状态
     * @param pageable     分页参数
     * @return 通知记录分页结果
     */
    public Page<NoticeRecordDTO> queryNoticeRecords(String target, String businessType,
                                                    NoticeTypeEnum noticeTypeEnum, NoticeStatusEnum status,
                                                    Pageable pageable) {
        Page<NoticeRecord> records = noticeRecordRepository.findByConditions(
                target, businessType, noticeTypeEnum, status, pageable);
        return records.map(NoticeRecordDTO::from);
    }

    /**
     * 查询用户的通知记录
     *
     * @param target 用户标识（手机号、邮箱等）
     * @param limit  查询数量限制
     * @return 通知记录列表
     */
    public List<NoticeRecordDTO> getUserNoticeRecords(String target, int limit) {
        List<NoticeRecord> records = noticeRecordRepository.findRecentByTarget(target, limit);
        return records.stream()
                .map(NoticeRecordDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * 查询失败的通知记录
     *
     * @param maxRetryCount 最大重试次数
     * @param limit         查询数量限制
     * @return 失败的通知记录列表
     */
    public List<NoticeRecordDTO> getFailedNoticeRecords(int maxRetryCount, int limit) {
        List<NoticeRecord> records = noticeRecordRepository.findFailedRecords(maxRetryCount, limit);
        return records.stream()
                .map(NoticeRecordDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * 根据业务类型查询通知模板
     *
     * @param businessType 业务类型
     * @return 通知模板列表
     */
    public List<NoticeTemplateDTO> getTemplatesByBusinessType(String businessType) {
        List<NoticeTemplate> templates = noticeTemplateRepository.findByBusinessType(businessType);
        return templates.stream()
                .map(NoticeTemplateDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * 根据ID查询通知模板
     *
     * @param templateId 模板ID
     * @return 通知模板DTO
     */
    public NoticeTemplateDTO getTemplateById(String templateId) {
        NoticeTemplate template = noticeTemplateRepository.findByIdOrThrow(templateId);
        return NoticeTemplateDTO.from(template);
    }

    /**
     * 查询所有启用的通知模板
     *
     * @return 启用的通知模板列表
     */
    public List<NoticeTemplateDTO> getAllEnabledTemplates() {
        List<NoticeTemplate> templates = noticeTemplateRepository.findAllEnabled();
        return templates.stream()
                .map(NoticeTemplateDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * 统计通知发送情况
     *
     * @param businessType 业务类型
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return 通知统计信息
     */
    public NoticeStatisticsDTO getNoticeStatistics(String businessType, Long startTime, Long endTime) {
        long totalCount = noticeRecordRepository.countByBusinessTypeAndTimeRange(businessType, startTime, endTime);
        long successCount = noticeRecordRepository.countSuccessByBusinessTypeAndTimeRange(businessType, startTime, endTime);
        long failedCount = noticeRecordRepository.countFailedByBusinessTypeAndTimeRange(businessType, startTime, endTime);
        
        return NoticeStatisticsDTO.builder()
                .totalCount(totalCount)
                .successCount(successCount)
                .failedCount(failedCount)
                .successRate(totalCount > 0 ? (double) successCount / totalCount * 100 : 0)
                .build();
    }

    /**
     * 验证验证码是否有效
     *
     * @param mobile       手机号
     * @param code         验证码
     * @param businessType 业务类型
     * @return 验证结果
     */
    public VerifyCodeResultDTO verifyCode(String mobile, String code, String businessType) {
        // 查找最近的通知记录
        List<NoticeRecord> recentRecords = noticeRecordRepository.findRecentByTargetAndBusinessType(mobile, businessType, 10);
        
        for (NoticeRecord record : recentRecords) {
            // 检查消息内容是否包含验证码
            String content = record.getNotice().getContent();
            if (content != null && content.contains(code)) {
                // 验证码有效，检查是否已过期（5分钟内）
                long createdAt = record.getNotice().getCreatedAt().getTime();
                long now = System.currentTimeMillis();
                long diffInMinutes = (now - createdAt) / (1000 * 60);
                
                if (diffInMinutes <= 5) { // 5分钟内有效
                    return VerifyCodeResultDTO.success("验证码验证成功");
                } else {
                    return VerifyCodeResultDTO.failed("验证码已过期");
                }
            }
        }
        
        return VerifyCodeResultDTO.failed("验证码无效");
    }
}
