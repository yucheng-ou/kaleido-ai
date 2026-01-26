package com.xiaoo.kaleido.wardrobe.domain.location.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IClothingDomainService;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRecordRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.LocationRecordAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationDomainService;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationRecordDomainService;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 位置记录领域服务实现类
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationRecordDomainServiceImpl implements ILocationRecordDomainService {

    private final ILocationRecordRepository locationRecordRepository;
    private final IClothingDomainService clothingDomainService;
    private final ILocationDomainService locationDomainService;

    @Override
    public LocationRecordAggregate createLocationRecord(
            String clothingId,
            String locationId,
            String userId) {
        // 1. 参数校验
        validateCreateParams(clothingId, locationId, userId);

        // 2. 验证服装存在且属于用户
        ClothingAggregate clothing = clothingDomainService.findByIdOrThrow(clothingId);
        if (!userId.equals(clothing.getUserId())) {
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_OWNER_MISMATCH);
        }

        // 3. 验证位置存在且属于用户
        StorageLocationAggregate location = locationDomainService.findByIdOrThrow(locationId);
        if (!userId.equals(location.getUserId())) {
            throw WardrobeException.of(WardrobeErrorCode.DATA_NOT_BELONG_TO_USER);
        }

        // 4. 检查服装是否已在目标位置
        if (isClothingInLocation(clothingId, locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.OPERATE_FAILED, "服装已在目标位置");
        }

        // 5. 创建新的位置记录
        LocationRecordAggregate locationRecord = LocationRecordAggregate.create(
                clothingId, locationId, userId);

        // 6. 记录日志
        log.info("位置记录创建完成，记录ID: {}, 服装ID: {}, 位置ID: {}, 用户ID: {}",
                locationRecord.getId(), clothingId, locationId, userId);

        return locationRecord;
    }

    @Override
    public LocationRecordAggregate findCurrentByClothingId(String clothingId) {
        // 1. 参数校验
        if (StrUtil.isBlank(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "服装ID不能为空");
        }

        // 2. 查询当前位置记录
        return locationRecordRepository.findCurrentByClothingId(clothingId)
                .orElse(null);
    }

    @Override
    public List<LocationRecordAggregate> findCurrentByLocationId(String locationId) {
        // 1. 参数校验
        if (StrUtil.isBlank(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置ID不能为空");
        }

        // 2. 查询位置的所有当前位置记录
        return locationRecordRepository.findCurrentByLocationId(locationId);
    }

    @Override
    public List<LocationRecordAggregate> findCurrentByUserId(String userId) {
        // 1. 参数校验
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }

        // 2. 查询用户的所有当前位置记录
        return locationRecordRepository.findCurrentByUserId(userId);
    }

    @Override
    public List<LocationRecordAggregate> findByClothingId(String clothingId) {
        // 1. 参数校验
        if (StrUtil.isBlank(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "服装ID不能为空");
        }

        // 2. 查询服装的所有位置记录
        return locationRecordRepository.findByClothingId(clothingId);
    }

    @Override
    public boolean isClothingInLocation(String clothingId, String locationId) {
        // 1. 参数校验
        if (StrUtil.isBlank(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "服装ID不能为空");
        }
        if (StrUtil.isBlank(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置ID不能为空");
        }

        // 2. 检查服装是否在指定位置
        return locationRecordRepository.existsCurrentByClothingIdAndLocationId(clothingId, locationId);
    }

    @Override
    public int countCurrentByLocationId(String locationId) {
        // 1. 参数校验
        if (StrUtil.isBlank(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置ID不能为空");
        }

        // 2. 统计位置中的当前服装数量
        return locationRecordRepository.countCurrentByLocationId(locationId);
    }

    /**
     * 验证创建位置记录的参数
     *
     * @param clothingId 服装ID
     * @param locationId 位置ID
     * @param userId     用户ID
     */
    private void validateCreateParams(String clothingId, String locationId, String userId) {
        if (StrUtil.isBlank(clothingId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "服装ID不能为空");
        }
        if (StrUtil.isBlank(locationId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "位置ID不能为空");
        }
        if (StrUtil.isBlank(userId)) {
            throw WardrobeException.of(WardrobeErrorCode.PARAM_NOT_NULL, "用户ID不能为空");
        }
    }
}
