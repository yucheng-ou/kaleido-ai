package com.xiaoo.kaleido.sms.config;

import com.xiaoo.kaleido.sms.service.SmsService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author ouyucheng
 * @date 2025/12/17
 * @description
 */

@AutoConfiguration
@Import(SmsService.class)
public class SmsAutoConfiguration {
}
