package com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.LocationRecordAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 位置记录仓储接口
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
public interface ILocationRecordRepository {

    /**
     * 保存位置记录聚合根

     * 保存位置记录聚合根到数据库
     *
     * @param locationRecordAggregate 位置记录聚合根，不能为空
     */
    void save(LocationRecordAggregate locationRecordAggregate);

    /**
     * 更新位置记录聚合根

     * 更新位置记录聚合根信息到数据库
     *
     * @param locationRecordAggregate 位置记录聚合根，不能为空
     */
    void update(LocationRecordAggregate locationRecordAggregate);

    /**
     * 根据ID查找位置记录聚合根

     * 根据位置记录ID查询位置记录聚合根，返回Optional对象
     *
     * @param id 位置记录ID，不能为空
     * @return 位置记录聚合根（如果存在），Optional.empty()表示位置记录不存在
     */
    Optional<LocationRecordAggregate> findById(String id);

    /**
     * 根据ID查找位置记录聚合根，如果不存在则抛出异常

     * 用于命令操作中需要确保位置记录存在的场景
     *
     * @param id 位置记录ID，不能为空
     * @return 位置记录聚合根
     */
    LocationRecordAggregate findByIdOrThrow(String id);

    /**
     * 根据服装ID查询当前位置记录

     * 查询指定服装的当前位置记录（is_current = 1）
     *
     * @param clothingId 服装ID，不能为空
     * @return 当前位置记录聚合根，如果不存在返回Optional.empty()
     */
    Optional<LocationRecordAggregate> findCurrentByClothingId(String clothingId);

    /**
     * 根据位置ID查询所有当前位置记录

     * 查询指定位置的所有当前位置记录（is_current = 1）
     *
     * @param locationId 位置ID，不能为空
     * @return 当前位置记录聚合根列表
     */
    List<LocationRecordAggregate> findCurrentByLocationId(String locationId);

    /**
     * 根据用户ID查询所有当前位置记录

     * 查询指定用户的所有当前位置记录（is_current = 1）
     *
     * @param userId 用户ID，不能为空
     * @return 当前位置记录聚合根列表
     */
    List<LocationRecordAggregate> findCurrentByUserId(String userId);

    /**
     * 根据服装ID查询所有位置记录（历史记录）

     * 查询指定服装的所有位置记录，按记录时间倒序排列
     *
     * @param clothingId 服装ID，不能为空
     * @return 位置记录聚合根列表
     */
    List<LocationRecordAggregate> findByClothingId(String clothingId);

    /**
     * 将服装的所有位置记录标记为非当前
     * 将指定服装的所有位置记录的is_current字段更新为0
     * 用于服装位置变更时，将旧位置记录标记为非当前
     *
     * @param clothingId 服装ID，不能为空
     */
    void markAllAsNotCurrentByClothingId(String clothingId);

    /**
     * 检查服装是否在指定位置

     * 检查指定服装是否在指定位置（存在is_current = 1的记录）
     *
     * @param clothingId 服装ID，不能为空
     * @param locationId 位置ID，不能为空
     * @return 如果存在当前位置记录返回true，否则返回false
     */
    boolean existsCurrentByClothingIdAndLocationId(String clothingId, String locationId);

    /**
     * 统计位置中的当前服装数量

     * 统计指定位置中的当前服装数量（is_current = 1的记录数）
     *
     * @param locationId 位置ID，不能为空
     * @return 当前位置中的服装数量
     */
    int countCurrentByLocationId(String locationId);
}
