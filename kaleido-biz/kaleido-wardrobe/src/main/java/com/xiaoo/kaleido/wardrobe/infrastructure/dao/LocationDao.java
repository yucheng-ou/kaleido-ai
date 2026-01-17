package com.xiaoo.kaleido.wardrobe.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.LocationPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 位置数据访问接口
 * <p>
 * 负责位置表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Mapper
public interface LocationDao extends BaseMapper<LocationPO> {

    /**
     * 根据ID查询位置
     *
     * @param id 位置ID
     * @return 位置持久化对象
     */
    LocationPO findById(@Param("id") String id);

    /**
     * 根据用户ID查询所有位置
     *
     * @param userId 用户ID
     * @return 位置列表
     */
    List<LocationPO> findByUserId(@Param("userId") String userId);

    /**
     * 检查位置名称是否已存在（同一用户下）
     *
     * @param name   位置名称
     * @param userId 用户ID
     * @return 是否存在
     */
    boolean existsByNameAndUserId(@Param("name") String name, @Param("userId") String userId);

    /**
     * 检查位置是否被服装引用
     *
     * @param locationId 位置ID
     * @return 是否被引用
     */
    boolean hasClothingReferences(@Param("locationId") String locationId);

    /**
     * 根据ID查找位置，如果不存在则返回null
     *
     * @param id 位置ID
     * @return 位置持久化对象
     */
    LocationPO selectById(@Param("id") String id);

    /**
     * 根据ID列表查询位置
     *
     * @param locationIds 位置ID列表
     * @return 位置列表
     */
    List<LocationPO> findByIds(@Param("locationIds") List<String> locationIds);
}
