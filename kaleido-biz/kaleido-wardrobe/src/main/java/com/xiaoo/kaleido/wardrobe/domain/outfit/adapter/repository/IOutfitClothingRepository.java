package com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitClothing;

import java.util.List;
import java.util.Optional;

/**
 * 搭配-服装关联仓储接口
 * <p>
 * 定义搭配-服装关联实体的数据访问操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IOutfitClothingRepository {

    /**
     * 保存搭配-服装关联
     *
     * @param outfitClothing 搭配-服装关联实体
     * @return 保存后的搭配-服装关联实体
     */
    OutfitClothing save(OutfitClothing outfitClothing);

    /**
     * 根据ID查找搭配-服装关联
     *
     * @param id 关联ID
     * @return 搭配-服装关联实体（如果存在）
     */
    Optional<OutfitClothing> findById(String id);

    /**
     * 根据搭配ID查找所有搭配-服装关联
     *
     * @param outfitId 搭配ID
     * @return 搭配-服装关联实体列表
     */
    List<OutfitClothing> findByOutfitId(String outfitId);

    /**
     * 根据服装ID查找所有搭配-服装关联
     *
     * @param clothingId 服装ID
     * @return 搭配-服装关联实体列表
     */
    List<OutfitClothing> findByClothingId(String clothingId);

    /**
     * 根据搭配ID和服装ID查找搭配-服装关联
     *
     * @param outfitId   搭配ID
     * @param clothingId 服装ID
     * @return 搭配-服装关联实体（如果存在）
     */
    Optional<OutfitClothing> findByOutfitIdAndClothingId(String outfitId, String clothingId);

    /**
     * 删除搭配-服装关联
     *
     * @param id 关联ID
     * @return 如果成功删除返回true，否则返回false
     */
    boolean deleteById(String id);

    /**
     * 根据搭配ID删除所有搭配-服装关联
     *
     * @param outfitId 搭配ID
     * @return 删除的数量
     */
    int deleteByOutfitId(String outfitId);

    /**
     * 根据服装ID删除所有搭配-服装关联
     *
     * @param clothingId 服装ID
     * @return 删除的数量
     */
    int deleteByClothingId(String clothingId);

    /**
     * 批量保存搭配-服装关联
     *
     * @param outfitClothings 搭配-服装关联实体列表
     * @return 保存后的搭配-服装关联实体列表
     */
    List<OutfitClothing> saveAll(List<OutfitClothing> outfitClothings);

    /**
     * 检查关联是否存在
     *
     * @param id 关联ID
     * @return 如果关联存在返回true，否则返回false
     */
    boolean existsById(String id);

    /**
     * 检查搭配和服装是否已关联
     *
     * @param outfitId   搭配ID
     * @param clothingId 服装ID
     * @return 如果已关联返回true，否则返回false
     */
    boolean existsByOutfitIdAndClothingId(String outfitId, String clothingId);
}
