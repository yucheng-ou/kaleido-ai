package com.xiaoo.kaleido.wardrobe.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.ClothingImagePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服装图片数据访问接口
 * <p>
 * 负责服装图片表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Mapper
public interface ClothingImageDao extends BaseMapper<ClothingImagePO> {

    /**
     * 根据服装ID查询图片列表
     *
     * @param clothingId 服装ID
     * @return 图片列表
     */
    List<ClothingImagePO> findByClothingId(@Param("clothingId") String clothingId);

    /**
     * 根据服装ID列表查询图片列表
     *
     * @param clothingIds 服装ID列表
     * @return 图片列表
     */
    List<ClothingImagePO> findByClothingIds(@Param("clothingIds") List<String> clothingIds);

    /**
     * 批量插入图片
     *
     * @param images 图片列表
     * @return 插入数量
     */
    int batchInsert(@Param("images") List<ClothingImagePO> images);

    /**
     * 根据服装ID删除图片
     *
     * @param clothingId 服装ID
     * @return 删除数量
     */
    int deleteByClothingId(@Param("clothingId") String clothingId);

    /**
     * 根据图片ID列表删除图片
     *
     * @param imageIds 图片ID列表
     * @return 删除数量
     */
    int deleteByIds(@Param("imageIds") List<String> imageIds);
}
