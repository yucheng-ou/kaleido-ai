package com.xiaoo.kaleido.tag.domain.adapter.repository;

import com.xiaoo.kaleido.tag.domain.model.aggregate.TagAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 标签仓储接口
 * <p>
 * 定义标签聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface ITagRepository {

    /**
     * 保存标签聚合根

     * 保存标签聚合根到数据库，如果是新标签则插入，如果是已存在标签则更新
     * 注意：只保存标签的基本信息，不处理关联关系
     *
     * @param tagAggregate 标签聚合根，不能为空
     * @throws com.xiaoo.kaleido.tag.types.exception.TagException 当保存失败时抛出
     */
    void save(TagAggregate tagAggregate);

    /**
     * 更新标签聚合根

     * 更新标签聚合根信息到数据库，包括标签基本信息和关联关系
     *
     * @param tagAggregate 标签聚合根，不能为空
     * @throws com.xiaoo.kaleido.tag.types.exception.TagException 当更新失败或标签不存在时抛出
     */
    void update(TagAggregate tagAggregate);

    /**
     * 根据ID查找标签聚合根

     * 根据标签ID查询标签聚合根，返回Optional对象
     * 注意：需要同时加载标签的基本信息和关联关系
     *
     * @param tagId 标签ID，不能为空
     * @return 标签聚合根（如果存在），Optional.empty()表示标签不存在
     * @throws com.xiaoo.kaleido.tag.types.exception.TagException 当查询失败时抛出
     */
    Optional<TagAggregate> findById(String tagId);

    /**
     * 根据ID和用户ID查找标签聚合根

     * 根据标签ID和用户ID查询标签聚合根，用于权限验证
     * 注意：需要同时加载标签的基本信息和关联关系
     *
     * @param tagId 标签ID，不能为空
     * @param userId 用户ID，不能为空
     * @return 标签聚合根（如果存在且属于该用户），Optional.empty()表示标签不存在或不属于该用户
     * @throws com.xiaoo.kaleido.tag.types.exception.TagException 当查询失败时抛出
     */
    Optional<TagAggregate> findByIdAndUserId(String tagId, String userId);

    /**
     * 根据ID查找标签聚合根，如果不存在则抛出异常

     * 用于命令操作中需要确保标签存在的场景，如果标签不存在则抛出异常
     * 注意：需要同时加载标签的基本信息和关联关系
     *
     * @param tagId 标签ID，不能为空
     * @return 标签聚合根
     * @throws com.xiaoo.kaleido.tag.types.exception.TagException 当标签不存在时抛出
     */
    TagAggregate findByIdOrThrow(String tagId);

    /**
     * 检查标签名称在用户下的唯一性

     * 检查同类型标签名称在用户下是否唯一，用于标签创建和更新时的业务规则校验
     * 对应数据库唯一索引：uk_user_tag_type_name (user_id, type_code, name)
     *
     * @param userId   用户ID，不能为空
     * @param name     标签名称，不能为空
     * @param typeCode 标签类型编码，不能为空
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
     * @throws com.xiaoo.kaleido.tag.types.exception.TagException 当参数无效或查询失败时抛出
     */
    boolean existsByUserIdAndNameAndTypeCode(String userId, String name, String typeCode);

    /**
     * 根据用户ID和类型编码查询标签列表

     * 查询指定用户下指定类型的所有标签
     *
     * @param userId   用户ID，不能为空
     * @param typeCode 标签类型编码，不能为空
     * @return 标签聚合根列表
     * @throws com.xiaoo.kaleido.tag.types.exception.TagException 当参数无效或查询失败时抛出
     */
    List<TagAggregate> findByUserIdAndTypeCode(String userId, String typeCode);


    void insertRelation(TagAggregate tag);

    void deleteRelation(TagAggregate tag);
}
