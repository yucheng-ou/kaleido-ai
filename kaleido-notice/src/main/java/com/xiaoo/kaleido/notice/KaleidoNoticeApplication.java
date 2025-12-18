package com.xiaoo.kaleido.notice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ouyucheng
 * @date 2025/12/17
 * @description
 */

@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.sms")
public class KaleidoNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaleidoNoticeApplication.class, args);
    }

}
