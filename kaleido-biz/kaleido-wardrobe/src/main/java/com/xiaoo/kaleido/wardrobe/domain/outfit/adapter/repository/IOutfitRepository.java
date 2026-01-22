package com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 穿搭仓储接口
 * <p>
 * 定义穿搭聚合根的持久化操作，遵循仓储模式
 * 实现应放在infrastructure层
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
public interface IOutfitRepository {

    /**
     * 保存穿搭聚合根

     * 保存或更新穿搭聚合根，包括其关联的实体（服装、图片、穿着记录）
     *
     * @param outfitAggregate 穿搭聚合根，不能为空
     * @return 保存后的穿搭聚合根
     */
    OutfitAggregate save(OutfitAggregate outfitAggregate);

    /**
     * 根据ID查找穿搭聚合根

     * 查找指定ID的穿搭聚合根，包含其关联的实体
     *
     * @param id 穿搭ID，不能为空
     * @return 穿搭聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当穿搭不存在时抛出
     */
    OutfitAggregate findById(String id);

    /**
     * 根据ID查找穿搭聚合根（包含已删除的）

     * 查找指定ID的穿搭聚合根，包含已逻辑删除的记录
     *
     * @param id 穿搭ID，不能为空
     * @return 穿搭聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当穿搭不存在时抛出
     */
    OutfitAggregate findByIdIncludeDeleted(String id);

    /**
     * 根据用户ID查找穿搭聚合根列表

     * 查找指定用户的所有穿搭，包含基本信息
     * 注意：可能不包含完整的关联实体，根据业务需求决定
     *
     * @param userId 用户ID，不能为空
     * @return 穿搭聚合根列表
     */
    List<OutfitAggregate> findByUserId(String userId);

    /**
     * 根据用户ID和穿搭名称查找穿搭聚合根

     * 用于检查同一用户下穿搭名称是否重复
     *
     * @param userId 用户ID，不能为空
     * @param name   穿搭名称，不能为空
     * @return 穿搭聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当穿搭不存在时抛出
     */
    OutfitAggregate findByUserIdAndName(String userId, String name);

    /**
     * 删除穿搭聚合根

     * 删除指定ID的穿搭聚合根（逻辑删除或物理删除，根据实现决定）
     *
     * @param id 穿搭ID，不能为空
     */
    void deleteById(String id);

    /**
     * 检查穿搭是否存在
     *
     * @param id 穿搭ID，不能为空
     * @return 如果存在返回true，否则返回false
     */
    boolean existsById(String id);

    /**
     * 检查同一用户下穿搭名称是否已存在
     *
     * @param userId 用户ID，不能为空
     * @param name   穿搭名称，不能为空
     * @return 如果已存在返回true，否则返回false
     */
    boolean existsByUserIdAndName(String userId, String name);
}
