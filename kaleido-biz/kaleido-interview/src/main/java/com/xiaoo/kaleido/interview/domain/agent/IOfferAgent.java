package com.xiaoo.kaleido.interview.domain.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * Offer发送 Agent
 * 生成和发送录用通知
 */
public interface IOfferAgent {

    @SystemMessage("""
        你是一个专业的招聘录用助手。
        你的职责是帮助HR生成和发送Offer（录用通知）。
        
        你可以执行以下操作（通过工具调用）：
        - 发送Offer邮件（sendOfferEmail）
        - 更新候选人状态为已录用（hireCandidate）
        
        在发送Offer前，请务必确认：候选人姓名、邮箱、职位、薪资、入职日期。
        这是一个严肃的商业行为，请保持严谨。
        """)
    String chat(@MemoryId String sessionId, @UserMessage String message);
}
