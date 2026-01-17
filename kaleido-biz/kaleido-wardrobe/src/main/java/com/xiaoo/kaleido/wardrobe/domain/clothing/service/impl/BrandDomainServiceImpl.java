package com.xiaoo.kaleido.wardrobe.domain.clothing.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IBrandRepository;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IBrandDomainService;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 品牌领域服务实现类
 * <p>
 * 实现品牌领域服务的所有业务逻辑，包括参数校验、业务规则验证、异常处理等
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandDomainServiceImpl implements IBrandDomainService {

    private final IBrandRepository brandRepository;

    @Override
    public BrandAggregate createBrand(String name, String logoPath, String description) {
        // 1.检查品牌名称是否唯一
        if (!isBrandNameUnique(name)) {
            throw WardrobeException.of(WardrobeErrorCode.BRAND_NAME_EXISTS);
        }

        // 2.创建品牌聚合根
        BrandAggregate brand = BrandAggregate.create(name, logoPath, description);

        // 3.记录日志
        log.info("品牌创建完成，品牌ID: {}, 品牌名称: {}", brand.getId(), brand.getName());

        return brand;
    }

    @Override
    public BrandAggregate findByIdOrThrow(String brandId) {
        // 1.参数校验
        if (StrUtil.isBlank(brandId)) {
            throw WardrobeException.of(WardrobeErrorCode.BRAND_ID_NOT_NULL);
        }

        // 2.查找品牌
        return brandRepository.findById(brandId)
                .orElseThrow(() -> WardrobeException.of(WardrobeErrorCode.BRAND_NOT_FOUND));
    }

    @Override
    public BrandAggregate updateBrand(String brandId, String logoPath, String description) {
        // 1.查找品牌（如果品牌不存在或已删除，findByIdOrThrow会抛出BRAND_NOT_FOUND异常）
        BrandAggregate brand = findByIdOrThrow(brandId);

        // 2.更新品牌信息
        brand.updateInfo(logoPath, description);

        // 3.记录日志
        log.info("品牌信息更新完成，品牌ID: {}", brandId);

        return brand;
    }

    @Override
    public boolean isBrandNameUnique(String name) {
        // 1.参数校验
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.BRAND_NAME_EMPTY);
        }

        // 2.检查品牌名称是否已存在
        return !brandRepository.existsByName(name.trim());
    }
}
