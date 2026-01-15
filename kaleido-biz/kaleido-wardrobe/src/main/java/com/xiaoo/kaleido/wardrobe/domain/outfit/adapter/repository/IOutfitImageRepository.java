package com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;

import java.util.List;
import java.util.Optional;

/**
 * 搭配图片仓储接口
 * <p>
 * 定义搭配图片实体的数据访问操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IOutfitImageRepository {

    /**
     * 保存搭配图片
     *
     * @param outfitImage 搭配图片实体
     * @return 保存后的搭配图片实体
     */
    OutfitImage save(OutfitImage outfitImage);

    /**
     * 根据ID查找搭配图片
     *
     * @param id 图片ID
     * @return 搭配图片实体（如果存在）
     */
    Optional<OutfitImage> findById(String id);

    /**
     * 根据搭配ID查找所有搭配图片
     *
     * @param outfitId 搭配ID
     * @return 搭配图片实体列表
     */
    List<OutfitImage> findByOutfitId(String outfitId);

    /**
     * 根据搭配ID和是否为主图查找搭配图片
     *
     * @param outfitId  搭配ID
     * @param isPrimary 是否为主图
     * @return 搭配图片实体列表
     */
    List<OutfitImage> findByOutfitIdAndIsPrimary(String outfitId, Boolean isPrimary);

    /**
     * 删除搭配图片
     *
     * @param id 图片ID
     * @return 如果成功删除返回true，否则返回false
     */
    boolean deleteById(String id);

    /**
     * 批量保存搭配图片
     *
     * @param outfitImages 搭配图片实体列表
     * @return 保存后的搭配图片实体列表
     */
    List<OutfitImage> saveAll(List<OutfitImage> outfitImages);

    /**
     * 根据搭配ID删除所有搭配图片
     *
     * @param outfitId 搭配ID
     * @return 删除的数量
     */
    int deleteByOutfitId(String outfitId);

    /**
     * 检查图片是否存在
     *
     * @param id 图片ID
     * @return 如果图片存在返回true，否则返回false
     */
    boolean existsById(String id);
}
