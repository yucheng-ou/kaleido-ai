package com.xiaoo.kaleido.wardrobe.domain.service;

import com.xiaoo.kaleido.wardrobe.domain.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.model.entity.LocationImage;

import java.util.List;

/**
 * 位置领域服务接口
 * <p>
 * 处理存储位置相关的业务逻辑，包括位置创建、状态管理、信息更新、图片管理等核心领域操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface ILocationDomainService {

    /**
     * 创建存储位置
     * <p>
     * 根据用户ID、位置名称等信息创建新位置，系统会自动生成位置ID并设置初始状态
     *
     * @param userId      用户ID，不能为空
     * @param name        位置名称，不能为空
     * @param description 描述，可为空
     * @param address     具体地址，可为空
     * @return 存储位置聚合根，包含完整的位置信息
     */
    StorageLocationAggregate createLocation(
            String userId,
            String name,
            String description,
            String address);

    /**
     * 根据ID查找位置，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保位置存在的场景
     *
     * @param locationId 位置ID字符串，不能为空
     * @return 存储位置聚合根，包含完整的位置信息和图片列表
     */
    StorageLocationAggregate findByIdOrThrow(String locationId);

    /**
     * 更新位置信息
     * <p>
     * 更新位置的基本信息，不包括图片
     *
     * @param locationId  位置ID，不能为空
     * @param name        新位置名称，不能为空
     * @param description 新描述，可为空
     * @param address     新具体地址，可为空
     * @return 更新后的存储位置聚合根
     */
    StorageLocationAggregate updateLocation(
            String locationId,
            String name,
            String description,
            String address);

    /**
     * 启用位置
     * <p>
     * 将位置状态设置为启用
     *
     * @param locationId 位置ID，不能为空
     * @return 启用后的存储位置聚合根
     */
    StorageLocationAggregate enableLocation(String locationId);

    /**
     * 禁用位置
     * <p>
     * 将位置状态设置为禁用
     *
     * @param locationId 位置ID，不能为空
     * @return 禁用后的存储位置聚合根
     */
    StorageLocationAggregate disableLocation(String locationId);

    /**
     * 添加图片
     *
     * @param locationId     位置ID，不能为空
     * @param path           图片路径（在minio中的文件路径），不能为空
     * @param imageOrder     排序序号，不能为空
     * @param isPrimary      是否为主图，不能为空
     * @param fileName       原始文件名，可为空
     * @param fileSize       文件大小，可为空
     * @param mimeType       文件类型，可为空
     * @param width          图片宽度，可为空
     * @param height         图片高度，可为空
     * @param thumbnailPath  缩略图路径（在minio中的文件路径），可为空
     * @param description    图片描述，可为空
     * @return 添加的图片实体
     */
    LocationImage addImage(
            String locationId,
            String path,
            Integer imageOrder,
            Boolean isPrimary,
            String fileName,
            Long fileSize,
            String mimeType,
            Integer width,
            Integer height,
            String thumbnailPath,
            String description);

    /**
     * 移除图片
     *
     * @param locationId 位置ID，不能为空
     * @param imageId    图片ID，不能为空
     * @return 如果成功移除返回true，如果图片不存在返回false
     */
    boolean removeImage(String locationId, String imageId);

    /**
     * 设置主图
     *
     * @param locationId 位置ID，不能为空
     * @param imageId    主图ID，不能为空
     * @return 更新后的存储位置聚合根
     */
    StorageLocationAggregate setPrimaryImage(String locationId, String imageId);

    /**
     * 验证位置名称在用户下的唯一性
     * <p>
     * 验证同用户下位置名称是否唯一
     *
     * @param userId 用户ID，不能为空
     * @param name   位置名称，不能为空
     * @return 如果位置名称在用户下唯一返回true，否则返回false
     */
    boolean isLocationNameUnique(String userId, String name);

    /**
     * 批量获取用户的位置列表
     *
     * @param userId 用户ID，不能为空
     * @return 存储位置聚合根列表
     */
    List<StorageLocationAggregate> findByUserId(String userId);
}
