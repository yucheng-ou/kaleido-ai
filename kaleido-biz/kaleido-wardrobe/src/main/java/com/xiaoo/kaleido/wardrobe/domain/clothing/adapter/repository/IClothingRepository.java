package com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 服装仓储接口
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IClothingRepository {

    /**
     * 保存服装聚合根
     * 保存服装聚合根到数据库，如果是新服装则插入，如果是已存在服装则更新
     *
     * @param clothingAggregate 服装聚合根，不能为空
     */
    void save(ClothingAggregate clothingAggregate);

    /**
     * 更新服装聚合根
     * 更新服装聚合根信息到数据库
     *
     * @param clothingAggregate 服装聚合根，不能为空
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
     * 根据服装ID查询服装聚合根，如果不存在则抛出异常
     *
     * @param clothingId 服装ID，不能为空
     * @return 服装聚合根
     */
    ClothingAggregate findById(String clothingId);

    /**
     * 检查服装名称在用户下的唯一性
     * 检查同用户下服装名称是否唯一，用于服装创建和更新时的业务规则校验
     * 对应数据库唯一索引：uk_user_clothing_name (user_id, name)
     *
     * @param userId 用户ID，不能为空
     * @param name   服装名称，不能为空
     * @return 是否存在，true表示已存在（不唯一），false表示不存在（唯一）
     */
    boolean existsByUserIdAndName(String userId, String name);

    /**
     * 根据用户ID查询服装列表
     * 查询指定用户的所有服装
     *
     * @param userId 用户ID，不能为空
     * @return 服装聚合根列表
     */
    List<ClothingAggregate> findByUserId(String userId);

}
