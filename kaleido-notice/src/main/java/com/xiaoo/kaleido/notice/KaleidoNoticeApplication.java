package com.xiaoo.kaleido.notice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.dromara.dynamictp.spring.annotation.EnableDynamicTp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ouyucheng
 * @date 2025/12/17
 * @description
 */

@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.notice")
@EnableDubbo
@EnableDynamicTp
public class KaleidoNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaleidoNoticeApplication.class, args);
    }

}
