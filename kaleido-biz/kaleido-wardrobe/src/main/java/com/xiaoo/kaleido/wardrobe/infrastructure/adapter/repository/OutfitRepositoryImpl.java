package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository.IOutfitRepository;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitClothing;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.WearRecord;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor.OutfitInfraConvertor;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.OutfitClothingDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.OutfitDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.OutfitImageDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitClothingPO;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitPO;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitImagePO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 穿搭仓储实现
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class OutfitRepositoryImpl implements IOutfitRepository {

    private final OutfitDao outfitDao;
    private final OutfitImageDao outfitImageDao;
    private final OutfitClothingDao outfitClothingDao;
    private final OutfitInfraConvertor convertor = OutfitInfraConvertor.INSTANCE;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutfitAggregate save(OutfitAggregate outfitAggregate) {
        // 保存穿搭基本信息
        OutfitPO outfitPO = convertor.toPO(outfitAggregate);

        outfitDao.insert(outfitPO);

        // 保存穿搭图片
        saveOutfitImages(outfitAggregate);

        // 保存穿搭服装关联
        saveOutfitClothings(outfitAggregate);

        return outfitAggregate;
    }

    @Override
    public OutfitAggregate findById(String id) {
        log.debug("根据ID查找穿搭聚合根: outfitId={}", id);

        OutfitPO outfitPO = outfitDao.findById(id);
        if (outfitPO == null) {
            throw new com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException(
                    com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode.OUTFIT_NOT_FOUND
            );
        }

        OutfitAggregate aggregate = convertor.toAggregate(outfitPO);

        // 加载关联的图片
        List<OutfitImagePO> imagePOs = outfitImageDao.findByOutfitId(id);
        List<OutfitImage> images = convertor.toImageEntities(imagePOs);
        aggregate.setImages(images);

        // 加载关联的服装
        List<OutfitClothingPO> clothingPOs = outfitClothingDao.findByOutfitIds(Collections.singletonList(id));
        List<OutfitClothing> clothings = convertor.toClothingEntities(clothingPOs);
        aggregate.setClothings(clothings);

        return aggregate;
    }

    @Override
    public OutfitAggregate findByIdIncludeDeleted(String id) {

        OutfitPO outfitPO = outfitDao.findByIdIncludeDeleted(id);
        if (outfitPO == null) {
            return null;
        }

        OutfitAggregate aggregate = convertor.toAggregate(outfitPO);

        // 加载关联的图片（需要实现包含已删除的查询）
        List<OutfitImagePO> imagePOs = outfitImageDao.findByOutfitId(id);
        List<OutfitImage> images = convertor.toImageEntities(imagePOs);
        aggregate.setImages(images);

        return aggregate;
    }

    @Override
    public List<OutfitAggregate> findByUserId(String userId) {
        List<OutfitPO> outfitPOs = outfitDao.findByUserId(userId);
        if (outfitPOs.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> outfitIds = outfitPOs.stream()
                .map(OutfitPO::getId)
                .collect(Collectors.toList());

        // 批量加载图片
        List<OutfitImagePO> allImagePOs = outfitImageDao.findByOutfitIds(outfitIds);

        // 批量加载服装关联
        List<OutfitClothingPO> allClothingPOs = outfitClothingDao.findByOutfitIds(outfitIds);

        return outfitPOs.stream().map(outfitPO -> {
            OutfitAggregate aggregate = convertor.toAggregate(outfitPO);

            // 过滤当前穿搭的图片
            List<OutfitImagePO> imagePOs = allImagePOs.stream()
                    .filter(po -> po.getOutfitId().equals(outfitPO.getId()))
                    .collect(Collectors.toList());
            List<OutfitImage> images = convertor.toImageEntities(imagePOs);
            aggregate.setImages(images);

            // 过滤当前穿搭的服装关联
            List<OutfitClothingPO> clothingPOs = allClothingPOs.stream()
                    .filter(po -> po.getOutfitId().equals(outfitPO.getId()))
                    .collect(Collectors.toList());
            List<OutfitClothing> clothings = convertor.toClothingEntities(clothingPOs);
            aggregate.setClothings(clothings);

            return aggregate;
        }).collect(Collectors.toList());
    }

    @Override
    public OutfitAggregate findByUserIdAndName(String userId, String name) {
        OutfitPO outfitPO = outfitDao.findByUserIdAndName(userId, name);
        if (outfitPO == null) {
            throw new com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException(
                    com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode.OUTFIT_NOT_FOUND
            );
        }

        OutfitAggregate aggregate = convertor.toAggregate(outfitPO);

        // 加载关联的图片
        List<OutfitImagePO> imagePOs = outfitImageDao.findByOutfitId(outfitPO.getId());
        List<OutfitImage> images = convertor.toImageEntities(imagePOs);
        aggregate.setImages(images);

        // 加载关联的服装
        List<OutfitClothingPO> clothingPOs = outfitClothingDao.findByOutfitIds(Collections.singletonList(outfitPO.getId()));
        List<OutfitClothing> clothings = convertor.toClothingEntities(clothingPOs);
        aggregate.setClothings(clothings);

        return aggregate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        // 逻辑删除穿搭
        OutfitPO outfitPO = outfitDao.findById(id);
        if (outfitPO != null) {
            // 设置删除标记（假设BasePO中有逻辑删除字段）
            outfitDao.deleteById(id);
        }

        // 删除关联的图片
        outfitImageDao.deleteByOutfitId(id);

        // 删除关联的服装
        outfitClothingDao.deleteByOutfitId(id);
    }

    @Override
    public boolean existsById(String id) {
        OutfitPO outfitPO = outfitDao.findById(id);
        return outfitPO != null;
    }

    @Override
    public boolean existsByUserIdAndName(String userId, String name) {
        return outfitDao.existsByUserIdAndName(userId, name);
    }

    /**
     * 保存穿搭图片
     *
     * @param aggregate 穿搭聚合根
     */
    private void saveOutfitImages(OutfitAggregate aggregate) {
        String outfitId = aggregate.getId();

        // 先删除旧的图片
        outfitImageDao.deleteByOutfitId(outfitId);

        // 保存新的图片
        List<OutfitImage> images = aggregate.getImages();
        if (images != null && !images.isEmpty()) {
            List<OutfitImagePO> imagePOs = convertor.toImagePOs(images);
            for (OutfitImagePO po : imagePOs) {
                outfitImageDao.insert(po);
            }
        }
    }

    /**
     * 保存穿搭服装关联
     *
     * @param aggregate 穿搭聚合根
     */
    private void saveOutfitClothings(OutfitAggregate aggregate) {
        String outfitId = aggregate.getId();

        // 先删除旧的关联
        outfitClothingDao.deleteByOutfitId(outfitId);

        // 保存新的关联
        List<OutfitClothing> clothings = aggregate.getClothings();
        if (clothings != null && !clothings.isEmpty()) {
            List<OutfitClothingPO> clothingPOs = convertor.toClothingPOs(clothings);
            // 设置关联的穿搭ID
            clothingPOs.forEach(po -> po.setOutfitId(outfitId));
            outfitClothingDao.batchInsert(clothingPOs);
        }
    }
}
