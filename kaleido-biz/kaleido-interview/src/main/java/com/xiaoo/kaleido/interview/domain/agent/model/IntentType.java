package com.xiaoo.kaleido.interview.domain.agent.model;

/**
 * 意图类型枚举
 */
public enum IntentType {
    /**
     * 普通聊天
     */
    GENERAL_CHAT("普通聊天", "处理日常问候、非业务相关话题"),

    /**
     * 候选人查询
     */
    CANDIDATE_QUERY("候选人查询", "查询候选人信息、简历详情、技能匹配度等"),

    /**
     * 面试安排
     */
    INTERVIEW_ARRANGEMENT("面试安排", "预约面试、协调面试时间、查看面试日程"),

    /**
     * Offer发送
     */
    OFFER_SENDING("Offer发送", "生成和发送录用通知、薪资谈判辅助");

    private final String description;
    private final String detail;

    IntentType(String description, String detail) {
        this.description = description;
        this.detail = detail;
    }

    public String getDescription() {
        return description;
    }
}
