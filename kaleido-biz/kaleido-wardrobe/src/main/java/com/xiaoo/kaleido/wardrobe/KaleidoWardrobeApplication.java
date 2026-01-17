package com.xiaoo.kaleido.wardrobe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.wardrobe")
public class KaleidoWardrobeApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoWardrobeApplication.class, args);
    }
}
