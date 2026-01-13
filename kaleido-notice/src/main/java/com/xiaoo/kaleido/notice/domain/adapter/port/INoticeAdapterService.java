package com.xiaoo.kaleido.notice.domain.adapter.port;


import com.xiaoo.kaleido.base.result.Result;

/**
 * 短信服务接口（领域层定义）
 * 负责短信发送功能
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
public interface INoticeAdapterService {

    /**
     * 发送短信
     *
     * @param target  通知目标
     * @param content 通知内容
     * @return 是否发送成功
     */
    Result<Void> sendNotice(String target, String content);
}
