package com.xiaoo.langchain4j.demo.controller.chat;

import com.xiaoo.langchain4j.demo.service.ChatService;
import com.xiaoo.langchain4j.demo.tools.TemperatureTools;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.service.AiServices;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * Langchain4j 演示聊天控制器
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@Slf4j
@Validated
@RequestMapping("/langchain")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiChatModel chatModel;
    private final ChatService chatService;
    private final OpenAiImageModel imageModel;

    /**
     * 直接使用 OpenAiChatModel 进行聊天
     *
     * @param userMessage 用户消息，不能为空
     * @return AI 生成的回复
     */
    @GetMapping("/chat")
    public String chat(@RequestParam @NotBlank String userMessage) {
        log.info("收到聊天请求: {}", userMessage);
        return chatModel.chat(userMessage);
    }

    /**
     * 图片解释 - 分析图片内容
     *
     * @param imageUrl 图片URL
     * @param question 关于图片的问题（可选，默认为"请描述这张图片的内容"）
     * @return AI 对图片的解释
     */
    @GetMapping("/image/analyze")
    public ChatResponse analyzeImage(
            @RequestParam @NotBlank String imageUrl,
            @RequestParam(defaultValue = "请描述这张图片的内容") String question
    ) {
        // 创建包含图片和问题的用户消息
        UserMessage userMessage = UserMessage.from(
                TextContent.from(question),
                ImageContent.from(imageUrl)
        );

        // 发送消息并获取AI响应
        return chatModel.chat(userMessage);
    }

    /**
     * 通过 AI 服务进行聊天
     *
     * @param userMessage 用户消息，不能为空
     * @return AI 生成的回复
     */
    @PostMapping("/chatByAiService")
    public String chatByAiService(@RequestParam @NotBlank String userMessage) {
        log.info("通过 AI 服务聊天: {}", userMessage);
        return chatService.chat(userMessage);
    }

    /**
     * 通过 AI 服务进行流式聊天
     *
     * @param userMessage 用户消息，不能为空
     * @return 流式 AI 回复
     */
    @PostMapping("/chatStream")
    public Flux<String> chatStream(@RequestParam @NotBlank String userMessage) {
        log.info("通过 AI 服务流式聊天: {}", userMessage);
        return chatService.chatStream(userMessage);
    }

    /**
     * 天气查询接口 - 根据城市查询当天天气
     *
     * @param city 城市名称，不能为空
     * @return 当天天气信息
     */
    @GetMapping("/weather")
    public String getWeather(@RequestParam @NotBlank String city) {
        ChatService langChainAiService = AiServices.builder(ChatService.class)
                .tools(new TemperatureTools())
                .chatModel(chatModel)
                .build();

        return langChainAiService.chat(city);
    }
}
