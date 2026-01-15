package com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity.ClothingImage;

import java.util.List;
import java.util.Optional;

/**
 * 服装图片仓储接口
 * <p>
 * 定义服装图片实体的持久化操作，包括保存、查询、删除等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IClothingImageRepository {

    /**
     * 保存服装图片
     *
     * @param image 服装图片实体，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当保存失败时抛出
     */
    void save(ClothingImage image);

    /**
     * 批量保存服装图片
     *
     * @param images 服装图片实体列表，不能为空
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当保存失败时抛出
     */
    void saveAll(List<ClothingImage> images);

    /**
     * 根据ID查找服装图片
     *
     * @param imageId 图片ID，不能为空
     * @return 服装图片实体（如果存在），Optional.empty()表示图片不存在
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    Optional<ClothingImage> findById(String imageId);

    /**
     * 根据服装ID查找所有图片
     *
     * @param clothingId 服装ID，不能为空
     * @return 服装图片实体列表
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    List<ClothingImage> findByClothingId(String clothingId);

    /**
     * 根据服装ID查找主图
     *
     * @param clothingId 服装ID，不能为空
     * @return 主图（如果存在），Optional.empty()表示没有主图
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    Optional<ClothingImage> findPrimaryImageByClothingId(String clothingId);

    /**
     * 删除服装图片
     *
     * @param imageId 图片ID，不能为空
     * @return 如果成功删除返回true，如果图片不存在返回false
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当删除失败时抛出
     */
    boolean deleteById(String imageId);

    /**
     * 根据服装ID删除所有图片
     *
     * @param clothingId 服装ID，不能为空
     * @return 删除的图片数量
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当删除失败时抛出
     */
    int deleteByClothingId(String clothingId);

    /**
     * 检查图片是否存在
     *
     * @param imageId 图片ID，不能为空
     * @return 如果图片存在返回true，否则返回false
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    boolean existsById(String imageId);

    /**
     * 检查服装是否有主图
     *
     * @param clothingId 服装ID，不能为空
     * @return 如果服装有主图返回true，否则返回false
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当查询失败时抛出
     */
    boolean hasPrimaryImage(String clothingId);
}
