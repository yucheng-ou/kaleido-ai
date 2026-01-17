package com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 服装仓储接口
 * <p>
 * 定义服装聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 * 注意：只提供聚合根级别的操作，不直接操作聚合内部的实体
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IClothingRepository {

    /**
     * 保存服装聚合根
     * <p>
     * 保存服装聚合根到数据库，如果是新服装则插入，如果是已存在服装则更新
     * 注意：需要同时保存服装基本信息和图片列表
     *
     * @param clothingAggregate 服装聚合根，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当保存失败时抛出
     */
    void save(ClothingAggregate clothingAggregate);

    /**
     * 更新服装聚合根
     * <p>
     * 更新服装聚合根信息到数据库
     * 注意：需要同时更新服装基本信息和图片列表
     *
     * @param clothingAggregate 服装聚合根，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当更新失败或服装不存在时抛出
     */
    void update(ClothingAggregate clothingAggregate);

    /**
     * 删除服装以及相关的图片
     *
     * @param clothingId 服装id
     */
    void delete(String clothingId);

    /**
     * 根据ID查找服装聚合根
     * <p>
     * 根据服装ID查询服装聚合根，返回Optional对象
     * 注意：需要同时加载服装的基本信息和图片列表
     *
     * @param clothingId 服装ID，不能为空
     * @return 服装聚合根（如果存在），Optional.empty()表示服装不存在
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    Optional<ClothingAggregate> findById(String clothingId);

    /**
     * 根据ID查找服装聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保服装存在的场景，如果服装不存在则抛出异常
     * 注意：需要同时加载服装的基本信息和图片列表
     *
     * @param clothingId 服装ID，不能为空
     * @return 服装聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当服装不存在时抛出
     */
    ClothingAggregate findByIdOrThrow(String clothingId);

    /**
     * 检查服装名称在用户下的唯一性
     * <p>
     * 检查同用户下服装名称是否唯一，用于服装创建和更新时的业务规则校验
     * 对应数据库唯一索引：uk_user_clothing_name (user_id, name)
     *
     * @param userId 用户ID，不能为空
     * @param name   服装名称，不能为空
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当参数无效或查询失败时抛出
     */
    boolean existsByUserIdAndName(String userId, String name);

    /**
     * 根据用户ID查询服装列表
     * <p>
     * 查询指定用户的所有服装
     * 注意：按需加载图片信息，默认只加载基本信息
     *
     * @param userId 用户ID，不能为空
     * @return 服装聚合根列表
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当参数无效或查询失败时抛出
     */
    List<ClothingAggregate> findByUserId(String userId);

    /**
     * 根据用户ID和类型编码查询服装列表
     * <p>
     * 查询指定用户下指定类型的所有服装
     *
     * @param userId   用户ID，不能为空
     * @param typeCode 服装类型编码，不能为空
     * @return 服装聚合根列表
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当参数无效或查询失败时抛出
     */
    List<ClothingAggregate> findByUserIdAndTypeCode(String userId, String typeCode);

    /**
     * 根据用户ID、类型编码和颜色编码查询服装列表
     * <p>
     * 查询指定用户下指定类型和颜色的所有服装
     *
     * @param userId    用户ID，不能为空
     * @param typeCode  服装类型编码，可为空
     * @param colorCode 颜色编码，可为空
     * @return 服装聚合根列表
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当参数无效或查询失败时抛出
     */
    List<ClothingAggregate> findByUserIdAndTypeCodeAndColorCode(String userId, String typeCode, String colorCode);

}
