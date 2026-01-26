package com.xiaoo.kaleido.wardrobe.application.command;

import com.xiaoo.kaleido.api.coin.IRpcCoinService;
import com.xiaoo.kaleido.api.coin.command.ProcessOutfitCreationCommand;
import com.xiaoo.kaleido.api.wardrobe.command.CreateOutfitWithClothingsCommand;
import com.xiaoo.kaleido.api.wardrobe.command.OutfitImageInfoCommand;
import com.xiaoo.kaleido.api.wardrobe.command.RecordOutfitWearCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateOutfitCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.file.IOutfitFileService;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository.IOutfitRepository;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.IOutfitDomainService;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.dto.OutfitImageInfoDTO;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 穿搭命令服务
 * <p>
 * 负责编排穿搭相关的命令操作，包括创建、更新、记录穿着等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutfitCommandService {

    private final IOutfitDomainService outfitDomainService;
    private final IOutfitRepository outfitRepository;
    private final IOutfitFileService outfitFileService;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcCoinService rpcCoinService;

    /**
     * 创建穿搭（包含服装和图片）
     *
     * @param command 创建穿搭命令
     * @return 创建的穿搭ID
     */
    @Transactional(rollbackFor = Exception.class)
    public String createOutfitWithClothingsAndImages(String userId,CreateOutfitWithClothingsCommand command) {
        // 1.准备图片信息
        List<OutfitImageInfoCommand> imageInfos = command.getImages();

        // 2.使用图片处理服务转换图片信息
        List<OutfitImageInfoDTO> domainImageInfos = outfitFileService.convertorImageInfo(imageInfos);

        // 3.调用领域服务创建穿搭
        OutfitAggregate outfit = outfitDomainService.createOutfitWithClothingsAndImages(
                userId,
                command.getName(),
                command.getDescription(),
                command.getClothingIds(),
                domainImageInfos
        );

        // 4.保存穿搭
        outfitRepository.save(outfit);

        // 5.记录日志
        log.info("穿搭创建成功，穿搭ID: {}, 用户ID: {}, 穿搭名称: {}, 服装数量: {}, 图片数量: {}",
                outfit.getId(), userId, command.getName(),
                command.getClothingIds().size(), command.getImages().size());

        // 6.调用金币服务扣减金币
        deductCoinsForOutfitCreation(userId, outfit.getId());

        return outfit.getId();
    }

    /**
     * 更新穿搭信息（包含服装和图片）
     *
     * @param command 更新穿搭命令
     */
    public void updateOutfit(String userId,UpdateOutfitCommand command) {
        // 1.准备图片信息
        List<OutfitImageInfoCommand> imageInfos = command.getImages();

        // 2.使用图片处理服务转换图片信息
        List<OutfitImageInfoDTO> domainImageInfos = outfitFileService.convertorImageInfo(imageInfos);

        // 3.调用领域服务更新穿搭
        OutfitAggregate outfit = outfitDomainService.updateOutfit(
                command.getOutfitId(),
                userId,
                command.getName(),
                command.getDescription(),
                command.getClothingIds(),
                domainImageInfos
        );

        // 4.保存穿搭
        outfitRepository.save(outfit);

        // 5.记录日志
        log.info("穿搭更新成功，穿搭ID: {}, 用户ID: {}, 新名称: {}, 服装数量: {}, 图片数量: {}",
                command.getOutfitId(), userId, command.getName(),
                command.getClothingIds().size(), command.getImages().size());
    }

    /**
     * 删除穿搭
     *
     * @param outfitId 穿搭ID
     * @param userId   用户ID
     */
    public void deleteOutfit(String outfitId, String userId) {
        // 1.调用领域服务删除穿搭（逻辑删除，将状态设置为DISABLE）
        OutfitAggregate outfit = outfitDomainService.deleteOutfit(outfitId, userId);

        // 2.更新穿搭状态（逻辑删除）
        outfitRepository.save(outfit);

        // 3.记录日志
        log.info("穿搭删除成功，穿搭ID: {}, 用户ID: {}", outfitId, userId);
    }

    /**
     * 记录穿搭穿着
     *
     * @param command 记录穿着命令
     */
    public void recordOutfitWear(String userId,RecordOutfitWearCommand command) {
        // 1.调用领域服务记录穿着
        OutfitAggregate outfit = outfitDomainService.recordOutfitWear(
                command.getOutfitId(),
                userId,
                command.getNotes()
        );

        // 2.保存穿搭（更新穿着次数和最后穿着日期）
        outfitRepository.save(outfit);

        // 3.记录日志
        log.info("穿搭穿着记录成功，穿搭ID: {}, 用户ID: {}, 穿着日期: {}",
                command.getOutfitId(), userId, command.getWearDate());
    }

    /**
     * 为穿搭创建扣减金币
     *
     * @param userId  用户ID
     * @param outfitId 穿搭ID
     */
    private void deductCoinsForOutfitCreation(String userId, String outfitId) {
        try {
            ProcessOutfitCreationCommand command = ProcessOutfitCreationCommand.builder()
                    .userId(userId)
                    .outfitId(outfitId)
                    .build();
            
            Result<Void> result = rpcCoinService.processOutfitCreation(userId, command);
            
            if (!Boolean.TRUE.equals(result.getSuccess())) {
                log.error("穿搭创建金币扣减失败，穿搭ID: {}, 用户ID: {}, 错误: {}", 
                        outfitId, userId, result.getMsg());
                throw WardrobeException.of(WardrobeErrorCode.COIN_DEDUCTION_FAILED,
                        "金币扣减失败: " + result.getMsg());
            } else {
                log.info("穿搭创建金币扣减成功，穿搭ID: {}, 用户ID: {}", outfitId, userId);
            }
        } catch (Exception e) {
            log.error("调用金币服务异常，穿搭ID: {}, 用户ID: {}", outfitId, userId, e);
            throw WardrobeException.of(WardrobeErrorCode.COIN_SERVICE_UNAVAILABLE, 
                    "金币服务不可用: " + e.getMessage());
        }
    }
}
