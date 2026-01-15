package com.xiaoo.kaleido.notice.trigger.job;

import cn.hutool.json.JSONUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeRepository;
import com.xiaoo.kaleido.notice.domain.factory.INoticeServiceFactory;
import com.xiaoo.kaleido.notice.domain.adapter.port.INoticeAdapterService;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.dynamictp.core.DtpRegistry;
import org.dromara.dynamictp.core.executor.DtpExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 通知推送重试计划任务
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeRetryJob {

    private final INoticeRepository noticeRepository;
    private final INoticeServiceFactory noticeServiceFactory;

    @Resource
    private ThreadPoolExecutor noticeRetryExecutor;

    /**
     * 通知推送重试计划任务（Dynamic-TP并行处理版本）
     * XXL-Job入口方法，负责整个重试任务的调度和执行
     */
    @XxlJob("noticeRetryHandler")
    public void noticeRetryHandler() {
        // 1.1 记录任务开始日志
        logTaskStart();

        // 1.2 初始化统计变量（替代RetryStatistics）
        int totalProcessed = 0;      // 总处理数量
        int totalSuccess = 0;        // 总成功数量
        int totalFailed = 0;         // 总失败数量
        int loopCount = 0;           // 循环次数
        boolean maxLoopReached = false;    // 是否达到最大循环次数
        boolean maxProcessReached = false; // 是否达到最大处理数量

        try {
            // 1.3 执行重试循环
            while (shouldContinue(loopCount, totalProcessed)) {
                loopCount++;
                int batchSuccess = 0;  // 当前批次成功数
                int batchFailed = 0;   // 当前批次失败数

                // 1.4 查询需要重试的通知
                List<NoticeAggregate> retryNotices = queryRetryNotices(loopCount);
                if (retryNotices.isEmpty()) {
                    break;  // 没有需要重试的通知，结束循环
                }

                // 1.5 并行处理当前批次
                int batchSize = retryNotices.size();
                totalProcessed += batchSize;

                // 创建并行任务
                List<CompletableFuture<NoticeRetryConstants.RetryStatus>> futures = retryNotices.stream()
                        .map(notice -> CompletableFuture.supplyAsync(
                                () -> retryNoticeWithStatus(notice),
                                noticeRetryExecutor
                        ))
                        .toList();

                // 等待所有任务完成
                waitForCompletion(futures);

                // 收集处理结果
                for (int i = 0; i < futures.size(); i++) {
                    CompletableFuture<NoticeRetryConstants.RetryStatus> future = futures.get(i);
                    try {
                        if (future.isDone() && !future.isCompletedExceptionally()) {
                            NoticeRetryConstants.RetryStatus status = future.getNow(NoticeRetryConstants.RetryStatus.FAILED);
                            if (status == NoticeRetryConstants.RetryStatus.SUCCESS) {
                                totalSuccess++;
                                batchSuccess++;
                            } else {
                                totalFailed++;
                                batchFailed++;
                            }
                        } else {
                            totalFailed++;
                            batchFailed++;
                            log.warn(NoticeRetryConstants.LogMessages.TASK_INCOMPLETE, i);
                        }
                    } catch (Exception e) {
                        totalFailed++;
                        batchFailed++;
                        log.error("获取任务结果异常", e);
                    }
                }

                // 1.6 记录批次结果
                logBatchResult(loopCount, batchSize, batchSuccess, batchFailed);

                // 1.7 检查是否应该结束循环（批次数量小于最大批次大小）
                if (shouldBreakLoop(retryNotices)) {
                    break;
                }
            }

            // 1.8 更新限制状态
            maxLoopReached = loopCount >= NoticeRetryConstants.MAX_LOOP_COUNT;
            maxProcessReached = totalProcessed >= NoticeRetryConstants.MAX_PROCESS_COUNT;

            // 1.9 记录最终结果
            logFinalResult(loopCount, totalProcessed, totalSuccess, totalFailed, maxLoopReached, maxProcessReached);

        } catch (Exception e) {
            // 1.10 处理任务异常
            handleTaskException(totalProcessed, totalSuccess, totalFailed, e);
        }
    }

    /**
     * 检查是否应该继续循环
     *
     * @param loopCount      当前循环次数
     * @param totalProcessed 总处理数量
     * @return true表示应该继续，false表示应该停止
     */
    private boolean shouldContinue(int loopCount, int totalProcessed) {
        return loopCount < NoticeRetryConstants.MAX_LOOP_COUNT
                && totalProcessed < NoticeRetryConstants.MAX_PROCESS_COUNT;
    }

    /**
     * 查询需要重试的通知
     *
     * @param loopCount 当前循环次数（用于日志）
     * @return 需要重试的通知列表，可能为空
     */
    private List<NoticeAggregate> queryRetryNotices(int loopCount) {
        // 2.1 从仓库查询需要重试的通知
        List<NoticeAggregate> retryNotices = noticeRepository.findRetryNotices(
                NoticeRetryConstants.RETRY_BATCH_SIZE);

        // 2.2 处理查询结果
        if (retryNotices.isEmpty()) {
            logNoRetryNotices(loopCount);
            return retryNotices;
        }

        // 2.3 记录批次开始日志
        logBatchStart(loopCount, retryNotices.size());
        return retryNotices;
    }

    /**
     * 重试单个通知并返回状态
     *
     * @param notice 通知聚合根
     * @return 重试状态（SUCCESS/FAILED/EXCEPTION）
     */
    private NoticeRetryConstants.RetryStatus retryNoticeWithStatus(NoticeAggregate notice) {
        try {
            // 2.4 执行重试并返回状态
            return retryNotice(notice) ?
                    NoticeRetryConstants.RetryStatus.SUCCESS :
                    NoticeRetryConstants.RetryStatus.FAILED;
        } catch (Exception e) {
            // 2.5 记录异常并返回异常状态
            log.error("重试通知异常，通知ID: {}, 错误信息: {}", notice.getId(), e.getMessage(), e);
            return NoticeRetryConstants.RetryStatus.EXCEPTION;
        }
    }

    /**
     * 重试单个通知的核心逻辑
     *
     * @param notice 通知聚合根
     * @return 是否重试成功
     */
    private boolean retryNotice(NoticeAggregate notice) {
        try {
            // 2.6 根据通知类型获取对应的适配器服务
            INoticeAdapterService noticeAdapterService = noticeServiceFactory.getNoticeAdapterService(
                    notice.getNoticeType());

            // 2.7 发送通知
            Result<Void> sendResult = noticeAdapterService.sendNotice(
                    notice.getTargetAddress(), notice.getContent());

            // 2.8 更新通知状态
            updateNoticeStatus(notice, sendResult);

            // 2.9 持久化更新
            noticeRepository.update(notice);

            // 2.10 返回发送结果
            return sendResult.getSuccess();

        } catch (Exception e) {
            // 2.11 记录异常并返回失败
            log.error("重试通知异常，通知ID: {}, 错误信息: {}", notice.getId(), e.getMessage(), e);
            return false;
        }
    }

    /**
     * 更新通知状态
     *
     * @param notice     通知聚合根
     * @param sendResult 发送结果
     */
    private void updateNoticeStatus(NoticeAggregate notice, Result<Void> sendResult) {
        if (sendResult.getSuccess()) {
            // 2.12 标记为成功并记录日志
            notice.markAsSuccess(JSONUtil.toJsonStr(sendResult));
            log.info("通知重试成功，通知ID: {}, 目标地址: {}", notice.getId(), notice.getTargetAddress());
        } else {
            // 2.13 标记为失败并记录错误日志
            notice.markAsFailed(JSONUtil.toJsonStr(sendResult));
            log.error("通知重试失败，通知ID: {}, 目标地址: {}, 失败原因: {}",
                    notice.getId(), notice.getTargetAddress(), sendResult.getMsg());
        }
    }

    /**
     * 等待所有并行任务完成
     *
     * @param futures 并行任务列表
     */
    private void waitForCompletion(List<CompletableFuture<NoticeRetryConstants.RetryStatus>> futures) {
        // 3.1 创建所有任务的组合Future
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        try {
            // 3.2 等待所有任务完成，设置超时时间
            allFutures.get(NoticeRetryConstants.PARALLEL_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (Exception e) {
            // 3.3 处理超时或异常
            log.warn(NoticeRetryConstants.LogMessages.PARALLEL_TIMEOUT, e);
            XxlJobHelper.log("并行处理超时或异常: {}", e.getMessage());
        }
    }

    /**
     * 判断是否应该结束循环
     *
     * @param retryNotices 当前批次的通知列表
     * @return true表示应该结束循环
     */
    private boolean shouldBreakLoop(List<NoticeAggregate> retryNotices) {
        return retryNotices.size() < NoticeRetryConstants.RETRY_BATCH_SIZE;
    }


    private void logTaskStart() {
        XxlJobHelper.log(NoticeRetryConstants.LogMessages.START_TASK);
        log.info(NoticeRetryConstants.LogMessages.START_TASK);
    }

    private void logNoRetryNotices(int loopCount) {
        XxlJobHelper.log(NoticeRetryConstants.LogMessages.NO_RETRY_NOTICES, loopCount);
        log.info(NoticeRetryConstants.LogMessages.NO_RETRY_NOTICES, loopCount);
    }

    private void logBatchStart(int loopCount, int batchSize) {
        XxlJobHelper.log("第 {} 次查询，需要重试的通知数量: {}", loopCount, batchSize);
        log.info(NoticeRetryConstants.LogMessages.BATCH_START, loopCount, batchSize);
    }

    private void logBatchResult(int loopCount, int batchSize, int batchSuccess, int batchFailed) {
        String batchResult = String.format("第 %d 批次并行处理完成: 处理 %d 条，成功 %d 条，失败 %d 条",
                loopCount, batchSize, batchSuccess, batchFailed);
        XxlJobHelper.log(batchResult);
        log.info(batchResult);
    }

    private void logFinalResult(int loopCount, int totalProcessed, int totalSuccess, int totalFailed,
                                boolean maxLoopReached, boolean maxProcessReached) {
        String finalResult;
        if (maxLoopReached) {
            finalResult = String.format("达到最大循环次数 %d，任务强制结束。总计处理: %d 条，成功: %d 条，失败: %d 条",
                    NoticeRetryConstants.MAX_LOOP_COUNT, totalProcessed, totalSuccess, totalFailed);
        } else if (maxProcessReached) {
            finalResult = String.format("达到最大处理条数 %d，任务强制结束。总计处理: %d 条，成功: %d 条，失败: %d 条",
                    NoticeRetryConstants.MAX_PROCESS_COUNT, totalProcessed, totalSuccess, totalFailed);
        } else {
            finalResult = String.format("通知重试任务执行完成，循环 %d 次，总计处理: %d 条，成功: %d 条，失败: %d 条",
                    loopCount, totalProcessed, totalSuccess, totalFailed);
        }
        XxlJobHelper.log(finalResult);
        log.info(finalResult);
    }

    private void handleTaskException(int totalProcessed, int totalSuccess, int totalFailed, Exception e) {
        String errorMsg = String.format("通知重试任务执行异常，已处理: %d 条，成功: %d 条，失败: %d 条，错误信息: %s",
                totalProcessed, totalSuccess, totalFailed, e.getMessage());
        XxlJobHelper.log(errorMsg);
        log.error(errorMsg, e);
    }
}
