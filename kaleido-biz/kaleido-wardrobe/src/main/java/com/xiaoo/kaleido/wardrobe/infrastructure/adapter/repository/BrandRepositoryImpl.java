package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IBrandRepository;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.BrandAggregate;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor.BrandInfraConvertor;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.BrandDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.BrandPO;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 品牌仓储实现（基础设施层）
 * <p>
 * 品牌仓储接口的具体实现，负责品牌聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class BrandRepositoryImpl implements IBrandRepository {

    private final BrandDao brandDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(BrandAggregate brandAggregate) {
        try {
            // 1.转换BrandAggregate为BrandPO
            BrandPO brandPO = BrandInfraConvertor.INSTANCE.toPO(brandAggregate);

            // 2.保存品牌基本信息
            brandDao.insert(brandPO);

            log.info("品牌保存成功，品牌ID: {}, 品牌名称: {}",
                    brandAggregate.getId(), brandAggregate.getName());
        } catch (Exception e) {
            log.error("品牌保存失败，品牌ID: {}, 原因: {}", brandAggregate.getId(), e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.BRAND_SAVE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BrandAggregate brandAggregate) {
        try {
            // 1.转换BrandAggregate为BrandPO
            BrandPO brandPO = BrandInfraConvertor.INSTANCE.toPO(brandAggregate);

            // 2.更新品牌基本信息
            brandDao.updateById(brandPO);

            log.info("品牌更新成功，品牌ID: {}, 品牌名称: {}",
                    brandAggregate.getId(), brandAggregate.getName());
        } catch (Exception e) {
            log.error("品牌更新失败，品牌ID: {}, 原因: {}", brandAggregate.getId(), e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.BRAND_UPDATE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String brandId) {
        try {
            // 删除品牌
            brandDao.deleteById(brandId);

            log.info("品牌删除成功，品牌ID: {}", brandId);
        } catch (Exception e) {
            log.error("品牌删除失败，品牌ID: {}, 原因: {}", brandId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.BRAND_DELETE_FAIL);
        }
    }

    @Override
    public Optional<BrandAggregate> findById(String brandId) {
        try {
            // 1.查询品牌基本信息
            BrandPO brandPO = brandDao.findById(brandId);
            if (brandPO == null) {
                return Optional.empty();
            }

            // 2.转换为BrandAggregate
            BrandAggregate brandAggregate = BrandInfraConvertor.INSTANCE.toAggregate(brandPO);

            return Optional.of(brandAggregate);
        } catch (Exception e) {
            log.error("查询品牌失败，品牌ID: {}, 原因: {}", brandId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.BRAND_QUERY_FAIL);
        }
    }

    @Override
    public BrandAggregate findByIdOrThrow(String brandId) {
        return findById(brandId)
                .orElseThrow(() -> WardrobeException.of(BizErrorCode.DATA_NOT_EXIST));
    }

    @Override
    public boolean existsByName(String name) {
        try {
            return brandDao.existsByName(name);
        } catch (Exception e) {
            log.error("检查品牌名称唯一性失败，品牌名称: {}, 原因: {}", name, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.BRAND_QUERY_FAIL);
        }
    }

    @Override
    public List<BrandAggregate> findAll() {
        try {
            // 1.查询所有品牌基本信息
            List<BrandPO> brandPOs = brandDao.findAll();

            // 2.转换为BrandAggregate列表
            return brandPOs.stream()
                    .map(BrandInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询所有品牌失败，原因: {}", e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.BRAND_QUERY_FAIL);
        }
    }
}
