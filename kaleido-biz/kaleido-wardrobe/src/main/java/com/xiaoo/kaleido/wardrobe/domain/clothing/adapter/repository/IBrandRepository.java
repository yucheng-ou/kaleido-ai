package com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository;


import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 品牌仓储接口
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IBrandRepository {

    /**
     * 保存品牌聚合根
     * 保存品牌聚合根到数据库，如果是新品牌则插入，如果是已存在品牌则更新
     *
     * @param brandAggregate 品牌聚合根，不能为空
     */
    void save(BrandAggregate brandAggregate);

    /**
     * 更新品牌聚合根
     *
     * @param brandAggregate 品牌聚合根，不能为空
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
     *
     * @param brandId 品牌ID，不能为空
     * @return 品牌聚合根
     */
    BrandAggregate findById(String brandId);

    /**
     * 检查品牌名称的唯一性
     * 检查品牌名称是否全局唯一，用于品牌创建和更新时的业务规则校验
     * 对应数据库唯一索引：uk_name (name)
     *
     * @param name 品牌名称，不能为空
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
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
