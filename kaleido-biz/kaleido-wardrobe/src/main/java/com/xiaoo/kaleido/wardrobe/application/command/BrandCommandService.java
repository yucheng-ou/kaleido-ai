package com.xiaoo.kaleido.wardrobe.application.command;

import com.xiaoo.kaleido.api.wardrobe.command.CreateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateBrandCommand;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IBrandRepository;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.service.IBrandDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 品牌命令服务
 * <p>
 * 负责编排品牌相关的命令操作，包括创建、更新、删除等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandCommandService {

    private final IBrandDomainService brandDomainService;
    private final IBrandRepository brandRepository;

    /**
     * 创建品牌
     *
     * @param command 创建品牌命令
     * @return 创建的品牌ID
     */
    public String createBrand(CreateBrandCommand command) {
        // 1.调用领域服务创建品牌
        BrandAggregate brand = brandDomainService.createBrand(
                command.getName(),
                command.getLogoPath(),
                command.getDescription()
        );

        // 2.保存品牌
        brandRepository.save(brand);

        // 3.记录日志
        log.info("品牌创建成功，品牌ID: {}, 品牌名称: {}", brand.getId(), brand.getName());

        return brand.getId();
    }

    /**
     * 更新品牌信息
     *
     * @param command 更新品牌命令
     */
    public void updateBrand(UpdateBrandCommand command) {
        // 1.调用领域服务更新品牌
        BrandAggregate brand = brandDomainService.updateBrand(
                command.getBrandId(),
                command.getLogoPath(),
                command.getDescription()
        );

        // 2.更新品牌
        brandRepository.update(brand);

        // 3.记录日志
        log.info("品牌更新成功，品牌ID: {}", command.getBrandId());
    }

    /**
     * 删除品牌（逻辑删除）
     *
     * @param brandId 品牌ID
     */
    public void deleteBrand(String brandId) {

        // 1.删除品牌
        brandRepository.delete(brandId);

        // 2.记录日志
        log.info("品牌删除成功，品牌ID: {}", brandId);
    }
}
