package com.xiaoo.kaleido.interview.application.service;

import com.xiaoo.kaleido.interview.domain.agent.*;
import com.xiaoo.kaleido.interview.domain.agent.model.IntentResult;
import com.xiaoo.kaleido.interview.types.config.RedisChatMemoryStore;
import dev.langchain4j.data.message.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

        // 1. 获取历史消息快照（用于后续回滚）
        List<ChatMessage> originalHistory = chatMemoryStore.getMessages(sessionId);

        // 2. 意图识别与查询改写
        // 注意：IntentAgent 会将 UserMessage 和 AiMessage(JSON) 写入内存
        IntentResult intentResult = intentAgent.analyzeIntent(sessionId, message);
        log.info("意图识别结果: {}", intentResult);

        // 3. 回滚内存状态
        // 移除 IntentAgent 产生的中间过程消息，避免污染主对话历史
        chatMemoryStore.updateMessages(sessionId, originalHistory);

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
}
