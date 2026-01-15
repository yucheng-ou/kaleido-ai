package com.xiaoo.kaleido.notice.domain.model.valobj;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * 重试状态值对象
 *
 * @author ouyucheng
 * @date 2025/12/19
 * @description
 */
@Getter
@Builder
public class RetryStatus {

    /**
     * 重试间隔时间 毫秒
     */
    private static final Integer retryIntervalTime = 10 * 1000;

    /**
     * 下次重试时间
     */
    private Date nextRetryAt;

    /**
     * 重试次数
     */
    private Integer retryNum;

    public static RetryStatus init() {
        return create(null, 0);
    }

    public static RetryStatus create(Date nextRetryAt, Integer retryNum) {
        return RetryStatus.builder().nextRetryAt(nextRetryAt).retryNum(retryNum).build();
    }

    public void update(Integer maxRetryNum) {
        if (retryNum >= maxRetryNum - 1) {
            nextRetryAt = null;
        } else {
            nextRetryAt = new Date(System.currentTimeMillis() + retryIntervalTime);
        }
        retryNum++;
    }
}
