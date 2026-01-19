package com.xiaoo.kaleido.wardrobe.infrastructure.dao;

import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.LocationRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 位置记录数据访问对象
 * <p>
 * 负责位置记录表t_wardrobe_location_record的数据访问操作
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Mapper
public interface LocationRecordDao {

    /**
     * 插入位置记录
     *
     * @param locationRecordPO 位置记录持久化对象
     * @return 插入记录数
     */
    int insert(LocationRecordPO locationRecordPO);

    /**
     * 根据ID查询位置记录
     *
     * @param id 位置记录ID
     * @return 位置记录持久化对象
     */
    LocationRecordPO selectById(@Param("id") String id);

    /**
     * 根据服装ID查询当前位置记录
     *
     * @param clothingId 服装ID
     * @return 当前位置记录，如果不存在返回null
     */
    LocationRecordPO selectCurrentByClothingId(@Param("clothingId") String clothingId);

    /**
     * 根据位置ID查询所有当前位置记录
     *
     * @param locationId 位置ID
     * @return 当前位置记录列表
     */
    List<LocationRecordPO> selectCurrentByLocationId(@Param("locationId") String locationId);

    /**
     * 根据用户ID查询所有当前位置记录
     *
     * @param userId 用户ID
     * @return 当前位置记录列表
     */
    List<LocationRecordPO> selectCurrentByUserId(@Param("userId") String userId);

    /**
     * 根据服装ID查询所有位置记录（历史记录）
     *
     * @param clothingId 服装ID
     * @return 位置记录列表（按记录时间倒序）
     */
    List<LocationRecordPO> selectByClothingId(@Param("clothingId") String clothingId);

    /**
     * 更新位置记录的当前状态
     *
     * @param id        位置记录ID
     * @param isCurrent 是否为当前位置（0-否，1-是）
     * @return 更新记录数
     */
    int updateCurrentStatus(@Param("id") String id, @Param("isCurrent") Integer isCurrent);

    /**
     * 将服装的所有位置记录标记为非当前
     *
     * @param clothingId 服装ID
     * @return 更新记录数
     */
    int markAllAsNotCurrentByClothingId(@Param("clothingId") String clothingId);

    /**
     * 逻辑删除位置记录
     *
     * @param id 位置记录ID
     * @return 删除记录数
     */
    int delete(@Param("id") String id);

    /**
     * 检查服装是否在指定位置
     *
     * @param clothingId 服装ID
     * @param locationId 位置ID
     * @return 如果存在当前位置记录返回true，否则返回false
     */
    boolean existsCurrentByClothingIdAndLocationId(
            @Param("clothingId") String clothingId,
            @Param("locationId") String locationId);

    /**
     * 统计位置中的当前服装数量
     *
     * @param locationId 位置ID
     * @return 当前位置中的服装数量
     */
    int countCurrentByLocationId(@Param("locationId") String locationId);
}
