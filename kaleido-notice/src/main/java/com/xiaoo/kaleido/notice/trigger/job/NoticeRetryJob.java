package com.xiaoo.kaleido.notice.trigger.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class NoticeRetryJob {

    /**
     * 通知推送重试计划任务
     */
    @XxlJob("noticeRetryExecute")
    public void orderTimeOutExecute() {
        System.out.println("noticeRetryExecute...");
        //1.读取所有符合推送条件的通知
        //2.执行推送
        //3.更新推送结果
    }

}
