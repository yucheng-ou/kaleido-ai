package com.xiaoo.kaleido.coin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.coin")
public class KaleidoCoinApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoCoinApplication.class, args);
    }
}
