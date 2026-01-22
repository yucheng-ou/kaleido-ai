package com.xiaoo.kaleido.wardrobe.application.query.impl;

import com.xiaoo.kaleido.api.wardrobe.response.OutfitInfoResponse;
import com.xiaoo.kaleido.api.wardrobe.response.WearRecordResponse;
import com.xiaoo.kaleido.wardrobe.application.convertor.OutfitConvertor;
import com.xiaoo.kaleido.wardrobe.application.query.IOutfitQueryService;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.service.IOutfitDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 穿搭查询服务实现类
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutfitQueryServiceImpl implements IOutfitQueryService {

    private final IOutfitDomainService outfitDomainService;
    private final OutfitConvertor outfitConvertor;

    @Override
    public List<OutfitInfoResponse> findByUserId(String userId) {
        log.info("查询用户穿搭列表，用户ID: {}", userId);
        
        // 调用领域服务查询穿搭列表
        List<OutfitAggregate> outfitList = outfitDomainService.findOutfitsByUserId(userId);
        
        // 使用MapStruct转换器转换为响应对象
        return outfitList.stream()
                .map(outfitConvertor::toOutfitResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OutfitInfoResponse findById(String outfitId, String userId) {
        log.info("查询穿搭详情，穿搭ID: {}, 用户ID: {}", outfitId, userId);
        
        // 调用领域服务查询穿搭并进行用户权限校验
        OutfitAggregate outfit = outfitDomainService.findByIdAndUserIdOrThrow(outfitId, userId);
        
        // 使用MapStruct转换器转换为响应对象
        return outfitConvertor.toOutfitResponse(outfit);
    }

    @Override
    public List<WearRecordResponse> findWearRecordsByOutfitId(String outfitId, String userId) {
        log.info("查询穿搭穿着记录，穿搭ID: {}, 用户ID: {}", outfitId, userId);
        
        // 调用领域服务查询穿搭并进行用户权限校验
        OutfitAggregate outfit = outfitDomainService.findByIdAndUserIdOrThrow(outfitId, userId);
        
        // 使用MapStruct转换器转换穿着记录
        return outfit.getWearRecords().stream()
                .map(outfitConvertor::toWearRecordResponse)
                .collect(Collectors.toList());
    }
}
