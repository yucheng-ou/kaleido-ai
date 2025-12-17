package com.xiaoo.kaleido.sms.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ouyucheng
 * @date 2025/12/17
 * @description
 */

@Slf4j
public class SmsService {

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param msg    短信内容
     * @return 是否发送成功
     */
    public Boolean sendSmsMsg(String mobile, String msg) {
        //TODO 后续集成阿里云短信服务

        return true;
    }

}
