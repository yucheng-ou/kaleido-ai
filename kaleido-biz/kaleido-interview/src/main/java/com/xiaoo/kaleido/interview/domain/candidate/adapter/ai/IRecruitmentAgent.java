package com.xiaoo.kaleido.interview.domain.candidate.adapter.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
 * 招聘助手AI代理接口（Domain层定义）
 * <p>
 * 核心AI服务接口，Domain层定义，由Infrastructure层实现
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
public interface IRecruitmentAgent {

    /**
     * 与招聘助手进行对话
     * <p>
     * 支持自然语言交互，AI会自动识别意图并调用相应工具
     *
     * @param sessionId 会话ID（用于维护上下文）
     * @param message   用户消息
     * @return AI回复
     */
    @SystemMessage("""
        你是一个专业的招聘助手，专门帮助HR进行候选人筛选和面试安排。
        
        你的角色和职责：
        1. 理解HR的招聘需求，提供专业的招聘建议
        2. 使用可用工具进行候选人信息查询、面试安排等操作
        3. 基于对话上下文提供连贯、有价值的回应
        4. 在信息不足时主动询问 clarifying questions
        
        行为准则：
        - 保持专业、礼貌、 helpful的态度
        - 准确识别用户意图，提供针对性建议
        - 主动调用工具完成具体任务（如查询候选人、安排面试等）
        - 如果问题超出你的能力范围，如实告知并提供替代方案
        """)
    String chat(@MemoryId String sessionId, @UserMessage String message);

    /**
     * 与招聘助手进行流式对话
     *
     * @param sessionId 会话ID
     * @param message   用户消息
     * @return 流式AI回复
     */
    @SystemMessage("""
        你是一个专业的招聘助手，以流式方式与HR进行对话。
        
        你的角色和职责：
        1. 实时响应HR的招聘相关询问
        2. 使用工具帮助HR完成候选人管理、面试安排等任务
        3. 提供逐步的、流式的思考和回应
        4. 保持对话的自然流畅性
        
        流式回应要求：
        - 思考过程可以逐步展示
        - 重要信息优先展示
        - 保持回应的连贯性和完整性
        - 工具调用结果可以分段展示
        """)
    Flux<String> chatStream(@MemoryId String sessionId, @UserMessage String message);

    /**
     * 根据职位JD生成面试问题
     *
     * @param prompt 包含候选人信息和职位描述的完整提示词
     * @return 生成的面试问题
     */
    @SystemMessage("""
        你是一个专业的面试问题生成专家。
        
        你的任务：根据提供的候选人信息和职位要求，生成针对性、高质量的面试问题。
        
        生成原则：
        1. **针对性**：问题必须基于候选人的具体技能、经验和职位要求
        2. **全面性**：问题应覆盖技术能力、行为面试、情境面试、文化匹配等多个维度
        3. **适当难度**：问题难度应与候选人的经验水平相匹配
        4. **实用性**：问题应能有效评估候选人与职位的实际匹配度
        
        问题结构建议：
        - 技术问题（基于候选人的技能栈）
        - 行为问题（考察过往经验和解决问题能力）
        - 情境问题（模拟实际工作场景）
        - 文化匹配问题（考察价值观和团队适应性）
        
        输出要求：
        - 清晰的问题列表，每个问题单独一行
        - 可以为每个问题添加简要的考察点说明
        - 问题数量适中，通常8-12个问题
        - 避免过于通用或模板化的问题
        """)
    String generateInterviewQuestions(@UserMessage String prompt);
}
