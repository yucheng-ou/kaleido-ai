package com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.Outfit;

import java.util.List;
import java.util.Optional;

/**
 * 搭配仓储接口
 * <p>
 * 定义搭配实体的数据访问操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IOutfitRepository {

    /**
     * 保存搭配
     *
     * @param outfit 搭配实体
     * @return 保存后的搭配实体
     */
    Outfit save(Outfit outfit);

    /**
     * 根据ID查找搭配
     *
     * @param id 搭配ID
     * @return 搭配实体（如果存在）
     */
    Optional<Outfit> findById(String id);

    /**
     * 根据用户ID查找所有搭配
     *
     * @param userId 用户ID
     * @return 搭配实体列表
     */
    List<Outfit> findByUserId(String userId);

    /**
     * 根据用户ID和名称查找搭配
     *
     * @param userId 用户ID
     * @param name   搭配名称
     * @return 搭配实体（如果存在）
     */
    Optional<Outfit> findByUserIdAndName(String userId, String name);

    /**
     * 删除搭配
     *
     * @param id 搭配ID
     * @return 如果成功删除返回true，否则返回false
     */
    boolean deleteById(String id);

    /**
     * 检查搭配名称在用户下是否唯一
     *
     * @param userId 用户ID
     * @param name   搭配名称
     * @return 如果搭配名称在用户下唯一返回true，否则返回false
     */
    boolean isNameUnique(String userId, String name);

    /**
     * 检查搭配是否存在
     *
     * @param id 搭配ID
     * @return 如果搭配存在返回true，否则返回false
     */
    boolean existsById(String id);
}
