package com.xiaoo.kaleido.wardrobe.domain.outfit.service;

import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.dto.OutfitImageInfoDTO;

import java.util.List;

/**
 * 穿搭领域服务接口
 * <p>
 * 处理穿搭相关的业务逻辑，包括穿搭创建、信息更新、服装管理、图片管理、穿着记录等核心领域操作
 * 遵循DDD原则：负责参数校验（针对controller未校验部分）+ 业务规则验证 + 聚合根操作
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
public interface IOutfitDomainService {

    /**
     * 创建穿搭（包含服装和图片）

     * 根据用户ID、穿搭名称、描述等信息创建新穿搭，并包含服装列表和图片信息
     * 注意：用户只提供文件路径，图片的width、height、imageSize、imageType等字段后续通过MinIO服务获取
     *
     * @param userId      用户ID，不能为空（已在controller层校验）
     * @param name        穿搭名称，不能为空（已在controller层校验）
     * @param description 穿搭描述，可为空
     * @param clothingIds 服装ID列表，不能为空且至少包含1件服装
     * @param images      图片信息列表，不能为空
     * @return 穿搭聚合根，包含完整的穿搭信息、服装列表和图片
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当业务规则校验失败时抛出
     */
    OutfitAggregate createOutfitWithClothingsAndImages(
            String userId,
            String name,
            String description,
            List<String> clothingIds,
            List<OutfitImageInfoDTO> images);

    /**
     * 根据ID查找穿搭，如果不存在则抛出异常

     * 用于命令操作中需要确保穿搭存在的场景
     * 注意：会加载穿搭的基本信息、服装列表、图片列表和穿着记录
     *
     * @param outfitId 穿搭ID字符串，不能为空
     * @return 穿搭聚合根，包含完整的穿搭信息
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当穿搭不存在时抛出
     */
    OutfitAggregate findByIdOrThrow(String outfitId);

    /**
     * 根据ID和用户ID查找穿搭，如果不存在或用户不匹配则抛出异常

     * 用于需要验证用户权限的查询场景
     * 注意：会加载穿搭的基本信息、服装列表、图片列表和穿着记录
     *
     * @param outfitId 穿搭ID字符串，不能为空
     * @param userId 用户ID字符串，不能为空
     * @return 穿搭聚合根，包含完整的穿搭信息
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当穿搭不存在或用户不匹配时抛出
     */
    OutfitAggregate findByIdAndUserIdOrThrow(String outfitId, String userId);

    /**
     * 更新穿搭信息（包含服装和图片）

     * 更新穿搭的基本信息、服装列表和图片信息
     * 注意：服装列表为全量替换，图片列表为全量替换
     *
     * @param outfitId    穿搭ID，不能为空
     * @param userId      用户ID，不能为空（已在controller层校验）
     * @param name        新穿搭名称，不能为空（已在controller层校验）
     * @param description 新穿搭描述，可为空
     * @param clothingIds 新服装ID列表，不能为空且至少包含1件服装
     * @param images      新图片信息列表，不能为空
     * @return 更新后的穿搭聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当业务规则校验失败时抛出
     */
    OutfitAggregate updateOutfit(
            String outfitId,
            String userId,
            String name,
            String description,
            List<String> clothingIds,
            List<OutfitImageInfoDTO> images);

    /**
     * 记录穿搭穿着

     * 用户穿着该搭配时调用，更新穿着次数和最后穿着日期，并创建穿着记录
     * 注意：wearDate自动使用当前系统时间
     *
     * @param outfitId 穿搭ID，不能为空
     * @param userId   用户ID，不能为空（已在controller层校验）
     * @param notes    备注，可为空
     * @return 更新后的穿搭聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当业务规则校验失败时抛出
     */
    OutfitAggregate recordOutfitWear(String outfitId, String userId, String notes);

    /**
     * 删除穿搭

     * 删除穿搭（逻辑删除或物理删除，根据业务规则）
     *
     * @param outfitId 穿搭ID，不能为空
     * @param userId   用户ID，不能为空（已在controller层校验）
     * @return 删除后的穿搭聚合根
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当业务规则校验失败时抛出
     */
    OutfitAggregate deleteOutfit(String outfitId, String userId);

    /**
     * 批量获取用户的穿搭列表
     *
     * @param userId 用户ID，不能为空
     * @return 穿搭聚合根列表（包含基本信息，按需加载服装、图片和穿着记录）
     * @throws com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException 当参数无效时抛出
     */
    List<OutfitAggregate> findOutfitsByUserId(String userId);
}
