package com.xiaoo.kaleido.tag.application.command;

import com.xiaoo.kaleido.api.tag.command.AssociateEntityCommand;
import com.xiaoo.kaleido.api.tag.command.CreateTagCommand;
import com.xiaoo.kaleido.api.tag.command.DissociateEntityCommand;
import com.xiaoo.kaleido.api.tag.command.UpdateTagCommand;
import com.xiaoo.kaleido.tag.domain.adapter.repository.ITagRepository;
import com.xiaoo.kaleido.tag.domain.model.aggregate.TagAggregate;
import com.xiaoo.kaleido.tag.domain.service.ITagDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 标签命令服务
 * <p>
 * 负责编排标签相关的命令操作，包括创建、更新、删除、关联实体等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TagCommandService {

    private final ITagDomainService tagDomainService;
    private final ITagRepository tagRepository;

    /**
     * 创建标签
     *
     * @param userId 用户ID
     * @param command 创建标签命令
     */
    public void createTag(String userId, CreateTagCommand command) {
        // 1.调用领域服务创建标签
        TagAggregate tag = tagDomainService.createTag(
                userId,
                command.getName(),
                command.getTypeCode(),
                command.getColor(),
                command.getDescription()
        );

        // 2.保存标签
        tagRepository.save(tag);

        // 3.记录日志
        log.info("标签创建成功，标签ID: {}, 用户ID: {}, 标签名称: {}",
                tag.getId(), userId, command.getName());
    }

    /**
     * 更新标签
     *
     * @param userId  用户ID
     * @param tagId   标签ID
     * @param command 更新标签命令
     */
    public void updateTag(String userId, String tagId, UpdateTagCommand command) {
        // 1.调用领域服务更新标签
        TagAggregate tag = tagDomainService.updateTag(
                userId,
                tagId,
                command.getName(),
                command.getColor(),
                command.getDescription()
        );

        // 2.保存标签
        tagRepository.update(tag);

        // 3.记录日志
        log.info("标签更新成功，标签ID: {}, 用户ID: {}, 新名称: {}", tagId, userId, command.getName());
    }


    /**
     * 关联实体到标签
     *
     * @param userId  用户ID
     * @param command 关联实体命令
     */
    public void associateEntity(String userId, AssociateEntityCommand command) {
        // 1.调用领域服务关联实体
        TagAggregate tag = tagDomainService.associateEntity(
                command.getTagId(),
                command.getEntityId(),
                userId,
                command.getEntityTypeCode()
        );

        // 2.保存标签
        tagRepository.insertRelation(tag);

        // 3.记录日志
        log.info("标签关联实体成功，标签ID: {}, 实体ID: {}, 用户ID: {}",
                command.getTagId(), command.getEntityId(), userId);
    }

    /**
     * 取消标签与实体的关联
     *
     * @param userId  用户ID
     * @param command 取消关联实体命令
     */
    public void dissociateEntity(String userId, DissociateEntityCommand command) {
        // 1.调用领域服务取消关联
        TagAggregate tag = tagDomainService.dissociateEntity(
                command.getTagId(),
                command.getEntityId(),
                userId
        );

        // 2.删除关联关系
        tagRepository.deleteRelation(tag);

        // 3.记录日志
        log.info("标签取消关联实体成功，标签ID: {}, 实体ID: {}, 用户ID: {}",
                command.getTagId(), command.getEntityId(), userId);
    }
}
