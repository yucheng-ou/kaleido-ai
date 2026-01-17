package com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository;


import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 品牌仓储接口
 * <p>
 * 定义品牌聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IBrandRepository {

    /**
     * 保存品牌聚合根
     * <p>
     * 保存品牌聚合根到数据库，如果是新品牌则插入，如果是已存在品牌则更新
     *
     * @param brandAggregate 品牌聚合根，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当保存失败时抛出
     */
    void save(BrandAggregate brandAggregate);

    /**
     * 更新品牌聚合根
     * <p>
     * 更新品牌聚合根信息到数据库
     *
     * @param brandAggregate 品牌聚合根，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当更新失败或品牌不存在时抛出
     */
    void update(BrandAggregate brandAggregate);

    /**
     * 删除服装 同时删除对应的图片
     *
     * @param clothingId 服装id
     */
    void delete(String clothingId);

    /**
     * 根据ID查找品牌聚合根
     * <p>
     * 根据品牌ID查询品牌聚合根，返回Optional对象
     *
     * @param brandId 品牌ID，不能为空
     * @return 品牌聚合根（如果存在），Optional.empty()表示品牌不存在
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    Optional<BrandAggregate> findById(String brandId);

    /**
     * 根据ID查找品牌聚合根，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保品牌存在的场景，如果品牌不存在则抛出异常
     *
     * @param brandId 品牌ID，不能为空
     * @return 品牌聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当品牌不存在时抛出
     */
    BrandAggregate findByIdOrThrow(String brandId);

    /**
     * 检查品牌名称的唯一性
     * <p>
     * 检查品牌名称是否全局唯一，用于品牌创建和更新时的业务规则校验
     * 对应数据库唯一索引：uk_name (name)
     *
     * @param name 品牌名称，不能为空
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当参数无效或查询失败时抛出
     */
    boolean existsByName(String name);


    /**
     * 查询所有品牌
     * <p>
     * 查询所有品牌列表，按创建时间倒序排列
     *
     * @return 品牌聚合根列表，如果不存在则返回空列表
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    List<BrandAggregate> findAll();

}
