package com.xiaoo.kaleido.wardrobe.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.BrandPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌数据访问接口
 * <p>
 * 负责品牌表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Mapper
public interface BrandDao extends BaseMapper<BrandPO> {

    /**
     * 根据ID查询品牌
     *
     * @param id 品牌ID
     * @return 品牌持久化对象
     */
    BrandPO findById(@Param("id") String id);

    /**
     * 查询所有品牌
     *
     * @return 品牌列表
     */
    List<BrandPO> findAll();

    /**
     * 检查品牌名称是否已存在
     *
     * @param name 品牌名称
     * @return 是否存在
     */
    boolean existsByName(@Param("name") String name);

    /**
     * 根据ID查找品牌，如果不存在则返回null
     *
     * @param id 品牌ID
     * @return 品牌持久化对象
     */
    BrandPO selectById(@Param("id") String id);
}
