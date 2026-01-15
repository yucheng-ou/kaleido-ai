package com.xiaoo.kaleido.tag.application.command;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.api.tag.command.*;
import com.xiaoo.kaleido.tag.domain.adapter.repository.ITagRepository;
import com.xiaoo.kaleido.tag.domain.model.aggregate.TagAggregate;
import com.xiaoo.kaleido.tag.domain.service.ITagDomainService;
import com.xiaoo.kaleido.tag.types.exception.TagException;
import com.xiaoo.kaleido.tag.types.exception.TagErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签命令服务
 *
 * @author ouyucheng
 * @date 2026/1/15
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
     * @param command 创建标签命令
     */
    public void createTag(CreateTagCommand command) {
        // 1.调用领域服务创建标签
        TagAggregate tag = tagDomainService.createTag(
                command.getUserId(),
                command.getName(),
                command.getTypeCode(),
                command.getColor(),
                command.getDescription()
        );

        // 2.保存标签
        tagRepository.save(tag);

        // 3.记录日志
        log.info("标签创建成功，标签ID: {}, 用户ID: {}, 标签名称: {}",
                tag.getId(), command.getUserId(), command.getName());
    }

    /**
     * 更新标签
     *
     * @param command 更新标签命令
     */
    public void updateTag(String tagId,UpdateTagCommand command) {
        // 1.调用领域服务更新标签
        TagAggregate tag = tagDomainService.updateTag(
                tagId,
                command.getName(),
                command.getColor(),
                command.getDescription()
        );

        // 2.保存标签
        tagRepository.update(tag);

        // 3.记录日志
        log.info("标签更新成功，标签ID: {}, 新名称: {}", tagId, command.getName());
    }

    /**
     * 删除标签
     *
     * @param tagId 标签ID
     */
    public void deleteTag(String tagId) {
        // 1.调用领域服务删除标签
        TagAggregate tag = tagDomainService.deleteTag(tagId);

        // 2.保存标签
        tagRepository.update(tag);

        // 3.记录日志
        log.info("标签删除成功，标签ID: {}", tagId);
    }

    /**
     * 启用标签
     *
     * @param tagId 标签ID
     */
    public void enableTag(String tagId) {
        // 1.调用领域服务启用标签
        TagAggregate tag = tagDomainService.enableTag(tagId);

        // 2.保存标签
        tagRepository.update(tag);

        // 3.记录日志
        log.info("标签启用成功，标签ID: {}", tagId);
    }

    /**
     * 禁用标签
     *
     * @param tagId 标签ID
     */
    public void disableTag(String tagId) {
        // 1.调用领域服务禁用标签
        TagAggregate tag = tagDomainService.disableTag(tagId);

        // 2.保存标签
        tagRepository.update(tag);

        // 3.记录日志
        log.info("标签禁用成功，标签ID: {}", tagId);
    }

    /**
     * 关联标签
     *
     * @param command 关联标签命令
     * @return 成功关联的数量
     */
    public int associateTags(AssociateTagsCommand command) {
        // 1.调用领域服务关联标签
        int successCount = tagDomainService.associateTags(
                command.getTagIds(),
                command.getEntityId(),
                command.getUserId(),
                command.getEntityTypeCode()
        );

        // 2.记录日志
        log.info("标签关联完成，总标签数: {}, 成功关联数: {}, 实体ID: {}",
                command.getTagIds().size(), successCount, command.getEntityId());

        return successCount;
    }

    /**
     * 取消关联标签
     *
     * @param command 取消关联标签命令
     * @return 成功取消关联的数量
     */
    public int dissociateTags(DissociateTagsCommand command) {
        // 1.调用领域服务取消关联标签
        int successCount = tagDomainService.dissociateTags(
                command.getTagIds(),
                command.getEntityId(),
                command.getUserId()
        );

        // 2.记录日志
        log.info("标签取消关联完成，总标签数: {}, 成功取消关联数: {}, 实体ID: {}",
                command.getTagIds().size(), successCount, command.getEntityId());

        return successCount;
    }
}
