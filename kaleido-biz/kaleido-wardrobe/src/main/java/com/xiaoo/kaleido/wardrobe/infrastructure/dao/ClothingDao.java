package com.xiaoo.kaleido.wardrobe.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.ClothingPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服装数据访问接口
 * <p>
 * 负责服装表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Mapper
public interface ClothingDao extends BaseMapper<ClothingPO> {

    /**
     * 根据ID查询服装
     *
     * @param id 服装ID
     * @return 服装持久化对象
     */
    ClothingPO findById(@Param("id") String id);

    /**
     * 根据用户ID查询服装列表
     *
     * @param userId 用户ID
     * @return 服装列表
     */
    List<ClothingPO> findByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID和类型编码查询服装列表
     *
     * @param userId   用户ID
     * @param typeCode 服装类型编码
     * @return 服装列表
     */
    List<ClothingPO> findByUserIdAndTypeCode(@Param("userId") String userId, @Param("typeCode") String typeCode);

    /**
     * 根据用户ID、类型编码和颜色编码查询服装列表
     *
     * @param userId    用户ID
     * @param typeCode  服装类型编码
     * @param colorCode 颜色编码
     * @return 服装列表
     */
    List<ClothingPO> findByUserIdAndTypeCodeAndColorCode(
            @Param("userId") String userId,
            @Param("typeCode") String typeCode,
            @Param("colorCode") String colorCode);

    /**
     * 检查用户下服装名称是否已存在
     *
     * @param userId 用户ID
     * @param name   服装名称
     * @return 是否存在
     */
    boolean existsByUserIdAndName(@Param("userId") String userId, @Param("name") String name);

}
