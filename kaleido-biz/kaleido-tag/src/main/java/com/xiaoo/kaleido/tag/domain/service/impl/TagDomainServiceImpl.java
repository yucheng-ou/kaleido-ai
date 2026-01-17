package com.xiaoo.kaleido.tag.domain.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.tag.domain.adapter.repository.ITagRepository;
import com.xiaoo.kaleido.tag.domain.model.aggregate.TagAggregate;
import com.xiaoo.kaleido.tag.domain.model.entity.TagRelation;
import com.xiaoo.kaleido.tag.domain.service.ITagDomainService;
import com.xiaoo.kaleido.tag.types.exception.TagException;
import com.xiaoo.kaleido.tag.types.exception.TagErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        // 1.验证标签名称在用户下的唯一性（同类型）
        if (!isTagNameUnique(userId, name, typeCode)) {
            throw TagException.of(TagErrorCode.TAG_NAME_EXISTS);
        }

        // 2.创建标签聚合根
        TagAggregate tag = TagAggregate.create(userId, name, typeCode, color, description);

        // 3.记录日志
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

        // 2.从仓储层获取标签（不加载关联关系，按需加载）
        return tagRepository.findById(tagId)
                .orElseThrow(() -> TagException.of(TagErrorCode.TAG_NOT_FOUND));
    }

    @Override
    public TagAggregate updateTag(String tagId, String name, String color, String description) {
        // 1.查询标签（findByIdOrThrow内部会校验tagId）
        TagAggregate tag = findByIdOrThrow(tagId);

        // 2.更新标签信息
        tag.updateInfo(name, color, description);

        // 3.记录日志
        log.info("标签信息更新成功，标签ID: {}, 新名称: {}", tagId, name);

        return tag;
    }



    @Override
    public TagAggregate associateEntity(String tagId, String entityId, String userId, String entityTypeCode) {
        // 1.查询标签（需要加载关联关系，因为要检查是否已关联）
        TagAggregate tag = tagRepository.findByIdOrThrow(tagId);

        // 2.验证标签是否属于当前用户
        if (!tag.getUserId().equals(userId)) {
            throw TagException.of(TagErrorCode.TAG_NOT_BELONG_TO_USER);
        }

        // 3.验证标签类型与实体类型是否匹配
        if (!tag.canAssociateWith(entityTypeCode)) {
            throw TagException.of(TagErrorCode.TAG_TYPE_MISMATCH);
        }

        // 4.检查是否已关联（避免重复关联）
        if (tag.isEntityAssociated(entityId)) {
            throw TagException.of(TagErrorCode.ENTITY_ALREADY_ASSOCIATED);
        }

        // 5.关联实体（只操作聚合根）
        tag.associateEntity(entityId, userId);

        // 6.记录日志
        log.info("标签关联成功，标签ID: {}, 实体ID: {}, 用户ID: {}", tagId, entityId, userId);

        return tag;
    }

    @Override
    public TagAggregate dissociateEntity(String tagId, String entityId, String userId) {
        // 1.查询标签（需要加载关联关系，因为要检查是否已关联）
        TagAggregate tag = tagRepository.findByIdOrThrow(tagId);

        // 2.验证标签是否属于当前用户
        if (!tag.getUserId().equals(userId)) {
            throw TagException.of(TagErrorCode.TAG_NOT_BELONG_TO_USER);
        }

        // 3.把需要删除的实体绑定到聚合根上面
        tag.associateEntity(entityId,userId);

        // 4.记录日志
        log.info("标签解除关联成功，标签ID: {}, 实体ID: {}, 用户ID: {}", tagId, entityId, userId);

        return tag;
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
}
