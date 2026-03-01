package com.xiaoo.kaleido.interview.application.service;

import com.xiaoo.kaleido.interview.domain.agent.*;
import com.xiaoo.kaleido.interview.domain.agent.model.IntentResult;
import com.xiaoo.kaleido.interview.types.config.RedisChatMemoryStore;
import dev.langchain4j.data.message.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 聊天应用服务 (Application Layer)
 * 负责协调多个 Agent 进行工作
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatApplicationService {

    private final IIntentAgent intentAgent;
    private final IGeneralChatAgent generalChatAgent;
    private final ICandidateAgent candidateAgent;
    private final IInterviewAgent interviewAgent;
    private final IOfferAgent offerAgent;
    private final RedisChatMemoryStore chatMemoryStore;

    /**
     * 处理聊天请求
     *
     * @param sessionId 会话ID
     * @param message   用户消息
     * @return AI回复
     */
    public String chat(String sessionId, String message) {
        log.info("收到聊天请求，SessionId: {}, Message: {}", sessionId, message);

        // 1. 获取历史记录（用于意图识别上下文）
        String chatHistory = getChatHistoryAsString(sessionId);

        // 2. 意图识别与查询改写
        IntentResult intentResult = intentAgent.analyzeIntent(chatHistory, message);
        log.info("意图识别结果: {}", intentResult);

        // 3. 根据意图路由到相应的 Agent
        String response;
        String queryToUse = intentResult.getRewrittenQuery() != null ? 
                            intentResult.getRewrittenQuery() : message;

        try {
            switch (intentResult.getIntentType()) {
                case CANDIDATE_QUERY:
                    response = candidateAgent.chat(sessionId, queryToUse);
                    break;
                case INTERVIEW_ARRANGEMENT:
                    response = interviewAgent.chat(sessionId, queryToUse);
                    break;
                case OFFER_SENDING:
                    response = offerAgent.chat(sessionId, queryToUse);
                    break;
                case GENERAL_CHAT:
                default:
                    response = generalChatAgent.chat(sessionId, message); // 普通聊天使用原始消息可能更自然
                    break;
            }
        } catch (Exception e) {
            log.error("Agent 执行出错: {}", e.getMessage(), e);
            response = "抱歉，系统暂时无法处理您的请求，请稍后再试。";
        }

        return response;
    }

    /**
     * 获取格式化的聊天历史
     */
    private String getChatHistoryAsString(String sessionId) {
        List<ChatMessage> messages = chatMemoryStore.getMessages(sessionId);
        if (messages == null || messages.isEmpty()) {
            return "无历史记录";
        }
        
        // 取最近 10 条记录
        int start = Math.max(0, messages.size() - 10);
        return messages.subList(start, messages.size()).stream()
                .map(msg -> msg.type() + ": " + msg.text())
                .collect(Collectors.joining("\n"));
    }
}
