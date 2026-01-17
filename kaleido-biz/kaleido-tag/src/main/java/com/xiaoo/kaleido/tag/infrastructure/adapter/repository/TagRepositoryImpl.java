package com.xiaoo.kaleido.tag.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.tag.domain.adapter.repository.ITagRepository;
import com.xiaoo.kaleido.tag.domain.model.aggregate.TagAggregate;
import com.xiaoo.kaleido.tag.domain.model.entity.TagRelation;
import com.xiaoo.kaleido.tag.infrastructure.adapter.repository.convertor.TagInfraConvertor;
import com.xiaoo.kaleido.tag.infrastructure.adapter.repository.convertor.TagRelationInfraConvertor;
import com.xiaoo.kaleido.tag.infrastructure.dao.TagDao;
import com.xiaoo.kaleido.tag.infrastructure.dao.TagRelationDao;
import com.xiaoo.kaleido.tag.infrastructure.dao.po.TagPO;
import com.xiaoo.kaleido.tag.infrastructure.dao.po.TagRelationPO;
import com.xiaoo.kaleido.tag.types.exception.TagException;
import com.xiaoo.kaleido.tag.types.exception.TagErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 标签仓储实现（基础设施层）
 * <p>
 * 标签仓储接口的具体实现，负责标签聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements ITagRepository {

    private final TagDao tagDao;
    private final TagRelationDao tagRelationDao;

    @Override
    public void save(TagAggregate tagAggregate) {
        // 1.转换TagAggregate为TagPO
        TagPO tagPO = TagInfraConvertor.INSTANCE.toPO(tagAggregate);

        // 2.保存标签基本信息
        tagDao.insert(tagPO);

        log.info("标签保存成功，标签ID: {}, 用户ID: {}, 标签名称: {}",
                tagAggregate.getId(), tagAggregate.getUserId(), tagAggregate.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TagAggregate tagAggregate) {
        // 1.转换TagAggregate为TagPO
        TagPO tagPO = TagInfraConvertor.INSTANCE.toPO(tagAggregate);

        // 2.更新标签基本信息
        tagDao.updateById(tagPO);

        log.info("标签更新成功，标签ID: {}, 用户ID: {}, 标签名称: {}",
                tagAggregate.getId(), tagAggregate.getUserId(), tagAggregate.getName());
    }

    @Override
    public Optional<TagAggregate> findById(String tagId) {
        try {
            // 1.查询标签基本信息
            TagPO tagPO = tagDao.findById(tagId);
            if (tagPO == null) {
                return Optional.empty();
            }

            // 2.转换为TagAggregate
            TagAggregate tagAggregate = TagInfraConvertor.INSTANCE.toAggregate(tagPO);

            return Optional.of(tagAggregate);
        } catch (Exception e) {
            log.error("查询标签失败，标签ID: {}, 原因: {}", tagId, e.getMessage(), e);
            throw TagException.of(TagErrorCode.TAG_QUERY_FAIL);
        }
    }

    @Override
    public TagAggregate findByIdOrThrow(String tagId) {
        return findById(tagId)
                .orElseThrow(() -> TagException.of(BizErrorCode.DATA_NOT_EXIST));
    }

    @Override
    public boolean existsByUserIdAndNameAndTypeCode(String userId, String name, String typeCode) {
        try {
            return tagDao.existsByUserIdAndNameAndTypeCode(userId, name, typeCode);
        } catch (Exception e) {
            log.error("检查标签名称唯一性失败，用户ID: {}, 标签名称: {}, 类型编码: {}, 原因: {}",
                    userId, name, typeCode, e.getMessage(), e);
            throw TagException.of(TagErrorCode.TAG_QUERY_FAIL);
        }
    }

    @Override
    public List<TagAggregate> findByUserIdAndTypeCode(String userId, String typeCode) {
        try {
            // 1.查询标签基本信息列表
            List<TagPO> tagPOs = tagDao.findByUserIdAndTypeCode(userId, typeCode);

            // 2.转换为TagAggregate列表
            // 注意：这里不加载关联关系，按需加载
            return tagPOs.stream()
                    .map(TagInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户标签列表失败，用户ID: {}, 类型编码: {}, 原因: {}",
                    userId, typeCode, e.getMessage(), e);
            throw TagException.of(TagErrorCode.TAG_QUERY_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRelation(TagAggregate tag) {
        //1.插入关联关系
        batchSaveTagRelations(tag);

        //2.更新标签
        update(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRelation(TagAggregate tag) {
        //1.删除关联关系
        tagRelationDao.deleteByTagId(tag.getId());

        //2.更新标签
        update(tag);
    }

    /**
     * 批量保存标签关联关系
     * <p>
     * 将标签关联关系列表批量保存到数据库
     *
     * @param tagAggregate 标签聚合根，包含关联关系列表
     */
    private void batchSaveTagRelations(TagAggregate tagAggregate) {
        List<TagRelation> relations = tagAggregate.getRelations();
        if (relations == null || relations.isEmpty()) {
            return;
        }

        // 转换TagRelation为TagRelationPO
        List<TagRelationPO> relationPOs = TagRelationInfraConvertor.INSTANCE.toPOList(relations);

        // 批量插入
        if (!relationPOs.isEmpty()) {
            tagRelationDao.batchInsert(relationPOs);
        }
    }
}
