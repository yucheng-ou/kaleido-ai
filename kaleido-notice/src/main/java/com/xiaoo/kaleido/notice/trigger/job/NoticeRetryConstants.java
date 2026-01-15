package com.xiaoo.kaleido.notice.trigger.job;

/**
 * 通知重试任务常量配置
 *
 * @author ouyucheng
 * @date 2026/1/14
 */
public final class NoticeRetryConstants {

    private NoticeRetryConstants() {
        // 防止实例化
    }

    /**
     * 每次查询的重试通知数量
     */
    public static final int RETRY_BATCH_SIZE = 200;

    /**
     * 最大循环次数，防止无限循环
     */
    public static final int MAX_LOOP_COUNT = 100;

    /**
     * 最大处理条数，防止一次任务处理过多数据
     */
    public static final int MAX_PROCESS_COUNT = 10000;

    /**
     * 并行处理超时时间（秒）
     */
    public static final int PARALLEL_TIMEOUT_SECONDS = 300;


    /**
     * 任务执行结果状态
     */
    public enum RetryStatus {
        SUCCESS,
        FAILED,
        TIMEOUT,
        EXCEPTION
    }

    /**
     * 日志消息模板
     */
    public static final class LogMessages {
        public static final String START_TASK = "开始执行通知推送重试任务（Dynamic-TP并行处理）";
        public static final String NO_RETRY_NOTICES = "第 {} 次查询，没有需要重试的通知，结束循环";
        public static final String BATCH_START = "第 {} 次查询，开始并行处理 {} 条需要重试的通知";
        public static final String BATCH_RESULT = "第 {} 批次并行处理完成: 处理 {} 条，成功 {} 条，失败 {} 条";
        public static final String PARALLEL_TIMEOUT = "并行处理超时或异常，已处理部分任务";
        public static final String TASK_INCOMPLETE = "任务未完成或异常，通知索引: {}";
        public static final String THREAD_POOL_STATUS = "线程池状态: 核心线程={}, 最大线程={}, 活跃线程={}, 队列大小={}, 队列剩余容量={}, 完成任务数={}";
        public static final String THREAD_POOL_STATUS_FAILED = "获取线程池状态失败";

        private LogMessages() {
            // 防止实例化
        }
    }
}
