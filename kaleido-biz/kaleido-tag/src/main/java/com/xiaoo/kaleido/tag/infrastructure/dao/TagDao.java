package com.xiaoo.kaleido.tag.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.api.tag.query.TagListQueryReq;
import com.xiaoo.kaleido.tag.infrastructure.dao.po.TagPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签数据访问接口
 * <p>
 * 负责标签表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Mapper
public interface TagDao extends BaseMapper<TagPO> {

    /**
     * 根据ID查询标签
     *
     * @param id 标签ID
     * @return 标签持久化对象
     */
    TagPO findById(@Param("id") String id);

    /**
     * 根据用户ID和类型编码查询标签列表
     *
     * @param userId   用户ID
     * @param typeCode 标签类型编码
     * @return 标签列表
     */
    List<TagPO> findByUserIdAndTypeCode(@Param("userId") String userId, @Param("typeCode") String typeCode);

    /**
     * 根据条件查询标签列表
     *
     * @param queryReq 查询条件
     * @return 标签列表
     */
    List<TagPO> selectByCondition(@Param("queryReq") TagListQueryReq queryReq);

    /**
     * 检查用户下同类型标签名称是否已存在
     *
     * @param userId   用户ID
     * @param name     标签名称
     * @param typeCode 标签类型编码
     * @return 是否存在
     */
    boolean existsByUserIdAndNameAndTypeCode(@Param("userId") String userId,
                                             @Param("name") String name,
                                             @Param("typeCode") String typeCode);

    /**
     * 根据标签ID列表查询标签
     *
     * @param tagIds 标签ID列表
     * @return 标签列表
     */
    List<TagPO> findByIds(@Param("tagIds") List<String> tagIds);

    /**
     * 根据标签ID和用户ID查询标签
     *
     * @param tagId  标签ID
     * @param userId 用户ID
     * @return 标签持久化对象
     */
    TagPO findByIdAndUserId(@Param("tagId") String tagId, @Param("userId") String userId);

    /**
     * 根据标签ID和用户ID列表查询标签
     *
     * @param tagIds  标签ID列表
     * @param userId  用户ID
     * @return 标签列表
     */
    List<TagPO> findByIdsAndUserId(@Param("tagIds") List<String> tagIds, @Param("userId") String userId);
}
