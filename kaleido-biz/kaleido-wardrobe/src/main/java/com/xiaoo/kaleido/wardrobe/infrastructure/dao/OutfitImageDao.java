package com.xiaoo.kaleido.wardrobe.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitImagePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 穿搭图片数据访问接口
 * <p>
 * 负责穿搭图片表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Mapper
public interface OutfitImageDao extends BaseMapper<OutfitImagePO> {

    /**
     * 根据穿搭ID查询图片列表
     *
     * @param outfitId 穿搭ID
     * @return 图片列表
     */
    List<OutfitImagePO> findByOutfitId(@Param("outfitId") String outfitId);

    /**
     * 根据穿搭ID列表查询图片列表
     *
     * @param outfitIds 穿搭ID列表
     * @return 图片列表
     */
    List<OutfitImagePO> findByOutfitIds(@Param("outfitIds") List<String> outfitIds);

    /**
     * 根据穿搭ID删除图片
     *
     * @param outfitId 穿搭ID
     * @return 删除的记录数
     */
    int deleteByOutfitId(@Param("outfitId") String outfitId);

    /**
     * 根据穿搭ID查询主图
     *
     * @param outfitId 穿搭ID
     * @return 主图（如果存在），否则返回null
     */
    OutfitImagePO findPrimaryByOutfitId(@Param("outfitId") String outfitId);

    /**
     * 根据穿搭ID和图片ID查询图片
     *
     * @param outfitId 穿搭ID
     * @param imageId  图片ID
     * @return 图片（如果存在），否则返回null
     */
    OutfitImagePO findByOutfitIdAndImageId(@Param("outfitId") String outfitId, @Param("imageId") String imageId);

    /**
     * 检查穿搭下是否存在图片
     *
     * @param outfitId 穿搭ID
     * @return 如果存在返回true，否则返回false
     */
    boolean existsByOutfitId(@Param("outfitId") String outfitId);

    /**
     * 检查穿搭下是否存在主图
     *
     * @param outfitId 穿搭ID
     * @return 如果存在主图返回true，否则返回false
     */
    boolean existsPrimaryByOutfitId(@Param("outfitId") String outfitId);
}
