package com.xiaoo.kaleido.interview.domain.agent;

import com.xiaoo.kaleido.interview.domain.agent.model.IntentResult;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 意图识别 Agent
 * 负责分析用户输入的意图，并进行查询改写
 */
public interface IIntentAgent {

    /**
     * 分析用户意图
     * @param sessionId   会话ID，用于关联上下文
     * @param userMessage 当前用户输入
     * @return 意图识别结果
     */
    @UserMessage("""
        请分析用户的意图，并将其分类为以下几种类型之一：
        1. GENERAL_CHAT: 日常闲聊、问候、与招聘业务无关的话题
        2. CANDIDATE_QUERY: 查询候选人信息、简历筛选、技能匹配等
        3. INTERVIEW_ARRANGEMENT: 安排面试、协调时间、查询面试日程
        4. OFFER_SENDING: 发送Offer、录用通知、薪资相关
        5. KNOWLEDGE_QUERY: 查询公司内部规章制度、报销标准、员工手册等非候选人信息
        
        同时，请根据上下文改写用户的查询，使其语义更完整、明确，便于后续处理。
        
        用户输入：
        {{userMessage}}
        
        请严格按照 JSON 格式返回结果，字段包括：intentType (枚举值), confidence (0-1小数), rewrittenQuery (字符串)。
        """)
    IntentResult analyzeIntent(@MemoryId String sessionId, @V("userMessage") String userMessage);
}
