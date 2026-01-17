package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository;

import com.xiaoo.kaleido.wardrobe.domain.outfit.adapter.repository.IOutfitRepository;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.aggregate.OutfitAggregate;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitClothing;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.OutfitImage;
import com.xiaoo.kaleido.wardrobe.domain.outfit.model.entity.WearRecord;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor.OutfitInfraConvertor;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.OutfitDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.OutfitImageDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitPO;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.OutfitImagePO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 穿搭仓储实现
 * <p>
 * 实现IOutfitRepository接口，负责穿搭聚合根的持久化操作
 * 遵循DDD仓储模式，隔离领域层和基础设施层
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
    private final OutfitInfraConvertor convertor = OutfitInfraConvertor.INSTANCE;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutfitAggregate save(OutfitAggregate outfitAggregate) {
        log.debug("保存穿搭聚合根: outfitId={}", outfitAggregate.getId());

        // 保存穿搭基本信息
        OutfitPO outfitPO = convertor.toPO(outfitAggregate);
        if (outfitAggregate.getId() == null) {
            outfitDao.insert(outfitPO);
        } else {
            outfitDao.updateById(outfitPO);
        }

        // 保存穿搭图片
        saveOutfitImages(outfitAggregate);

        // TODO: 保存穿搭服装关联和穿着记录（需要创建相应的DAO）

        return findById(outfitAggregate.getId()).orElseThrow(
                () -> new RuntimeException("保存后查找穿搭失败: " + outfitAggregate.getId())
        );
    }

    @Override
    public Optional<OutfitAggregate> findById(String id) {
        log.debug("根据ID查找穿搭聚合根: outfitId={}", id);

        OutfitPO outfitPO = outfitDao.findById(id);
        if (outfitPO == null) {
            return Optional.empty();
        }

        OutfitAggregate aggregate = convertor.toAggregate(outfitPO);

        // 加载关联的图片
        List<OutfitImagePO> imagePOs = outfitImageDao.findByOutfitId(id);
        List<OutfitImage> images = convertor.toImageEntities(imagePOs);
        aggregate.setImages(images);

        // TODO: 加载关联的服装和穿着记录

        return Optional.of(aggregate);
    }

    @Override
    public Optional<OutfitAggregate> findByIdIncludeDeleted(String id) {
        log.debug("根据ID查找穿搭聚合根（包含已删除的）: outfitId={}", id);

        OutfitPO outfitPO = outfitDao.findByIdIncludeDeleted(id);
        if (outfitPO == null) {
            return Optional.empty();
        }

        OutfitAggregate aggregate = convertor.toAggregate(outfitPO);

        // 加载关联的图片（需要实现包含已删除的查询）
        List<OutfitImagePO> imagePOs = outfitImageDao.findByOutfitId(id);
        List<OutfitImage> images = convertor.toImageEntities(imagePOs);
        aggregate.setImages(images);

        return Optional.of(aggregate);
    }

    @Override
    public List<OutfitAggregate> findByUserId(String userId) {
        log.debug("根据用户ID查找穿搭聚合根列表: userId={}", userId);

        List<OutfitPO> outfitPOs = outfitDao.findByUserId(userId);
        if (outfitPOs.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> outfitIds = outfitPOs.stream()
                .map(OutfitPO::getId)
                .collect(Collectors.toList());

        // 批量加载图片
        List<OutfitImagePO> allImagePOs = outfitImageDao.findByOutfitIds(outfitIds);

        return outfitPOs.stream().map(outfitPO -> {
            OutfitAggregate aggregate = convertor.toAggregate(outfitPO);

            // 过滤当前穿搭的图片
            List<OutfitImagePO> imagePOs = allImagePOs.stream()
                    .filter(po -> po.getOutfitId().equals(outfitPO.getId()))
                    .collect(Collectors.toList());
            List<OutfitImage> images = convertor.toImageEntities(imagePOs);
            aggregate.setImages(images);

            return aggregate;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<OutfitAggregate> findByUserIdAndName(String userId, String name) {
        log.debug("根据用户ID和穿搭名称查找穿搭聚合根: userId={}, name={}", userId, name);

        OutfitPO outfitPO = outfitDao.findByUserIdAndName(userId, name);
        if (outfitPO == null) {
            return Optional.empty();
        }

        OutfitAggregate aggregate = convertor.toAggregate(outfitPO);

        // 加载关联的图片
        List<OutfitImagePO> imagePOs = outfitImageDao.findByOutfitId(outfitPO.getId());
        List<OutfitImage> images = convertor.toImageEntities(imagePOs);
        aggregate.setImages(images);

        return Optional.of(aggregate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        log.debug("删除穿搭聚合根: outfitId={}", id);

        // 逻辑删除穿搭
        OutfitPO outfitPO = outfitDao.findById(id);
        if (outfitPO != null) {
            // 设置删除标记（假设BasePO中有逻辑删除字段）
            outfitDao.deleteById(id);
        }

        // 删除关联的图片
        outfitImageDao.deleteByOutfitId(id);

        // TODO: 删除关联的服装和穿着记录
    }

    @Override
    public boolean existsById(String id) {
        log.debug("检查穿搭是否存在: outfitId={}", id);
        OutfitPO outfitPO = outfitDao.findById(id);
        return outfitPO != null;
    }

    @Override
    public boolean existsByUserIdAndName(String userId, String name) {
        log.debug("检查同一用户下穿搭名称是否已存在: userId={}, name={}", userId, name);
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
}
