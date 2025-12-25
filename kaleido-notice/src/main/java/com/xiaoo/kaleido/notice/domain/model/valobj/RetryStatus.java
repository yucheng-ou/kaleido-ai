package com.xiaoo.kaleido.notice.domain.model.valobj;

import com.xiaoo.kaleido.notice.types.enums.NoticeTypeEnum;
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
     * 下次重试时间
     */
    private final Date nextRetryAt;

    /**
     * 重试次数
     */
    private final Integer retryNum;

    public static RetryStatus init() {
        return create(null, 0);
    }

    public static RetryStatus create(Date nextRetryAt, Integer retryNum) {
        return RetryStatus.builder().nextRetryAt(nextRetryAt).retryNum(retryNum).build();
    }

    ;
}
