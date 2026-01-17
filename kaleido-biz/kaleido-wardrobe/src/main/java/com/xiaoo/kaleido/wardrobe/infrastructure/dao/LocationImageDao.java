package com.xiaoo.kaleido.wardrobe.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.LocationImagePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 位置图片数据访问接口
 * <p>
 * 负责位置图片表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Mapper
public interface LocationImageDao extends BaseMapper<LocationImagePO> {

    /**
     * 根据位置ID查询图片列表
     *
     * @param locationId 位置ID
     * @return 图片列表
     */
    List<LocationImagePO> findByLocationId(@Param("locationId") String locationId);

    /**
     * 根据位置ID列表查询图片
     *
     * @param locationIds 位置ID列表
     * @return 图片列表
     */
    List<LocationImagePO> findByLocationIds(@Param("locationIds") List<String> locationIds);

    /**
     * 根据位置ID删除图片
     *
     * @param locationId 位置ID
     * @return 删除的行数
     */
    int deleteByLocationId(@Param("locationId") String locationId);

    /**
     * 批量插入图片
     *
     * @param imagePOs 图片列表
     * @return 插入的行数
     */
    int batchInsert(@Param("list") List<LocationImagePO> imagePOs);
}
