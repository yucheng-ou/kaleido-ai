package com.xiaoo.kaleido.wardrobe.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitClothingPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 穿搭-服装关联数据访问接口
 * <p>
 * 负责穿搭-服装关联表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Mapper
public interface OutfitClothingDao extends BaseMapper<OutfitClothingPO> {

    /**
     * 根据穿搭ID查询关联的服装ID列表
     *
     * @param outfitId 穿搭ID
     * @return 服装ID列表
     */
    List<String> findClothingIdsByOutfitId(@Param("outfitId") String outfitId);

    /**
     * 根据服装ID查询关联的穿搭ID列表
     *
     * @param clothingId 服装ID
     * @return 穿搭ID列表
     */
    List<String> findOutfitIdsByClothingId(@Param("clothingId") String clothingId);

    /**
     * 根据穿搭ID列表批量查询关联的服装ID
     *
     * @param outfitIds 穿搭ID列表
     * @return 穿搭ID与服装ID的关联列表
     */
    List<OutfitClothingPO> findByOutfitIds(@Param("outfitIds") List<String> outfitIds);

    /**
     * 根据穿搭ID删除所有关联记录
     *
     * @param outfitId 穿搭ID
     * @return 删除的记录数
     */
    int deleteByOutfitId(@Param("outfitId") String outfitId);

    /**
     * 根据服装ID删除所有关联记录
     *
     * @param clothingId 服装ID
     * @return 删除的记录数
     */
    int deleteByClothingId(@Param("clothingId") String clothingId);

    /**
     * 批量插入关联记录
     *
     * @param records 关联记录列表
     * @return 插入的记录数
     */
    int batchInsert(@Param("records") List<OutfitClothingPO> records);

    /**
     * 检查穿搭是否包含指定服装
     *
     * @param outfitId 穿搭ID
     * @param clothingId 服装ID
     * @return 是否存在关联
     */
    boolean existsByOutfitIdAndClothingId(@Param("outfitId") String outfitId, @Param("clothingId") String clothingId);
}
