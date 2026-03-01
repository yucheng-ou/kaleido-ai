package com.xiaoo.kaleido.interview.domain.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
 * 企业知识问答 Agent
 * 负责查询公司内部文档、规章制度等
 */
public interface IKnowledgeAgent {

    @SystemMessage("""
        你是一个专业的企业知识顾问。
        你的职责是帮助员工解答关于公司规章制度、报销流程、员工手册等问题。
        你可以访问公司内部知识库（通过 RAG）。
        
        请基于检索到的文档内容回答，如果文档中没有相关信息，请如实告知，不要编造。
        """)
    Flux<String> chat(@MemoryId String sessionId, @UserMessage String message);
}
