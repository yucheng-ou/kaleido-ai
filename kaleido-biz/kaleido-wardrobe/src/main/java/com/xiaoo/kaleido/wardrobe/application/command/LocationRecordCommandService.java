package com.xiaoo.kaleido.wardrobe.application.command;

import com.xiaoo.kaleido.api.wardrobe.command.AddClothingToLocationCommand;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IClothingRepository;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRecordRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.LocationRecordAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.service.ILocationRecordDomainService;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 位置记录命令服务
 * <p>
 * 负责编排位置记录相关的命令操作，包括将衣服添加到位置等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationRecordCommandService {

    private final ILocationRecordDomainService locationRecordDomainService;
    private final ILocationRecordRepository locationRecordRepository;
    private final IClothingRepository clothingRepository;

    /**
     * 将衣服添加到位置
     *
     * @param command 将衣服添加到位置命令
     */
    @Transactional(rollbackFor = Exception.class)
    public void addClothingToLocation(AddClothingToLocationCommand command) {
        // 1. 调用领域服务创建位置记录
        LocationRecordAggregate locationRecord = locationRecordDomainService.createLocationRecord(
                command.getClothingId(),
                command.getLocationId(),
                command.getUserId(),
                command.getNotes()
        );

        // 2. 保存位置记录
        locationRecordRepository.save(locationRecord);

        // 3. 更新服装的当前位置ID
        ClothingAggregate clothing = clothingRepository.findByIdOrThrow(command.getClothingId());
        clothing.changeLocation(command.getLocationId());
        clothingRepository.update(clothing);

        // 4. 记录日志
        log.info("衣服添加到位置成功，服装ID: {}, 位置ID: {}, 用户ID: {}, 位置记录ID: {}",
                command.getClothingId(), command.getLocationId(),
                command.getUserId(), locationRecord.getId());
    }
}
