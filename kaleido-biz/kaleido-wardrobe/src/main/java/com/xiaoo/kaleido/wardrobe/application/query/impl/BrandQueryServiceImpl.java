package com.xiaoo.kaleido.wardrobe.application.query.impl;

import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;
import com.xiaoo.kaleido.wardrobe.application.convertor.WardrobeConvertor;
import com.xiaoo.kaleido.wardrobe.application.query.IBrandQueryService;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IBrandRepository;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IBrandDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 品牌查询服务实现类
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandQueryServiceImpl implements IBrandQueryService {

    private final IBrandDomainService brandDomainService;
    private final IBrandRepository brandRepository;
    private final WardrobeConvertor wardrobeConvertor;

    @Override
    public List<BrandInfoResponse> findAll() {
        log.info("查询所有品牌列表");
        
        // 查询所有品牌
        List<BrandAggregate> brandList = brandRepository.findAll();
        
        // 使用MapStruct转换器转换为响应对象
        return brandList.stream()
                .map(wardrobeConvertor::toBrandResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BrandInfoResponse findById(String brandId) {
        log.info("查询品牌详情，品牌ID: {}", brandId);
        
        // 调用领域服务查询品牌
        BrandAggregate brand = brandDomainService.findByIdOrThrow(brandId);
        
        // 使用MapStruct转换器转换为响应对象
        return wardrobeConvertor.toBrandResponse(brand);
    }
}
