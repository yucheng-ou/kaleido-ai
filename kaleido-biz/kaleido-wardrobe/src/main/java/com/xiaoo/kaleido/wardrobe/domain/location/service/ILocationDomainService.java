package com.xiaoo.kaleido.wardrobe.domain.location.service;


import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.service.dto.LocationImageInfoDTO;

import java.util.List;

/**
 * 位置领域服务接口
 * <p>
 * 处理位置相关的业务逻辑，包括位置创建、信息更新、图片管理等核心领域操作
 * 遵循领域服务职责：包含参数校验与聚合根的修改，可以查询数据库进行参数校验
 * 注意：不能直接调用仓储层写入或更新数据库，只返回聚合根
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
public interface ILocationDomainService {

    /**
     * 创建位置（包含图片）
     * <p>
     * 根据用户ID、位置名称、描述、地址和图片信息创建新位置
     * 包含参数校验：位置名称唯一性校验、图片数量限制校验等
     *
     * @param userId      用户ID，不能为空
     * @param name        位置名称，不能为空
     * @param description 位置描述，可为空
     * @param address     具体地址，可为空
     * @param images      图片信息列表，可为空
     * @return 位置聚合根，包含完整的位置信息和图片
     */
    StorageLocationAggregate createLocationWithImages(
            String userId,
            String name,
            String description,
            String address,
            List<LocationImageInfoDTO> images);

    /**
     * 根据ID查找位置，如果不存在或已删除则抛出异常
     * <p>
     * 用于命令操作中需要确保位置存在的场景
     *
     * @param locationId 位置ID字符串，不能为空
     * @return 位置聚合根，包含完整的位置信息
     */
    StorageLocationAggregate findByIdOrThrow(String locationId);

    /**
     * 更新位置信息（包含图片）
     * <p>
     * 更新位置的名称、描述、地址和图片信息
     * 包含参数校验：位置名称唯一性校验、图片数量限制校验等
     *
     * @param locationId  位置ID，不能为空
     * @param name        新位置名称，不能为空
     * @param description 新位置描述，可为空
     * @param address     新具体地址，可为空
     * @param images      新图片信息列表，可为空
     * @return 更新后的位置聚合根
     */
    StorageLocationAggregate updateLocationWithImages(
            String locationId,
            String name,
            String description,
            String address,
            List<LocationImageInfoDTO> images);

    /**
     * 验证位置名称的唯一性
     * <p>
     * 验证同一用户下位置名称是否唯一（排除已删除的位置）
     *
     * @param userId 用户ID，不能为空
     * @param name   位置名称，不能为空
     * @return 如果位置名称唯一返回true，否则返回false
     */
    boolean isLocationNameUnique(String userId, String name);

    /**
     * 检查位置是否可删除
     * <p>
     * 检查位置是否被服装引用，如果被引用则不能删除
     *
     * @param locationId 位置ID，不能为空
     * @return 如果位置可删除返回true，否则返回false
     */
    boolean canDeleteLocation(String locationId);

    /**
     * 验证并获取可删除的位置聚合根
     * <p>
     * 验证位置是否可删除（如是否有服装引用），如果可删除则返回位置聚合根
     * 如果不可删除则抛出异常
     *
     * @param locationId 位置ID，不能为空
     * @return 可删除的位置聚合根
     * @throws WardrobeException 当位置不可删除时抛出
     */
    StorageLocationAggregate validateAndGetForDeletion(String locationId);

    /**
     * 设置主图
     * <p>
     * 设置指定图片为主图，包含图片存在性校验
     *
     * @param locationId 位置ID，不能为空
     * @param imageId    图片ID，不能为空
     * @return 更新后的位置聚合根
     */
    StorageLocationAggregate setPrimaryImage(String locationId, String imageId);

    /**
     * 根据用户ID查找所有位置
     * <p>
     * 查找指定用户的所有位置（排除已删除的位置）
     *
     * @param userId 用户ID，不能为空
     * @return 位置聚合根列表
     */
    List<StorageLocationAggregate> findByUserId(String userId);
}
