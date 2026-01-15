package com.xiaoo.kaleido.tag.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.tag.infrastructure.dao.po.TagRelationPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签关联数据访问接口
 * <p>
 * 负责标签关联表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Mapper
public interface TagRelationDao extends BaseMapper<TagRelationPO> {

    /**
     * 根据标签ID和实体ID查询关联
     *
     * @param tagId    标签ID
     * @param entityId 实体ID
     * @return 标签关联持久化对象
     */
    TagRelationPO findByTagIdAndEntityId(@Param("tagId") String tagId, @Param("entityId") String entityId);

    /**
     * 根据标签ID查询关联列表
     *
     * @param tagId 标签ID
     * @return 标签关联列表
     */
    List<TagRelationPO> findByTagId(@Param("tagId") String tagId);

    /**
     * 根据实体ID查询关联列表
     *
     * @param entityId 实体ID
     * @return 标签关联列表
     */
    List<TagRelationPO> findByEntityId(@Param("entityId") String entityId);

    /**
     * 根据标签ID列表和实体ID查询关联列表
     *
     * @param tagIds   标签ID列表
     * @param entityId 实体ID
     * @return 标签关联列表
     */
    List<TagRelationPO> findByTagIdsAndEntityId(@Param("tagIds") List<String> tagIds, @Param("entityId") String entityId);

    /**
     * 根据标签ID和实体ID列表查询关联列表
     *
     * @param tagId     标签ID
     * @param entityIds 实体ID列表
     * @return 标签关联列表
     */
    List<TagRelationPO> findByTagIdAndEntityIds(@Param("tagId") String tagId, @Param("entityIds") List<String> entityIds);

    /**
     * 根据标签ID和用户ID查询关联列表
     *
     * @param tagId  标签ID
     * @param userId 用户ID
     * @return 标签关联列表
     */
    List<TagRelationPO> findByTagIdAndUserId(@Param("tagId") String tagId, @Param("userId") String userId);

    /**
     * 根据实体ID和用户ID查询关联列表
     *
     * @param entityId 实体ID
     * @param userId   用户ID
     * @return 标签关联列表
     */
    List<TagRelationPO> findByEntityIdAndUserId(@Param("entityId") String entityId, @Param("userId") String userId);

    /**
     * 批量插入标签关联
     *
     * @param relations 标签关联列表
     * @return 插入数量
     */
    int batchInsert(@Param("relations") List<TagRelationPO> relations);

    /**
     * 批量删除标签关联
     *
     * @param relations 标签关联列表
     * @return 删除数量
     */
    int batchDelete(@Param("relations") List<TagRelationPO> relations);

    /**
     * 根据标签ID删除所有关联
     *
     * @param tagId 标签ID
     * @return 删除数量
     */
    int deleteByTagId(@Param("tagId") String tagId);

    /**
     * 根据实体ID删除所有关联
     *
     * @param entityId 实体ID
     * @return 删除数量
     */
    int deleteByEntityId(@Param("entityId") String entityId);

    /**
     * 根据标签ID和实体ID列表删除关联
     *
     * @param tagId     标签ID
     * @param entityIds 实体ID列表
     * @return 删除数量
     */
    int deleteByTagIdAndEntityIds(@Param("tagId") String tagId, @Param("entityIds") List<String> entityIds);

    /**
     * 根据标签ID列表和实体ID删除关联
     *
     * @param tagIds   标签ID列表
     * @param entityId 实体ID
     * @return 删除数量
     */
    int deleteByTagIdsAndEntityId(@Param("tagIds") List<String> tagIds, @Param("entityId") String entityId);
}
