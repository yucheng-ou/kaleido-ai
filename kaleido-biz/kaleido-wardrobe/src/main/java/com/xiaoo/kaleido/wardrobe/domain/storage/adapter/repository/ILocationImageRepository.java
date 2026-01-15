package com.xiaoo.kaleido.wardrobe.domain.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.model.entity.LocationImage;

import java.util.List;
import java.util.Optional;

/**
 * 位置图片仓储接口
 * <p>
 * 定义位置图片实体的数据访问操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface ILocationImageRepository {

    /**
     * 保存位置图片
     *
     * @param locationImage 位置图片实体
     * @return 保存后的位置图片实体
     */
    LocationImage save(LocationImage locationImage);

    /**
     * 根据ID查找位置图片
     *
     * @param id 图片ID
     * @return 位置图片实体（如果存在）
     */
    Optional<LocationImage> findById(String id);

    /**
     * 根据位置ID查找所有位置图片
     *
     * @param locationId 位置ID
     * @return 位置图片实体列表
     */
    List<LocationImage> findByLocationId(String locationId);

    /**
     * 根据位置ID和是否为主图查找位置图片
     *
     * @param locationId 位置ID
     * @param isPrimary  是否为主图
     * @return 位置图片实体列表
     */
    List<LocationImage> findByLocationIdAndIsPrimary(String locationId, Boolean isPrimary);

    /**
     * 删除位置图片
     *
     * @param id 图片ID
     * @return 如果成功删除返回true，否则返回false
     */
    boolean deleteById(String id);

    /**
     * 批量保存位置图片
     *
     * @param locationImages 位置图片实体列表
     * @return 保存后的位置图片实体列表
     */
    List<LocationImage> saveAll(List<LocationImage> locationImages);

    /**
     * 根据位置ID删除所有位置图片
     *
     * @param locationId 位置ID
     * @return 删除的数量
     */
    int deleteByLocationId(String locationId);

    /**
     * 检查图片是否存在
     *
     * @param id 图片ID
     * @return 如果图片存在返回true，否则返回false
     */
    boolean existsById(String id);
}
