package com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository;


import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 位置仓储接口
 * <p>
 * 定义位置聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
public interface ILocationRepository {

    /**
     * 保存位置聚合根
     * <p>
     * 保存位置聚合根到数据库
     *
     * @param locationAggregate 位置聚合根，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当保存失败时抛出
     */
    void save(StorageLocationAggregate locationAggregate);

    /**
     * 更新位置聚合根
     * <p>
     * 更新位置聚合根信息到数据库
     *
     * @param locationAggregate 位置聚合根，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当更新失败或位置不存在时抛出
     */
    void update(StorageLocationAggregate locationAggregate);

    /**
     * 删除位置（逻辑删除）
     * <p>
     * 将位置标记为已删除状态
     *
     * @param locationId 位置ID，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当删除失败或位置不存在时抛出
     */
    void delete(String locationId);

    /**
     * 根据ID查找位置聚合根
     * <p>
     * 根据位置ID查询位置聚合根，返回Optional对象
     * 注意：已删除的位置不会返回
     *
     * @param locationId 位置ID，不能为空
     * @return 位置聚合根（如果存在且未删除），Optional.empty()表示位置不存在或已删除
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    Optional<StorageLocationAggregate> findById(String locationId);

    /**
     * 根据ID查找位置聚合根，如果不存在或已删除则抛出异常
     * <p>
     * 用于命令操作中需要确保位置存在的场景，如果位置不存在或已删除则抛出异常
     *
     * @param locationId 位置ID，不能为空
     * @return 位置聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当位置不存在或已删除时抛出
     */
    StorageLocationAggregate findByIdOrThrow(String locationId);

    /**
     * 检查位置名称的唯一性
     * <p>
     * 检查同一用户下位置名称是否唯一（排除已删除的位置）
     * 对应数据库唯一索引：uk_user_id_name (user_id, name)
     *
     * @param name   位置名称，不能为空
     * @param userId 用户ID，不能为空
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当参数无效或查询失败时抛出
     */
    boolean existsByNameAndUserId(String name, String userId);

    /**
     * 检查位置是否被服装引用
     * <p>
     * 检查是否有服装的current_location_id引用该位置
     * 用于删除位置前的校验
     *
     * @param locationId 位置ID，不能为空
     * @return 是否被引用，true表示有服装引用，false表示无引用
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当参数无效或查询失败时抛出
     */
    boolean hasClothingReferences(String locationId);

    /**
     * 根据用户ID查询所有位置
     * <p>
     * 查询指定用户的所有位置列表，按创建时间倒序排列
     * 注意：已删除的位置不会返回
     *
     * @param userId 用户ID，不能为空
     * @return 位置聚合根列表，如果不存在则返回空列表
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    List<StorageLocationAggregate> findByUserId(String userId);

    /**
     * 根据位置ID列表查询位置
     * <p>
     * 根据位置ID列表查询位置聚合根列表
     * 注意：已删除的位置不会返回
     *
     * @param locationIds 位置ID列表，不能为空
     * @return 位置聚合根列表
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    List<StorageLocationAggregate> findByIds(List<String> locationIds);
}
