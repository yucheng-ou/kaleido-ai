package com.xiaoo.kaleido.wardrobe.domain.clothing.service;

import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity.ClothingImage;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 服装领域服务接口
 * <p>
 * 处理服装相关的业务逻辑，包括服装创建、状态管理、信息更新、图片管理、位置变更等核心领域操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IClothingDomainService {

    /**
     * 创建服装
     * <p>
     * 根据用户ID、服装名称、类型编码等信息创建新服装，系统会自动生成服装ID并设置初始状态
     *
     * @param userId             用户ID，不能为空
     * @param name               服装名称，不能为空
     * @param typeCode           服装类型编码，不能为空
     * @param colorCode          颜色编码，可为空
     * @param seasonCode         季节编码，可为空
     * @param brandId            品牌ID，可为空
     * @param size               尺码，可为空
     * @param purchaseDate       购买日期，可为空
     * @param price              价格，可为空
     * @param description        描述，可为空
     * @param currentLocationId  当前位置ID，可为空
     * @return 服装聚合根，包含完整的服装信息
     */
    ClothingAggregate createClothing(
            String userId,
            String name,
            String typeCode,
            String colorCode,
            String seasonCode,
            String brandId,
            String size,
            Date purchaseDate,
            BigDecimal price,
            String description,
            String currentLocationId);

    /**
     * 根据ID查找服装，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保服装存在的场景
     *
     * @param clothingId 服装ID字符串，不能为空
     * @return 服装聚合根，包含完整的服装信息和图片列表
     */
    ClothingAggregate findByIdOrThrow(String clothingId);

    /**
     * 更新服装信息
     * <p>
     * 更新服装的基本信息，不包括图片和位置
     *
     * @param clothingId   服装ID，不能为空
     * @param name         新服装名称，不能为空
     * @param typeCode     新服装类型编码，不能为空
     * @param colorCode    新颜色编码，可为空
     * @param seasonCode   新季节编码，可为空
     * @param brandId      新品牌ID，可为空
     * @param size         新尺码，可为空
     * @param purchaseDate 新购买日期，可为空
     * @param price        新价格，可为空
     * @param description  新描述，可为空
     * @return 更新后的服装聚合根
     */
    ClothingAggregate updateClothing(
            String clothingId,
            String name,
            String typeCode,
            String colorCode,
            String seasonCode,
            String brandId,
            String size,
            Date purchaseDate,
            BigDecimal price,
            String description);

    /**
     * 变更服装位置
     *
     * @param clothingId  服装ID，不能为空
     * @param locationId  新位置ID，不能为空
     * @return 更新后的服装聚合根
     */
    ClothingAggregate changeClothingLocation(String clothingId, String locationId);

    /**
     * 启用服装
     * <p>
     * 将服装状态设置为启用
     *
     * @param clothingId 服装ID，不能为空
     * @return 启用后的服装聚合根
     */
    ClothingAggregate enableClothing(String clothingId);

    /**
     * 禁用服装
     * <p>
     * 将服装状态设置为禁用
     *
     * @param clothingId 服装ID，不能为空
     * @return 禁用后的服装聚合根
     */
    ClothingAggregate disableClothing(String clothingId);

    /**
     * 添加图片
     *
     * @param clothingId     服装ID，不能为空
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
    ClothingImage addImage(
            String clothingId,
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
     * @param clothingId 服装ID，不能为空
     * @param imageId    图片ID，不能为空
     * @return 如果成功移除返回true，如果图片不存在返回false
     */
    boolean removeImage(String clothingId, String imageId);

    /**
     * 设置主图
     *
     * @param clothingId 服装ID，不能为空
     * @param imageId    主图ID，不能为空
     * @return 更新后的服装聚合根
     */
    ClothingAggregate setPrimaryImage(String clothingId, String imageId);

    /**
     * 验证服装名称在用户下的唯一性
     * <p>
     * 验证同用户下服装名称是否唯一
     *
     * @param userId 用户ID，不能为空
     * @param name   服装名称，不能为空
     * @return 如果服装名称在用户下唯一返回true，否则返回false
     */
    boolean isClothingNameUnique(String userId, String name);

    /**
     * 批量获取用户的服装列表
     *
     * @param userId 用户ID，不能为空
     * @return 服装聚合根列表
     */
    List<ClothingAggregate> findByUserId(String userId);
}
