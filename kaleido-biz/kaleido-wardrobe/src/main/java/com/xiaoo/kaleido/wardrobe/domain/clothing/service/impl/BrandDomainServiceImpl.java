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
    public BrandAggregate createBrand(String name, String logoPath, String description, String country) {
        // 1.参数校验
        validateCreateBrandParams(name);

        // 2.检查品牌名称是否唯一
        if (!isBrandNameUnique(name)) {
            throw WardrobeException.of(WardrobeErrorCode.BRAND_NAME_EXISTS);
        }

        // 3.创建品牌聚合根
        BrandAggregate brand = BrandAggregate.create(name, logoPath, description, country);

        // 4.保存品牌
        brandRepository.save(brand);

        // 5.记录日志
        log.info("品牌创建成功，品牌ID: {}, 品牌名称: {}", brand.getId(), brand.getName());

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
    public BrandAggregate updateBrand(String brandId, String logoPath, String description, String country) {
        // 1.查找品牌
        BrandAggregate brand = findByIdOrThrow(brandId);

        // 2.验证品牌状态
        if (!brand.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.BRAND_DISABLED);
        }

        // 3.更新品牌信息
        brand.updateInfo(logoPath, description, country);

        // 4.保存更新
        brandRepository.update(brand);

        // 5.记录日志
        log.info("品牌信息更新成功，品牌ID: {}", brandId);

        return brand;
    }

    @Override
    public BrandAggregate enableBrand(String brandId) {
        // 1.查找品牌
        BrandAggregate brand = findByIdOrThrow(brandId);

        // 2.验证品牌状态
        if (brand.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.BRAND_ALREADY_ENABLED);
        }

        // 3.启用品牌
        brand.enable();

        // 4.保存更新
        brandRepository.update(brand);

        // 5.记录日志
        log.info("品牌启用成功，品牌ID: {}", brandId);

        return brand;
    }

    @Override
    public BrandAggregate disableBrand(String brandId) {
        // 1.查找品牌
        BrandAggregate brand = findByIdOrThrow(brandId);

        // 2.验证品牌状态
        if (!brand.isEnabled()) {
            throw WardrobeException.of(WardrobeErrorCode.BRAND_ALREADY_DISABLED);
        }

        // 3.禁用品牌
        brand.disable();

        // 4.保存更新
        brandRepository.update(brand);

        // 5.记录日志
        log.info("品牌禁用成功，品牌ID: {}", brandId);

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

    /**
     * 验证创建品牌的参数
     *
     * @param name 品牌名称
     */
    private void validateCreateBrandParams(String name) {
        if (StrUtil.isBlank(name)) {
            throw WardrobeException.of(WardrobeErrorCode.BRAND_NAME_EMPTY);
        }

        if (name.trim().length() > 100) {
            throw WardrobeException.of(WardrobeErrorCode.BRAND_NAME_LENGTH_ERROR);
        }
    }
}
