package com.xiaoo.kaleido.wardrobe.domain.location.service;

import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.LocationRecordAggregate;

import java.util.List;

/**
 * 位置记录领域服务接口
 * <p>
 * 处理位置记录相关的业务逻辑，包括位置记录创建、查询等核心领域操作
 * 遵循领域服务职责：包含参数校验与聚合根的修改，可以查询数据库进行参数校验
 * 注意：不能直接调用仓储层写入或更新数据库，只返回聚合根
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
public interface ILocationRecordDomainService {

    /**
     * 创建位置记录

     * 创建新的位置记录，用于记录服装位置变更
     * 包含参数校验：服装和位置的存在性校验、服装是否已在目标位置校验等
     *
     * @param clothingId 服装ID，不能为空
     * @param locationId 位置ID，不能为空
     * @param userId     用户ID，不能为空
     * @param notes      备注，可为空
     * @return 位置记录聚合根
     */
    LocationRecordAggregate createLocationRecord(
            String clothingId,
            String locationId,
            String userId,
            String notes);

    /**
     * 根据服装ID查询当前位置记录

     * 查询指定服装的当前位置记录（is_current = 1）
     *
     * @param clothingId 服装ID，不能为空
     * @return 当前位置记录聚合根，如果不存在返回null
     */
    LocationRecordAggregate findCurrentByClothingId(String clothingId);

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
     * 检查服装是否在指定位置

     * 检查指定服装是否在指定位置（存在is_current = 1的记录）
     *
     * @param clothingId 服装ID，不能为空
     * @param locationId 位置ID，不能为空
     * @return 如果存在当前位置记录返回true，否则返回false
     */
    boolean isClothingInLocation(String clothingId, String locationId);

    /**
     * 统计位置中的当前服装数量

     * 统计指定位置中的当前服装数量（is_current = 1的记录数）
     *
     * @param locationId 位置ID，不能为空
     * @return 当前位置中的服装数量
     */
    int countCurrentByLocationId(String locationId);
}
