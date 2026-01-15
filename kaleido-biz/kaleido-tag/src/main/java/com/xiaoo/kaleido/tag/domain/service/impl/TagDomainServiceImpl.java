package com.xiaoo.kaleido.tag.domain.service.impl;

import cn.hutool.core.util.StrUtil;
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
 * 标签领域服务实现
 * <p>
 * 实现标签领域服务的核心业务逻辑，处理标签的创建、更新、删除、关联等操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TagDomainServiceImpl implements ITagDomainService {

    private final ITagRepository tagRepository;

    @Override
    public TagAggregate createTag(String userId, String name, String typeCode, String color, String description) {
        // 1.参数校验
        validateCreateParams(userId, name, typeCode);

        // 2.验证标签名称在用户下的唯一性（同类型）
        if (!isTagNameUnique(userId, name, typeCode)) {
            throw TagException.of(TagErrorCode.TAG_NAME_EXISTS);
        }

        // 3.创建标签聚合根
        TagAggregate tag = TagAggregate.create(userId, name, typeCode, color, description);

        // 4.保存标签
        tagRepository.save(tag);

        // 5.记录日志
        log.info("标签创建成功，标签ID: {}, 用户ID: {}, 标签名称: {}",
                tag.getId(), userId, name);

        return tag;
    }

    @Override
    public TagAggregate findByIdOrThrow(String tagId) {
        // 1.参数校验
        if (StrUtil.isBlank(tagId)) {
            throw TagException.of(TagErrorCode.TAG_ID_NOT_NULL);
        }

        // 2.从仓储层获取标签
        return tagRepository.findByIdOrThrow(tagId);
    }

    @Override
    public TagAggregate updateTag(String tagId, String name, String color, String description) {
        // 1.参数校验
        if (StrUtil.isBlank(tagId)) {
            throw TagException.of(TagErrorCode.TAG_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(name)) {
            throw TagException.of(TagErrorCode.TAG_NAME_EMPTY);
        }

        // 2.查询标签
        TagAggregate tag = findByIdOrThrow(tagId);

        // 3.更新标签信息
        tag.updateInfo(name, color, description);

        // 4.保存标签
        tagRepository.update(tag);

        // 5.记录日志
        log.info("标签信息更新成功，标签ID: {}, 新名称: {}", tagId, name);

        return tag;
    }

    @Override
    public TagAggregate deleteTag(String tagId) {
        // 1.参数校验
        if (StrUtil.isBlank(tagId)) {
            throw TagException.of(TagErrorCode.TAG_ID_NOT_NULL);
        }

        // 2.查询标签
        TagAggregate tag = findByIdOrThrow(tagId);

        // 3.删除标签（软删除）
        tag.delete();

        // 4.保存标签
        tagRepository.update(tag);

        // 5.记录日志
        log.info("标签删除成功，标签ID: {}", tagId);

        return tag;
    }

    @Override
    public TagAggregate enableTag(String tagId) {
        // 1.参数校验
        if (StrUtil.isBlank(tagId)) {
            throw TagException.of(TagErrorCode.TAG_ID_NOT_NULL);
        }

        // 2.查询标签
        TagAggregate tag = findByIdOrThrow(tagId);

        // 3.启用标签
        tag.enable();

        // 4.保存标签
        tagRepository.update(tag);

        // 5.记录日志
        log.info("标签启用成功，标签ID: {}", tagId);

        return tag;
    }

    @Override
    public TagAggregate disableTag(String tagId) {
        // 1.参数校验
        if (StrUtil.isBlank(tagId)) {
            throw TagException.of(TagErrorCode.TAG_ID_NOT_NULL);
        }

        // 2.查询标签
        TagAggregate tag = findByIdOrThrow(tagId);

        // 3.禁用标签
        tag.disable();

        // 4.保存标签
        tagRepository.update(tag);

        // 5.记录日志
        log.info("标签禁用成功，标签ID: {}", tagId);

        return tag;
    }

    @Override
    public int associateTags(List<String> tagIds, String entityId, String userId, String entityTypeCode) {
        // 1.参数校验
        validateAssociateParams(tagIds, entityId, userId, entityTypeCode);

        int successCount = 0;

        // 2.遍历所有标签，逐个关联
        for (String tagId : tagIds) {
            try {
                // 2.1 查询标签
                TagAggregate tag = findByIdOrThrow(tagId);

                // 2.2 验证标签是否属于当前用户
                if (!tag.getUserId().equals(userId)) {
                    log.warn("标签不属于当前用户，跳过关联，标签ID: {}, 用户ID: {}", tagId, userId);
                    continue;
                }

                // 2.3 验证标签类型与实体类型是否匹配
                if (!tag.canAssociateWith(entityTypeCode)) {
                    log.warn("标签类型与实体类型不匹配，跳过关联，标签ID: {}, 标签类型: {}, 实体类型: {}",
                            tagId, tag.getTypeCode(), entityTypeCode);
                    continue;
                }

                // 2.4 关联实体
                tag.associateEntity(entityId, userId);

                // 2.5 更新标签关联关系
                tagRepository.updateTagRelations(tagId, tag.getRelations());

                successCount++;
                log.debug("标签关联成功，标签ID: {}, 实体ID: {}", tagId, entityId);

            } catch (Exception e) {
                log.warn("标签关联失败，标签ID: {}, 实体ID: {}, 原因: {}", tagId, entityId, e.getMessage());
            }
        }

        // 3.记录日志
        log.info("批量关联完成，总标签数: {}, 成功关联数: {}, 实体ID: {}",
                tagIds.size(), successCount, entityId);

        return successCount;
    }

    @Override
    public int dissociateTags(List<String> tagIds, String entityId, String userId) {
        // 1.参数校验
        if (tagIds == null || tagIds.isEmpty()) {
            throw TagException.of(TagErrorCode.BATCH_OPERATION_PARAM_ERROR, "标签ID列表不能为空");
        }
        if (StrUtil.isBlank(entityId)) {
            throw TagException.of(TagErrorCode.ENTITY_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(userId)) {
            throw TagException.of(TagErrorCode.USER_ID_NOT_NULL);
        }

        int successCount = 0;

        // 2.遍历所有标签，逐个取消关联
        for (String tagId : tagIds) {
            try {
                // 2.1 查询标签
                TagAggregate tag = findByIdOrThrow(tagId);

                // 2.2 验证标签是否属于当前用户
                if (!tag.getUserId().equals(userId)) {
                    log.warn("标签不属于当前用户，跳过取消关联，标签ID: {}, 用户ID: {}", tagId, userId);
                    continue;
                }

                // 2.3 取消关联
                boolean removed = tag.dissociateEntity(entityId);

                if (removed) {
                    // 2.4 更新标签关联关系
                    tagRepository.updateTagRelations(tagId, tag.getRelations());

                    successCount++;
                    log.debug("标签取消关联成功，标签ID: {}, 实体ID: {}", tagId, entityId);
                } else {
                    log.debug("标签未关联该实体，无需取消关联，标签ID: {}, 实体ID: {}", tagId, entityId);
                }

            } catch (Exception e) {
                log.warn("标签取消关联失败，标签ID: {}, 实体ID: {}, 原因: {}", tagId, entityId, e.getMessage());
            }
        }

        // 3.记录日志
        log.info("批量取消关联完成，总标签数: {}, 成功取消关联数: {}, 实体ID: {}",
                tagIds.size(), successCount, entityId);

        return successCount;
    }

    @Override
    public boolean isTagNameUnique(String userId, String name, String typeCode) {
        // 1.参数校验
        if (StrUtil.isBlank(userId)) {
            throw TagException.of(TagErrorCode.USER_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(name)) {
            throw TagException.of(TagErrorCode.TAG_NAME_EMPTY);
        }
        if (StrUtil.isBlank(typeCode)) {
            throw TagException.of(TagErrorCode.TAG_TYPE_CODE_EMPTY);
        }

        // 2.从仓储层检查标签名称唯一性
        return !tagRepository.existsByUserIdAndNameAndTypeCode(userId, name, typeCode);
    }

    /**
     * 验证创建标签参数
     */
    private void validateCreateParams(String userId, String name, String typeCode) {
        if (StrUtil.isBlank(userId)) {
            throw TagException.of(TagErrorCode.USER_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(name)) {
            throw TagException.of(TagErrorCode.TAG_NAME_EMPTY);
        }
        if (StrUtil.isBlank(typeCode)) {
            throw TagException.of(TagErrorCode.TAG_TYPE_CODE_EMPTY);
        }
    }

    /**
     * 验证关联标签参数
     */
    private void validateAssociateParams(List<String> tagIds, String entityId, String userId, String entityTypeCode) {
        if (tagIds == null || tagIds.isEmpty()) {
            throw TagException.of(TagErrorCode.BATCH_OPERATION_PARAM_ERROR, "标签ID列表不能为空");
        }
        if (StrUtil.isBlank(entityId)) {
            throw TagException.of(TagErrorCode.ENTITY_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(userId)) {
            throw TagException.of(TagErrorCode.USER_ID_NOT_NULL);
        }
        if (StrUtil.isBlank(entityTypeCode)) {
            throw TagException.of(TagErrorCode.ENTITY_TYPE_CODE_NOT_NULL);
        }
    }
}
