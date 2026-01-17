package com.xiaoo.kaleido.wardrobe.application.query.impl;

import com.xiaoo.kaleido.api.wardrobe.response.ClothingInfoResponse;
import com.xiaoo.kaleido.wardrobe.application.convertor.WardrobeConvertor;
import com.xiaoo.kaleido.wardrobe.application.query.IClothingQueryService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IClothingDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服装查询服务实现类
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClothingQueryServiceImpl implements IClothingQueryService {

    private final IClothingDomainService clothingDomainService;
    private final WardrobeConvertor wardrobeConvertor;

    @Override
    public List<ClothingInfoResponse> findByUserId(String userId) {
        log.info("查询用户服装列表，用户ID: {}", userId);
        
        // 调用领域服务查询服装列表
        List<ClothingAggregate> clothingList = clothingDomainService.findByUserId(userId);
        
        // 使用MapStruct转换器转换为响应对象
        return clothingList.stream()
                .map(wardrobeConvertor::toClothingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClothingInfoResponse findById(String clothingId) {
        log.info("查询服装详情，服装ID: {}", clothingId);
        
        // 调用领域服务查询服装
        ClothingAggregate clothing = clothingDomainService.findByIdOrThrow(clothingId);
        
        // 使用MapStruct转换器转换为响应对象
        return wardrobeConvertor.toClothingResponse(clothing);
    }
}
