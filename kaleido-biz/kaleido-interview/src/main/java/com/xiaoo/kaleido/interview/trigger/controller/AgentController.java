package com.xiaoo.kaleido.interview.trigger.controller;

import com.xiaoo.kaleido.api.interview.command.ChatCommand;
import com.xiaoo.kaleido.interview.application.service.ChatApplicationService;
import com.xiaoo.kaleido.interview.application.command.ResumeCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * 招聘助手Agent控制器
 * 提供AI对话接口
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {

    private final ChatApplicationService chatApplicationService;
    private final ResumeCommandService resumeCommandService;

    /**
     * 核心对话接口
     * <p>
     * 集成意图识别、多Agent协作
     *
     * @param command 聊天命令（包含sessionId和message）
     * @return AI回复
     */
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@Valid @RequestBody ChatCommand command) {
        log.info("收到对话请求，sessionId: {}, message: {}", command.getSessionId(), command.getMessage());
        return chatApplicationService.chat(command.getSessionId(), command.getMessage());
    }

    /**
     * 生成面试问题
     *
     * @param candidateId    候选人ID
     * @param jobDescription 职位JD
     * @return 生成的面试问题
     */
    @PostMapping("/interview-questions")
    public String generateInterviewQuestions(
            @RequestParam String candidateId,
            @RequestParam String jobDescription) {
        log.info("生成面试问题，候选人ID: {}", candidateId);
        return resumeCommandService.generateInterviewQuestions(candidateId, jobDescription);
    }
}

