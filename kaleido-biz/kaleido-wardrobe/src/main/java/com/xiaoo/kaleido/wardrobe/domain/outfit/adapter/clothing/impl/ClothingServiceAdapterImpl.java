package com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.clothing.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IClothingDomainService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.clothing.IClothingServiceAdapter;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服装服务适配器实现类（防腐层）
 * <p>
 * 实现服装服务适配器接口，解耦穿搭领域对服装领域的直接依赖
 * 通过适配器模式将外部领域模型转换为当前领域需要的模型
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClothingServiceAdapterImpl implements IClothingServiceAdapter {

    private final IClothingDomainService clothingDomainService;

    @Override
    public boolean validateClothingBelongsToUser(String clothingId, String userId) {
        // 1. 参数校验
        if (StrUtil.isBlank(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "服装ID不能为空");
        }
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }

        try {
            // 2. 查找服装
            ClothingAggregate clothing = clothingDomainService.findByIdOrThrow(clothingId);
            
            // 3. 验证服装是否属于指定用户
            boolean belongsToUser = userId.equals(clothing.getUserId());
            
            // 4. 记录日志
            if (!belongsToUser) {
                log.warn("服装不属于指定用户，服装ID: {}, 服装所属用户: {}, 指定用户: {}", 
                        clothingId, clothing.getUserId(), userId);
            }
            
            return belongsToUser;
        } catch (WardrobeException e) {
            // 重新抛出领域异常
            throw e;
        } catch (Exception e) {
            // 捕获其他异常并转换为领域异常
            log.error("验证服装所属用户时发生异常，服装ID: {}, 用户ID: {}", clothingId, userId, e);
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_NOT_BELONG_TO_USER, "服装不属于该用户");
        }
    }

    @Override
    public void validateClothingsBelongToUser(List<String> clothingIds, String userId) {
        // 1. 参数校验
        if (clothingIds == null || clothingIds.isEmpty()) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "服装列表不能为空");
        }
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }

        // 2. 验证每个服装是否属于指定用户
        for (String clothingId : clothingIds) {
            if (!validateClothingBelongsToUser(clothingId, userId)) {
                throw WardrobeException.of(WardrobeErrorCode.DATA_NOT_BELONG_TO_USER,
                    "服装ID " + clothingId + " 不属于用户 " + userId);
            }
        }

        // 3. 记录日志
        log.info("服装列表验证通过，用户ID: {}, 服装数量: {}", userId, clothingIds.size());
    }
}
