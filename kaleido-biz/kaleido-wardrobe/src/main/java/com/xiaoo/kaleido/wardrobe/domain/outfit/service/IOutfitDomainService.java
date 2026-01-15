package com.xiaoo.kaleido.wardrobe.domain.outfit.service;

import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.WearRecord;

import java.time.LocalDate;
import java.util.List;

/**
 * 搭配领域服务接口
 * <p>
 * 处理搭配相关的业务逻辑，包括搭配创建、状态管理、信息更新、服装关联管理、图片管理、穿着记录管理等核心领域操作
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
public interface IOutfitDomainService {

    /**
     * 创建搭配
     * <p>
     * 根据用户ID、搭配名称等信息创建新搭配，系统会自动生成搭配ID并设置初始状态
     *
     * @param userId      用户ID，不能为空
     * @param name        搭配名称，不能为空
     * @param description 搭配描述，可为空
     * @return 搭配聚合根，包含完整的搭配信息
     */
    OutfitAggregate createOutfit(
            String userId,
            String name,
            String description);

    /**
     * 根据ID查找搭配，如果不存在则抛出异常
     * <p>
     * 用于命令操作中需要确保搭配存在的场景
     *
     * @param outfitId 搭配ID字符串，不能为空
     * @return 搭配聚合根，包含完整的搭配信息和关联列表
     */
    OutfitAggregate findByIdOrThrow(String outfitId);

    /**
     * 更新搭配信息
     * <p>
     * 更新搭配的基本信息，不包括服装关联、图片和穿着记录
     *
     * @param outfitId    搭配ID，不能为空
     * @param name        新搭配名称，不能为空
     * @param description 新搭配描述，可为空
     * @return 更新后的搭配聚合根
     */
    OutfitAggregate updateOutfit(
            String outfitId,
            String name,
            String description);

    /**
     * 启用搭配
     * <p>
     * 将搭配状态设置为启用
     *
     * @param outfitId 搭配ID，不能为空
     * @return 启用后的搭配聚合根
     */
    OutfitAggregate enableOutfit(String outfitId);

    /**
     * 禁用搭配
     * <p>
     * 将搭配状态设置为禁用
     *
     * @param outfitId 搭配ID，不能为空
     * @return 禁用后的搭配聚合根
     */
    OutfitAggregate disableOutfit(String outfitId);

    /**
     * 添加服装关联
     *
     * @param outfitId   搭配ID，不能为空
     * @param clothingId 服装ID，不能为空
     * @return 更新后的搭配聚合根
     */
    OutfitAggregate addClothingAssociation(String outfitId, String clothingId);

    /**
     * 移除服装关联
     *
     * @param outfitId   搭配ID，不能为空
     * @param clothingId 服装ID，不能为空
     * @return 如果成功移除返回true，如果关联不存在返回false
     */
    boolean removeClothingAssociation(String outfitId, String clothingId);

    /**
     * 添加图片
     *
     * @param outfitId       搭配ID，不能为空
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
    OutfitImage addImage(
            String outfitId,
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
     * @param outfitId 搭配ID，不能为空
     * @param imageId  图片ID，不能为空
     * @return 如果成功移除返回true，如果图片不存在返回false
     */
    boolean removeImage(String outfitId, String imageId);

    /**
     * 设置主图
     *
     * @param outfitId 搭配ID，不能为空
     * @param imageId  主图ID，不能为空
     * @return 更新后的搭配聚合根
     */
    OutfitAggregate setPrimaryImage(String outfitId, String imageId);

    /**
     * 添加穿着记录
     *
     * @param outfitId 搭配ID，不能为空
     * @param wearDate 穿着日期，不能为空
     * @param notes    备注，可为空
     * @return 添加的穿着记录实体
     */
    WearRecord addWearRecord(String outfitId, LocalDate wearDate, String notes);

    /**
     * 验证搭配名称在用户下的唯一性
     * <p>
     * 验证同用户下搭配名称是否唯一
     *
     * @param userId 用户ID，不能为空
     * @param name   搭配名称，不能为空
     * @return 如果搭配名称在用户下唯一返回true，否则返回false
     */
    boolean isOutfitNameUnique(String userId, String name);

    /**
     * 批量获取用户的搭配列表
     *
     * @param userId 用户ID，不能为空
     * @return 搭配聚合根列表
     */
    List<OutfitAggregate> findByUserId(String userId);

    /**
     * 获取搭配的穿着记录
     *
     * @param outfitId 搭配ID，不能为空
     * @return 穿着记录列表
     */
    List<WearRecord> getWearRecords(String outfitId);
}
