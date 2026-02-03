package com.xiaoo.kaleido.wardrobe.application.command;

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
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IBrandDomainService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IClothingDomainService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.event.IClothingEventPublisher;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.dto.ClothingImageInfoDTO;
import com.xiaoo.kaleido.api.wardrobe.enums.ClothingEventTypeEnums;
import com.xiaoo.kaleido.wardrobe.types.constant.EntityTypeConstants;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

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
    private final IBrandDomainService brandDomainService;
    private final IClothingRepository clothingRepository;
    private final IClothingFileService clothingFileService;
    private final IClothingEventPublisher clothingEventPublisher;
    private final LocationCommandService locationCommandService;
    private final TransactionTemplate transactionTemplate;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcTagService rpcTagService;

    /**
     * 创建服装（包含图片）
     *
     * @param command 创建服装命令
     * @return 创建的服装ID
     */
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

        // 开启编程式事务
        String brandName = transactionTemplate.execute(status -> {
            // 4.保存服装
            clothingRepository.save(clothing);

            // 5.如果提供了位置ID，创建位置记录
            locationCommandService.createLocationRecord(clothing.getId(), command.getCurrentLocationId(), userId);
            
            // 6.查询品牌名称
            return brandDomainService.getBrandName(command.getBrandId());
        });
        
        // 7.发布服装创建事件
        clothingEventPublisher.publishClothingEvent(
                ClothingEventTypeEnums.CREATE,
                userId,
                clothing.getId(),
                command.getName(),
                command.getTypeCode(),
                command.getColorCode(),
                command.getSeasonCode(),
                brandName,
                command.getSize(),
                command.getPurchaseDate(),
                command.getPrice(),
                command.getDescription(),
                command.getCurrentLocationId()
        );

        // 8.记录日志
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

        // 6.处理位置变更
        locationCommandService.handleLocationChange(command.getClothingId(), oldLocationId, newLocationId, userId);

        // 7.查询品牌名称
        String brandName = brandDomainService.getBrandName(command.getBrandId());
        
        // 8.确定用于事件的位置ID：如果提供了新的位置ID则使用新的，否则使用旧的
        String locationIdForEvent = newLocationId != null ? newLocationId : oldLocationId;
        
        // 9.发布服装更新事件
        clothingEventPublisher.publishClothingEvent(
                ClothingEventTypeEnums.UPDATE,
                userId,
                command.getClothingId(),
                command.getName(),
                command.getTypeCode(),
                command.getColorCode(),
                command.getSeasonCode(),
                brandName,
                command.getSize(),
                command.getPurchaseDate(),
                command.getPrice(),
                command.getDescription(),
                locationIdForEvent
        );

        // 10.记录日志
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

        // 3.发布服装删除事件
        clothingEventPublisher.publishSimpleClothingEvent(ClothingEventTypeEnums.DELETE, userId, clothingId);

        // 4.记录日志
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
        validateClothingOwnership(clothing, userId);
        
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
        validateClothingOwnership(clothing, userId);
        
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
     * 验证服装所有权
     *
     * @param clothing 服装聚合根
     * @param userId   用户ID
     */
    private void validateClothingOwnership(ClothingAggregate clothing, String userId) {
        if (!clothing.getUserId().equals(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_OWNER_MISMATCH, "只有服装所有者可以操作");
        }
    }

}
