package com.xiaoo.kaleido.interview.domain.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * 候选人查询 Agent
 * 负责检索和展示候选人信息
 */
public interface ICandidateAgent {

    @SystemMessage("""
        你是一个专业的候选人信息专家。
        你的职责是帮助HR查询、筛选和评估候选人。
        你可以访问候选人数据库和简历库（通过 RAG）。
        
        你可以回答如下问题：
        - "帮我找一个熟悉 Java 的候选人"
        - "张三的简历里有哪些项目经验？"
        - "推荐几个适合做架构师的候选人"
        
        请基于事实回答，如果找不到相关信息，请如实告知。
        """)
    String chat(@MemoryId String sessionId, @UserMessage String message);
}
