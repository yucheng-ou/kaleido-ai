package com.xiaoo.kaleido.wardrobe.application.command;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.api.tag.IRpcTagService;
import com.xiaoo.kaleido.api.tag.command.AssociateEntityCommand;
import com.xiaoo.kaleido.api.tag.command.DissociateEntityCommand;
import com.xiaoo.kaleido.api.wardrobe.command.CreateClothingWithImagesCommand;
import com.xiaoo.kaleido.api.wardrobe.command.ClothingImageInfoCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateClothingCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.file.IClothingFileService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IClothingRepository;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IClothingDomainService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto.ClothingImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRecordRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.LocationRecordAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationRecordDomainService;
import com.xiaoo.kaleido.wardrobe.types.EntityTypeConstants;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服装命令服务
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClothingCommandService {

    private final IClothingDomainService clothingDomainService;
    private final IClothingRepository clothingRepository;
    private final IClothingFileService clothingFileService;
    private final ILocationRecordDomainService locationRecordDomainService;
    private final ILocationRecordRepository locationRecordRepository;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcTagService rpcTagService;

    /**
     * 创建服装（包含图片）
     *
     * @param command 创建服装命令
     * @return 创建的服装ID
     */
    @Transactional(rollbackFor = Exception.class)
    public String createClothingWithImages(String userId,CreateClothingWithImagesCommand command) {
        // 1.准备图片信息
        List<ClothingImageInfoCommand> imageInfos = command.getImages();

        // 2.使用图片处理服务转换图片信息
        List<ClothingImageInfoDTO> domainImageInfos = clothingFileService.convertorImageInfo(imageInfos);

        // 3.调用领域服务创建服装
        ClothingAggregate clothing = clothingDomainService.createClothingWithImages(
                userId,
                command.getName(),
                command.getTypeCode(),
                command.getColorCode(),
                command.getSeasonCode(),
                command.getBrandId(),
                command.getSize(),
                command.getPurchaseDate(),
                command.getPrice(),
                command.getDescription(),
                command.getCurrentLocationId(),
                domainImageInfos
        );

        // 4.保存服装
        clothingRepository.save(clothing);

        // 5.如果提供了位置ID，创建位置记录
        if (StrUtil.isNotBlank(command.getCurrentLocationId())) {
            LocationRecordAggregate locationRecord = locationRecordDomainService.createLocationRecord(
                    clothing.getId(),
                    command.getCurrentLocationId(),
                    userId
            );
            locationRecordRepository.save(locationRecord);
            
            log.info("服装位置记录创建成功，服装ID: {}, 位置ID: {}, 位置记录ID: {}",
                    clothing.getId(), command.getCurrentLocationId(), locationRecord.getId());
        }

        // 6.记录日志
        log.info("服装创建成功，服装ID: {}, 用户ID: {}, 服装名称: {}",
                clothing.getId(), userId, command.getName());

        return clothing.getId();
    }

    /**
     * 更新服装信息（包含图片）
     *
     * @param command 更新服装命令
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateClothing(String userId, UpdateClothingCommand command) {
        // 1.获取更新前的服装信息，用于比较位置变化
        ClothingAggregate existingClothing = clothingDomainService.findByIdOrThrow(command.getClothingId());
        String oldLocationId = existingClothing.getCurrentLocationId();
        String newLocationId = command.getCurrentLocationId();
        
        // 2.准备图片信息
        List<ClothingImageInfoCommand> imageInfos = command.getImages();

        // 3.使用图片处理服务转换图片信息
        List<ClothingImageInfoDTO> domainImageInfos = clothingFileService.convertorImageInfo(imageInfos);

        // 4.调用领域服务更新服装
        ClothingAggregate clothing = clothingDomainService.updateClothing(
                command.getClothingId(),
                userId,
                command.getName(),
                command.getTypeCode(),
                command.getColorCode(),
                command.getSeasonCode(),
                command.getBrandId(),
                command.getSize(),
                command.getPurchaseDate(),
                command.getPrice(),
                command.getDescription(),
                command.getCurrentLocationId(),
                domainImageInfos
        );

        // 5.保存服装
        clothingRepository.update(clothing);

        // 6.检查位置是否发生变化，如果变化则创建位置记录
        boolean locationChanged = checkLocationChanged(oldLocationId, newLocationId);
        if (locationChanged && StrUtil.isNotBlank(newLocationId)) {
            // 将旧位置记录标记为非当前
            locationRecordRepository.markAllAsNotCurrentByClothingId(command.getClothingId());
            
            // 创建新的位置记录
            LocationRecordAggregate locationRecord = locationRecordDomainService.createLocationRecord(
                    command.getClothingId(),
                    newLocationId,
                    userId
            );
            locationRecordRepository.save(locationRecord);
            
            log.info("服装位置变更记录创建成功，服装ID: {}, 旧位置ID: {}, 新位置ID: {}, 位置记录ID: {}",
                    command.getClothingId(), oldLocationId, newLocationId, locationRecord.getId());
        }

        // 7.记录日志
        log.info("服装更新成功，服装ID: {}, 用户ID: {}, 新名称: {}",
                command.getClothingId(), userId, command.getName());
    }

    /**
     * 删除服装
     *
     * @param clothingId 服装ID
     * @param userId     用户ID
     */
    public void deleteClothing(String clothingId, String userId) {
        // 1.调用领域服务删除服装
        ClothingAggregate clothing = clothingDomainService.deleteClothing(clothingId, userId);

        // 2.更新服装状态（逻辑删除）
        clothingRepository.delete(clothingId);

        // 3.记录日志
        log.info("服装删除成功，服装ID: {}, 用户ID: {}", clothingId, userId);
    }

    /**
     * 为服装添加标签
     *
     * @param userId     用户ID
     * @param clothingId 服装ID
     * @param tagId      标签ID
     */
    public void associateTagToClothing(String userId, String clothingId, String tagId) {
        // 1. 验证服装是否存在且属于当前用户
        ClothingAggregate clothing = clothingDomainService.findByIdOrThrow(clothingId);
        if (!clothing.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_OWNER_MISMATCH, "只有服装所有者可以操作");
        }
        
        // 2. 构建标签关联命令
        AssociateEntityCommand command = AssociateEntityCommand.builder()
                .tagId(tagId)
                .entityId(clothingId)
                .entityTypeCode(EntityTypeConstants.CLOTHING)
                .build();
        
        // 3. 调用标签RPC服务
        Result<Void> result = rpcTagService.associateTags(userId, command);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            throw WardrobeException.of(WardrobeErrorCode.TAG_ASSOCIATION_FAILED, "标签关联失败: " + result.getMsg());
        }
        
        // 4. 记录日志
        log.info("服装标签关联成功，用户ID: {}, 服装ID: {}, 标签ID: {}", userId, clothingId, tagId);
    }

    /**
     * 从服装移除标签
     *
     * @param userId     用户ID
     * @param clothingId 服装ID
     * @param tagId      标签ID
     */
    public void dissociateTagFromClothing(String userId, String clothingId, String tagId) {
        // 1. 验证服装是否存在且属于当前用户
        ClothingAggregate clothing = clothingDomainService.findByIdOrThrow(clothingId);
        if (!clothing.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_OWNER_MISMATCH, "只有服装所有者可以操作");
        }
        
        // 2. 构建标签取消关联命令
        DissociateEntityCommand command = DissociateEntityCommand.builder()
                .tagId(tagId)
                .entityId(clothingId)
                .build();
        
        // 3. 调用标签RPC服务
        Result<Void> result = rpcTagService.dissociateTags(userId, command);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            throw WardrobeException.of(WardrobeErrorCode.TAG_DISSOCIATION_FAILED, "标签取消关联失败: " + result.getMsg());
        }
        
        // 4. 记录日志
        log.info("服装标签取消关联成功，用户ID: {}, 服装ID: {}, 标签ID: {}", userId, clothingId, tagId);
    }

    /**
     * 检查位置是否发生变化
     *
     * @param oldLocationId 旧位置ID
     * @param newLocationId 新位置ID
     * @return 如果位置发生变化返回true，否则返回false
     */
    private boolean checkLocationChanged(String oldLocationId, String newLocationId) {
        // 如果旧位置ID和新位置ID都为null，表示没有变化
        if (oldLocationId == null && newLocationId == null) {
            return false;
        }
        
        // 如果旧位置ID为null，新位置ID不为null，表示从无位置变为有位置
        if (oldLocationId == null) {
            return true;
        }
        
        // 如果旧位置ID不为null，新位置ID为null，表示从有位置变为无位置
        if (newLocationId == null) {
            return true;
        }
        
        // 如果都不为null，比较是否相等
        return !oldLocationId.equals(newLocationId);
    }
}
