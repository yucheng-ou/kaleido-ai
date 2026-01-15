package com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.WearRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 穿着记录仓储接口
 * <p>
 * 定义穿着记录实体的数据访问操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IWearRecordRepository {

    /**
     * 保存穿着记录
     *
     * @param wearRecord 穿着记录实体
     * @return 保存后的穿着记录实体
     */
    WearRecord save(WearRecord wearRecord);

    /**
     * 根据ID查找穿着记录
     *
     * @param id 记录ID
     * @return 穿着记录实体（如果存在）
     */
    Optional<WearRecord> findById(String id);

    /**
     * 根据搭配ID查找所有穿着记录
     *
     * @param outfitId 搭配ID
     * @return 穿着记录实体列表
     */
    List<WearRecord> findByOutfitId(String outfitId);

    /**
     * 根据用户ID查找所有穿着记录
     *
     * @param userId 用户ID
     * @return 穿着记录实体列表
     */
    List<WearRecord> findByUserId(String userId);

    /**
     * 根据用户ID和穿着日期查找穿着记录
     *
     * @param userId   用户ID
     * @param wearDate 穿着日期
     * @return 穿着记录实体列表
     */
    List<WearRecord> findByUserIdAndWearDate(String userId, LocalDate wearDate);

    /**
     * 根据搭配ID和穿着日期查找穿着记录
     *
     * @param outfitId 搭配ID
     * @param wearDate 穿着日期
     * @return 穿着记录实体（如果存在）
     */
    Optional<WearRecord> findByOutfitIdAndWearDate(String outfitId, LocalDate wearDate);

    /**
     * 删除穿着记录
     *
     * @param id 记录ID
     * @return 如果成功删除返回true，否则返回false
     */
    boolean deleteById(String id);

    /**
     * 根据搭配ID删除所有穿着记录
     *
     * @param outfitId 搭配ID
     * @return 删除的数量
     */
    int deleteByOutfitId(String outfitId);

    /**
     * 批量保存穿着记录
     *
     * @param wearRecords 穿着记录实体列表
     * @return 保存后的穿着记录实体列表
     */
    List<WearRecord> saveAll(List<WearRecord> wearRecords);

    /**
     * 检查记录是否存在
     *
     * @param id 记录ID
     * @return 如果记录存在返回true，否则返回false
     */
    boolean existsById(String id);

    /**
     * 检查用户是否在指定日期穿着过指定搭配
     *
     * @param userId   用户ID
     * @param outfitId 搭配ID
     * @param wearDate 穿着日期
     * @return 如果已穿着返回true，否则返回false
     */
    boolean existsByUserIdAndOutfitIdAndWearDate(String userId, String outfitId, LocalDate wearDate);
}
