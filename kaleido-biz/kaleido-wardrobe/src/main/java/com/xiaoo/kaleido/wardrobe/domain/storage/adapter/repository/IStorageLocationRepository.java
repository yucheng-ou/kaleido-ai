package com.xiaoo.kaleido.wardrobe.domain.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.model.entity.StorageLocation;

import java.util.List;
import java.util.Optional;

/**
 * 存储位置仓储接口
 * <p>
 * 定义存储位置实体的数据访问操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IStorageLocationRepository {

    /**
     * 保存存储位置
     *
     * @param storageLocation 存储位置实体
     * @return 保存后的存储位置实体
     */
    StorageLocation save(StorageLocation storageLocation);

    /**
     * 根据ID查找存储位置
     *
     * @param id 位置ID
     * @return 存储位置实体（如果存在）
     */
    Optional<StorageLocation> findById(String id);

    /**
     * 根据用户ID查找所有存储位置
     *
     * @param userId 用户ID
     * @return 存储位置实体列表
     */
    List<StorageLocation> findByUserId(String userId);

    /**
     * 根据用户ID和名称查找存储位置
     *
     * @param userId 用户ID
     * @param name   位置名称
     * @return 存储位置实体（如果存在）
     */
    Optional<StorageLocation> findByUserIdAndName(String userId, String name);

    /**
     * 删除存储位置
     *
     * @param id 位置ID
     * @return 如果成功删除返回true，否则返回false
     */
    boolean deleteById(String id);

    /**
     * 检查位置名称在用户下是否唯一
     *
     * @param userId 用户ID
     * @param name   位置名称
     * @return 如果位置名称在用户下唯一返回true，否则返回false
     */
    boolean isNameUnique(String userId, String name);

    /**
     * 检查位置是否存在
     *
     * @param id 位置ID
     * @return 如果位置存在返回true，否则返回false
     */
    boolean existsById(String id);
}
