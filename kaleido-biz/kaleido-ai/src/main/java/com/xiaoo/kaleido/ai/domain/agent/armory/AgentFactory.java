package com.xiaoo.kaleido.ai.domain.agent.armory;

import com.xiaoo.kaleido.ai.domain.agent.model.aggregate.AgentAggregate;
import com.xiaoo.kaleido.ai.domain.agent.model.vo.AgentStatus;
import com.xiaoo.kaleido.ai.domain.agent.adapter.repository.IAgentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * Agent注册中心
 * <p>
 * 负责管理Agent的注册状态和ChatClient实例
 * 提供线程安全的缓存管理和懒加载机制
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Slf4j
@Component
public class AgentFactory {

    /**
     * ChatClient缓存
     * key: Agent ID
     * value: ChatClient实例
     */
    private final Cache<String, ChatClient> chatClientCache;

    /**
     * Agent配置缓存
     * key: Agent ID
     * value: Agent配置
     */
    private final Cache<String, AgentAggregate> agentConfigCache;

    private final AgentChatClientArmory chatClientFactory;
    private final IAgentRepository agentRepository;

    /**
     * 构造函数，初始化Caffeine缓存
     */
    public AgentFactory(AgentChatClientArmory chatClientFactory, IAgentRepository agentRepository) {
        this.chatClientFactory = chatClientFactory;
        this.agentRepository = agentRepository;
        
        // 初始化ChatClient缓存，最大容量1000
        this.chatClientCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .build();
            
        // 初始化Agent配置缓存，最大容量1000
        this.agentConfigCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .build();
    }

    /**
     * 初始化方法 - 启动时加载所有已启用的Agent
     */
    @PostConstruct
    public void init() {
        log.info("开始初始化Agent注册中心...");
        loadEnabledAgents();
        log.info("Agent注册中心初始化完成，已加载Agent数量: {}", chatClientCache.estimatedSize());
    }

    /**
     * 注册Agent
     *
     * @param agentId Agent ID
     */
    public void registerAgent(String agentId) {
        if (isAgentRegistered(agentId)) {
            log.debug("Agent已注册，跳过注册，Agent ID: {}", agentId);
            return;
        }

        AgentAggregate agent = loadAgent(agentId);
        if (agent == null) {
            log.warn("Agent不存在，无法注册，Agent ID: {}", agentId);
            return;
        }

        if (agent.getStatus() != AgentStatus.NORMAL) {
            log.warn("Agent状态异常，无法注册，Agent ID: {}, 状态: {}", agentId, agent.getStatus());
            return;
        }

        try {
            ChatClient chatClient = chatClientFactory.createChatClient(agent);
            chatClientCache.put(agentId, chatClient);
            agentConfigCache.put(agentId, agent);
            log.info("Agent注册成功，Agent ID: {}, 名称: {}", agentId, agent.getName());
        } catch (Exception e) {
            log.error("Agent注册失败，Agent ID: {}, 错误: {}", agentId, e.getMessage(), e);
        }
    }

    /**
     * 注销Agent
     *
     * @param agentId Agent ID
     */
    public void unregisterAgent(String agentId) {
        // 检查是否存在于缓存中
        boolean wasPresent = chatClientCache.getIfPresent(agentId) != null;
        
        // 从两个缓存中移除
        chatClientCache.invalidate(agentId);
        agentConfigCache.invalidate(agentId);

        if (wasPresent) {
            log.info("Agent注销成功，Agent ID: {}", agentId);
        } else {
            log.debug("Agent未注册，无需注销，Agent ID: {}", agentId);
        }
    }

    /**
     * 获取ChatClient
     *
     * @param agentId Agent ID
     * @return ChatClient实例，如果不存在或状态异常则返回null
     */
    public ChatClient getChatClient(String agentId) {
        // 先尝试从缓存获取
        ChatClient chatClient = chatClientCache.getIfPresent(agentId);
        if (chatClient != null) {
            return chatClient;
        }

        // 缓存未命中，检查agent是否存在且状态正常
        AgentAggregate agent = loadAgent(agentId);
        if (agent == null || agent.getStatus() != AgentStatus.NORMAL) {
            return null;
        }

        // 使用get方法，如果不存在则创建
        try {
            chatClient = chatClientCache.get(agentId, id -> {
                ChatClient newChatClient = chatClientFactory.createChatClient(agent);
                agentConfigCache.put(id, agent);
                log.debug("ChatClient懒加载成功，Agent ID: {}", id);
                return newChatClient;
            });
            return chatClient;
        } catch (Exception e) {
            log.error("创建ChatClient失败，Agent ID: {}, 错误: {}", agentId, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 刷新Agent配置
     *
     * @param agentId Agent ID
     */
    public void refreshAgent(String agentId) {
        log.debug("开始刷新Agent配置，Agent ID: {}", agentId);
        unregisterAgent(agentId);
        registerAgent(agentId);
    }

    /**
     * 检查Agent是否已注册
     *
     * @param agentId Agent ID
     * @return 如果已注册返回true，否则返回false
     */
    public boolean isAgentRegistered(String agentId) {
        return chatClientCache.getIfPresent(agentId) != null;
    }

    /**
     * 清理无效的Agent缓存
     * 定时任务，每小时执行一次
     */
    @Scheduled(fixedRate = 3600000) // 每小时执行一次
    public void cleanupInvalidAgents() {
        log.debug("开始清理无效Agent缓存...");
        long initialSize = chatClientCache.estimatedSize();

        // 获取所有缓存的key并检查有效性
        chatClientCache.asMap().keySet().forEach(agentId -> {
            AgentAggregate agent = loadAgent(agentId);
            if (agent == null || agent.getStatus() != AgentStatus.NORMAL) {
                log.debug("清理无效Agent缓存，Agent ID: {}", agentId);
                chatClientCache.invalidate(agentId);
                agentConfigCache.invalidate(agentId);
            }
        });

        long cleanedCount = initialSize - chatClientCache.estimatedSize();
        if (cleanedCount > 0) {
            log.info("Agent缓存清理完成，清理数量: {}", cleanedCount);
        }
    }

    /**
     * 加载Agent
     */
    private AgentAggregate loadAgent(String agentId) {
        return agentRepository.findById(agentId);
    }

    /**
     * 加载所有已启用的Agent
     */
    private void loadEnabledAgents() {
        try {
            List<AgentAggregate> enabledAgents = agentRepository.findAllEnabled();
            log.info("发现{}个已启用的Agent", enabledAgents.size());

            for (AgentAggregate agent : enabledAgents) {
                try {
                    registerAgent(agent.getId());
                } catch (Exception e) {
                    log.error("加载Agent失败，Agent ID: {}, 错误: {}", agent.getId(), e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.error("加载已启用Agent列表失败，错误: {}", e.getMessage(), e);
        }
    }
}
