package com.xiaoo.kaleido.wardrobe.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 穿搭数据访问接口
 * <p>
 * 负责穿搭表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Mapper
public interface OutfitDao extends BaseMapper<OutfitPO> {

    /**
     * 根据ID查询穿搭
     *
     * @param id 穿搭ID
     * @return 穿搭持久化对象
     */
    OutfitPO findById(@Param("id") String id);

    /**
     * 根据用户ID查询穿搭列表
     *
     * @param userId 用户ID
     * @return 穿搭列表
     */
    List<OutfitPO> findByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID和穿搭名称查询穿搭
     *
     * @param userId 用户ID
     * @param name   穿搭名称
     * @return 穿搭持久化对象
     */
    OutfitPO findByUserIdAndName(@Param("userId") String userId, @Param("name") String name);

    /**
     * 检查用户下穿搭名称是否已存在
     *
     * @param userId 用户ID
     * @param name   穿搭名称
     * @return 是否存在
     */
    boolean existsByUserIdAndName(@Param("userId") String userId, @Param("name") String name);

    /**
     * 根据ID查询穿搭（包含已删除的）
     *
     * @param id 穿搭ID
     * @return 穿搭持久化对象
     */
    OutfitPO findByIdIncludeDeleted(@Param("id") String id);
}
