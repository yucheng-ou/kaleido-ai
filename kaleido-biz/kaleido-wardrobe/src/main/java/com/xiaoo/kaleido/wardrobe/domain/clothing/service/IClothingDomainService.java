package com.xiaoo.kaleido.wardrobe.domain.clothing.service;

import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto.ClothingImageInfoDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 服装领域服务接口
 * <p>
 * 处理服装相关的业务逻辑，包括服装创建、状态管理、信息更新、图片管理、位置变更等核心领域操作
 * 遵循DDD原则：负责参数校验（针对controller未校验部分）+ 业务规则验证 + 聚合根操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IClothingDomainService {


    /**
     * 创建服装（包含图片）
     * <p>
     * 根据用户ID、服装名称、类型编码等信息创建新服装，并包含图片信息
     * 注意：用户只提供文件路径，图片的width、height、fileSize、mimeType等字段后续通过MinIO服务获取
     *
     * @param userId             用户ID，不能为空（已在controller层校验）
     * @param name               服装名称，不能为空（已在controller层校验）
     * @param typeCode           服装类型编码，不能为空（已在controller层校验）
     * @param colorCode          颜色编码，可为空
     * @param seasonCode         季节编码，可为空
     * @param brandId            品牌ID，可为空
     * @param size               尺码，可为空
     * @param purchaseDate       购买日期，可为空
     * @param price              价格，可为空
     * @param description        描述，可为空
     * @param currentLocationId  当前位置ID，可为空
     * @param images             图片信息列表，不能为空
     * @return 服装聚合根，包含完整的服装信息和图片
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当业务规则校验失败时抛出
     */
    ClothingAggregate createClothingWithImages(
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
            String currentLocationId,
            List<ClothingImageInfoDTO> images);

    /**
     * 根据ID查找服装，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保服装存在的场景
     * 注意：会加载服装的基本信息和图片列表
     *
     * @param clothingId 服装ID字符串，不能为空
     * @return 服装聚合根，包含完整的服装信息和图片列表
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当服装不存在时抛出
     */
    ClothingAggregate findByIdOrThrow(String clothingId);

    /**
     * 更新服装信息（包含图片）
     * <p>
     * 更新服装的基本信息和图片信息
     *
     * @param clothingId         服装ID，不能为空
     * @param userId             用户ID，不能为空（已在controller层校验）
     * @param name               新服装名称，不能为空（已在controller层校验）
     * @param typeCode           新服装类型编码，不能为空（已在controller层校验）
     * @param colorCode          新颜色编码，可为空
     * @param seasonCode         新季节编码，可为空
     * @param brandId            新品牌ID，可为空
     * @param size               新尺码，可为空
     * @param purchaseDate       新购买日期，可为空
     * @param price              新价格，可为空
     * @param description        新描述，可为空
     * @param currentLocationId  新当前位置ID，可为空
     * @param images             新图片信息列表，不能为空
     * @return 更新后的服装聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当业务规则校验失败时抛出
     */
    ClothingAggregate updateClothing(
            String clothingId,
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
            String currentLocationId,
            List<ClothingImageInfoDTO> images);

    /**
     * 变更服装位置
     *
     * @param clothingId  服装ID，不能为空
     * @param locationId  新位置ID，不能为空（已在controller层校验）
     * @return 更新后的服装聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当业务规则校验失败时抛出
     */
    ClothingAggregate changeClothingLocation(String clothingId, String locationId);

    /**
     * 删除服装
     * <p>
     * 删除服装（逻辑删除或物理删除，根据业务规则）
     *
     * @param clothingId 服装ID，不能为空
     * @param userId     用户ID，不能为空（已在controller层校验）
     * @return 删除后的服装聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当业务规则校验失败时抛出
     */
    ClothingAggregate deleteClothing(String clothingId, String userId);

    /**
     * 批量获取用户的服装列表
     *
     * @param userId 用户ID，不能为空
     * @return 服装聚合根列表（包含基本信息，按需加载图片）
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当参数无效时抛出
     */
    List<ClothingAggregate> findByUserId(String userId);
}
