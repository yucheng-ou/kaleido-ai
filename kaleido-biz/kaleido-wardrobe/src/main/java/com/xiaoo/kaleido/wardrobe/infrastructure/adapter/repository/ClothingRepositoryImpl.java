package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.wardrobe.domain.clothing.adapter.repository.IClothingRepository;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.aggregate.ClothingAggregate;
import com.xiaoo.kaleido.wardrobe.domain.clothing.model.entity.ClothingImage;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor.ClothingImageInfraConvertor;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor.ClothingInfraConvertor;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.ClothingDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.ClothingImageDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.ClothingImagePO;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.ClothingPO;
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
 * 服装仓储实现（基础设施层）
 * <p>
 * 服装仓储接口的具体实现，负责服装聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/1/16
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ClothingRepositoryImpl implements IClothingRepository {

    private final ClothingDao clothingDao;
    private final ClothingImageDao clothingImageDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ClothingAggregate clothingAggregate) {
        try {
            // 1.转换ClothingAggregate为ClothingPO
            ClothingPO clothingPO = ClothingInfraConvertor.INSTANCE.toPO(clothingAggregate);

            // 2.保存服装基本信息
            clothingDao.insert(clothingPO);

            // 3.保存图片列表
            saveImages(clothingAggregate);

            log.info("服装保存成功，服装ID: {}, 用户ID: {}, 服装名称: {}, 图片数量: {}",
                    clothingAggregate.getId(), clothingAggregate.getUserId(),
                    clothingAggregate.getName(), clothingAggregate.getImages().size());
        } catch (Exception e) {
            log.error("服装保存失败，服装ID: {}, 原因: {}", clothingAggregate.getId(), e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_SAVE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ClothingAggregate clothingAggregate) {
        try {
            // 1.转换ClothingAggregate为ClothingPO
            ClothingPO clothingPO = ClothingInfraConvertor.INSTANCE.toPO(clothingAggregate);

            // 2.更新服装基本信息
            clothingDao.updateById(clothingPO);

            // 3.删除原有图片
            clothingImageDao.deleteByClothingId(clothingAggregate.getId());

            // 4.保存新图片列表
            saveImages(clothingAggregate);

            log.info("服装更新成功，服装ID: {}, 用户ID: {}, 服装名称: {}, 图片数量: {}",
                    clothingAggregate.getId(), clothingAggregate.getUserId(),
                    clothingAggregate.getName(), clothingAggregate.getImages().size());
        } catch (Exception e) {
            log.error("服装更新失败，服装ID: {}, 原因: {}", clothingAggregate.getId(), e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_UPDATE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String clothingId) {
        try {
            // 1.删除图片
            clothingImageDao.deleteByClothingId(clothingId);

            // 2.删除服装
            clothingDao.deleteById(clothingId);

            log.info("服装删除成功，服装ID: {}", clothingId);
        } catch (Exception e) {
            log.error("服装删除失败，服装ID: {}, 原因: {}", clothingId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_DELETE_FAIL);
        }
    }

    @Override
    public Optional<ClothingAggregate> findById(String clothingId) {
        try {
            // 1.查询服装基本信息
            ClothingPO clothingPO = clothingDao.findById(clothingId);
            if (clothingPO == null) {
                return Optional.empty();
            }

            // 2.转换为ClothingAggregate
            ClothingAggregate clothingAggregate = ClothingInfraConvertor.INSTANCE.toAggregate(clothingPO);

            // 3.查询并设置图片列表
            List<ClothingImagePO> imagePOs = clothingImageDao.findByClothingId(clothingId);
            List<ClothingImage> images = ClothingImageInfraConvertor.INSTANCE.toEntityList(imagePOs);
            clothingAggregate.addImages(images);

            return Optional.of(clothingAggregate);
        } catch (Exception e) {
            log.error("查询服装失败，服装ID: {}, 原因: {}", clothingId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_QUERY_FAIL);
        }
    }

    @Override
    public ClothingAggregate findByIdOrThrow(String clothingId) {
        return findById(clothingId)
                .orElseThrow(() -> WardrobeException.of(BizErrorCode.DATA_NOT_EXIST));
    }

    @Override
    public boolean existsByUserIdAndName(String userId, String name) {
        try {
            return clothingDao.existsByUserIdAndName(userId, name);
        } catch (Exception e) {
            log.error("检查服装名称唯一性失败，用户ID: {}, 服装名称: {}, 原因: {}",
                    userId, name, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_QUERY_FAIL);
        }
    }

    @Override
    public List<ClothingAggregate> findByUserId(String userId) {
        try {
            // 1.查询服装基本信息列表
            List<ClothingPO> clothingPOs = clothingDao.findByUserId(userId);

            // 2.转换为ClothingAggregate列表（不加载图片，按需加载）
            return clothingPOs.stream()
                    .map(ClothingInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户服装列表失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_QUERY_FAIL);
        }
    }

    @Override
    public List<ClothingAggregate> findByUserIdAndTypeCode(String userId, String typeCode) {
        try {
            // 1.查询服装基本信息列表
            List<ClothingPO> clothingPOs = clothingDao.findByUserIdAndTypeCode(userId, typeCode);

            // 2.转换为ClothingAggregate列表（不加载图片，按需加载）
            return clothingPOs.stream()
                    .map(ClothingInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户类型服装列表失败，用户ID: {}, 类型编码: {}, 原因: {}",
                    userId, typeCode, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_QUERY_FAIL);
        }
    }

    @Override
    public List<ClothingAggregate> findByUserIdAndTypeCodeAndColorCode(String userId, String typeCode, String colorCode) {
        try {
            // 1.查询服装基本信息列表
            List<ClothingPO> clothingPOs = clothingDao.findByUserIdAndTypeCodeAndColorCode(userId, typeCode, colorCode);

            // 2.转换为ClothingAggregate列表（不加载图片，按需加载）
            return clothingPOs.stream()
                    .map(ClothingInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户类型颜色服装列表失败，用户ID: {}, 类型编码: {}, 颜色编码: {}, 原因: {}",
                    userId, typeCode, colorCode, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.CLOTHING_QUERY_FAIL);
        }
    }

    /**
     * 保存服装图片列表
     *
     * @param clothingAggregate 服装聚合根，包含图片列表
     */
    private void saveImages(ClothingAggregate clothingAggregate) {
        List<ClothingImage> images = clothingAggregate.getImages();
        if (images == null || images.isEmpty()) {
            return;
        }

        // 转换ClothingImage为ClothingImagePO
        List<ClothingImagePO> imagePOs = ClothingImageInfraConvertor.INSTANCE.toPOList(images);

        // 设置服装ID
        String clothingId = clothingAggregate.getId();
        imagePOs.forEach(po -> po.setClothingId(clothingId));

        // 批量插入
        if (!imagePOs.isEmpty()) {
            clothingImageDao.batchInsert(imagePOs);
        }
    }
}
