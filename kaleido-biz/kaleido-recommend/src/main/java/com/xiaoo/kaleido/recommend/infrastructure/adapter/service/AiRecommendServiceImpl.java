package com.xiaoo.kaleido.recommend.infrastructure.adapter.service;

import com.xiaoo.kaleido.recommend.domain.recommend.adapter.service.IAiRecommendService;
import com.xiaoo.kaleido.recommend.types.exception.RecommendErrorCode;
import com.xiaoo.kaleido.recommend.types.exception.RecommendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * AI推荐服务实现（基础设施层）
 * <p>
 * 调用外部AI服务生成穿搭推荐
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiRecommendServiceImpl implements IAiRecommendService {

    private final RestTemplate restTemplate;

    @Value("${ai.service.url:http://localhost:8081/api/ai/generate}")
    private String aiServiceUrl;

    @Value("${ai.service.timeout:30000}")
    private int timeout;

    @Value("${ai.service.enabled:true}")
    private boolean enabled;

    @Override
    public String generateOutfitRecommendation(String prompt) {
        if (!enabled) {
            log.warn("AI服务未启用，跳过穿搭推荐生成");
            throw RecommendException.of(RecommendErrorCode.AI_SERVICE_UNAVAILABLE, "AI服务未启用");
        }

        try {
            // 1.准备请求参数
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("prompt", prompt);
            requestBody.put("model", "outfit_generator_v1");

            // 2.设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // 3.调用AI服务
            log.info("调用AI服务生成穿搭推荐，提示词长度: {}", prompt.length());
            ResponseEntity<Map> response = restTemplate.exchange(
                    aiServiceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // 4.处理响应
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                String outfitId = (String) responseBody.get("outfitId");
                if (outfitId != null && !outfitId.trim().isEmpty()) {
                    log.info("AI服务生成穿搭推荐成功，穿搭ID: {}", outfitId);
                    return outfitId;
                } else {
                    log.error("AI服务返回的穿搭ID为空，响应: {}", responseBody);
                    throw RecommendException.of(RecommendErrorCode.AI_SERVICE_ERROR, "AI服务返回的穿搭ID为空");
                }
            } else {
                log.error("AI服务调用失败，状态码: {}, 响应: {}", response.getStatusCode(), response.getBody());
                throw RecommendException.of(RecommendErrorCode.AI_SERVICE_ERROR, "AI服务调用失败");
            }
        } catch (RecommendException e) {
            throw e;
        } catch (Exception e) {
            log.error("AI服务调用异常，提示词: {}, 原因: {}", prompt, e.getMessage(), e);
            throw RecommendException.of(RecommendErrorCode.AI_SERVICE_ERROR, "AI服务调用异常: " + e.getMessage());
        }
    }

    @Override
    public boolean isServiceAvailable() {
        if (!enabled) {
            return false;
        }

        try {
            // 简单的健康检查
            ResponseEntity<String> response = restTemplate.getForEntity(aiServiceUrl.replace("/generate", "/health"), String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            log.warn("AI服务健康检查失败，原因: {}", e.getMessage());
            return false;
        }
    }
}
