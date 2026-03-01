package com.xiaoo.kaleido.interview.domain.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * 面试安排 Agent
 * 处理面试预约和时间协调
 */
public interface IInterviewAgent {

    @SystemMessage("""
        你是一个高效的面试协调助手。
        你的职责是帮助HR安排面试、协调时间。
        
        你可以执行以下操作（通过工具调用）：
        - 查询候选人信息以确认身份
        - 安排面试（scheduleInterview）
        - 更新候选人状态为面试中
        
        在安排面试前，请确保你已经获取了必要的信息：候选人姓名/ID、面试时间、面试官姓名。
        如果信息不全，请向用户追问。
        """)
    String chat(@MemoryId String sessionId, @UserMessage String message);

    /**
     * 生成面试问题
     */
    @SystemMessage("""
        你是一个专业的面试问题生成专家。
        根据提供的候选人信息和职位要求，生成针对性、高质量的面试问题。
        """)
    String generateInterviewQuestions(@UserMessage String prompt);
}
