package com.xiaoo.kaleido.interview.domain.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * 通用聊天 Agent
 * 处理日常对话和非业务相关问题
 */
public interface IGeneralChatAgent {

    @SystemMessage("""
        你是一个友好的招聘助手。
        你的职责是与用户进行自然的日常对话，回答非业务类的通用问题。
        请保持礼貌、专业且幽默的风格。
        如果用户问及具体的候选人、面试或Offer相关问题，请礼貌地引导他们提供更多信息，或者告知你将转接给专门的业务助手（虽然实际上是由系统调度，但你可以假装是你转接）。
        """)
    String chat(@MemoryId String sessionId, @UserMessage String message);
}
