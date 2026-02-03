package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.StorageLocationAggregate;
import com.xiaoo.kaleido.wardrobe.domain.location.model.entity.LocationImage;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor.LocationInfraConvertor;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.LocationDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.LocationImageDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.LocationPO;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.LocationImagePO;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeErrorCode;
import com.xiaoo.kaleido.wardrobe.types.exception.WardrobeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 位置仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2026/1/17
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LocationRepositoryImpl implements ILocationRepository {

    private final LocationDao locationDao;
    private final LocationImageDao locationImageDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(StorageLocationAggregate locationAggregate) {
        try {
            // 1.转换StorageLocationAggregate为LocationPO
            LocationPO locationPO = LocationInfraConvertor.INSTANCE.toPO(locationAggregate);

            // 2.保存位置基本信息
            locationDao.insert(locationPO);

            // 3.保存图片（如果有）
            if (locationAggregate.hasImages()) {
                List<LocationImagePO> imagePOs = LocationInfraConvertor.INSTANCE.toImagePOs(locationAggregate.getImages());
                locationImageDao.batchInsert(imagePOs);
            }

            log.info("位置保存成功，位置ID: {}, 位置名称: {}, 用户ID: {}, 图片数量: {}",
                    locationAggregate.getId(), locationAggregate.getName(), locationAggregate.getUserId(),
                    locationAggregate.getImageCount());
        } catch (Exception e) {
            log.error("位置保存失败，位置ID: {}, 原因: {}", locationAggregate.getId(), e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.OPERATE_FAILED, "位置保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(name = ":wardrobe:location:", key = "#locationAggregate.id")
    public void update(StorageLocationAggregate locationAggregate) {
        try {
            // 1.转换StorageLocationAggregate为LocationPO
            LocationPO locationPO = LocationInfraConvertor.INSTANCE.toPO(locationAggregate);

            // 2.更新位置基本信息
            locationDao.updateById(locationPO);

            // 3.删除旧图片
            locationImageDao.deleteByLocationId(locationAggregate.getId());

            // 4.保存新图片（如果有）
            if (locationAggregate.hasImages()) {
                List<LocationImagePO> imagePOs = LocationInfraConvertor.INSTANCE.toImagePOs(locationAggregate.getImages());
                locationImageDao.batchInsert(imagePOs);
            }

            log.info("位置更新成功，位置ID: {}, 位置名称: {}, 图片数量: {}",
                    locationAggregate.getId(), locationAggregate.getName(), locationAggregate.getImageCount());
        } catch (Exception e) {
            log.error("位置更新失败，位置ID: {}, 原因: {}", locationAggregate.getId(), e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.OPERATE_FAILED, "位置更新失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(name = ":wardrobe:location:", key = "#locationAggregate.id")
    public void delete(String locationId) {
        try {
            // 1.删除关联图片
            locationImageDao.deleteByLocationId(locationId);
            
            // 2.逻辑删除位置
            locationDao.deleteById(locationId);

            log.info("位置删除成功，位置ID: {}", locationId);
        } catch (Exception e) {
            log.error("位置删除失败，位置ID: {}, 原因: {}", locationId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.OPERATE_FAILED, "位置删除失败");
        }
    }

    @Override
    @Cached(name = ":wardrobe:location:", key = "#locationId", expire = 60, cacheType = CacheType.REMOTE, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    @CacheRefresh(refresh = 50, timeUnit = TimeUnit.MINUTES)
    public StorageLocationAggregate findById(String locationId) {
        try {
            // 1.查询位置基本信息
            LocationPO locationPO = locationDao.findById(locationId);
            if (locationPO == null) {
                throw WardrobeException.of(BizErrorCode.DATA_NOT_EXIST);
            }

            // 2.查询该位置的所有图片
            List<LocationImagePO> imagePOs = locationImageDao.findByLocationId(locationId);

            // 3.转换为StorageLocationAggregate
            StorageLocationAggregate locationAggregate = LocationInfraConvertor.INSTANCE.toAggregate(locationPO);
            
            // 4.添加图片到聚合根
            if (imagePOs != null && !imagePOs.isEmpty()) {
                List<LocationImage> images = LocationInfraConvertor.INSTANCE.toImageEntities(imagePOs);
                locationAggregate.addImages(images);
                
                // 设置主图ID
                imagePOs.stream()
                        .filter(LocationImagePO::getIsPrimary)
                        .findFirst()
                        .ifPresent(primaryImage -> locationAggregate.setPrimaryImageId(primaryImage.getId()));
            }

            return locationAggregate;
        } catch (WardrobeException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询位置失败，位置ID: {}, 原因: {}", locationId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "位置查询失败");
        }
    }

    @Override
    public boolean existsByNameAndUserId(String name, String userId) {
        try {
            return locationDao.existsByNameAndUserId(name, userId);
        } catch (Exception e) {
            log.error("检查位置名称唯一性失败，位置名称: {}, 用户ID: {}, 原因: {}", name, userId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "位置名称唯一性检查失败");
        }
    }

    @Override
    public boolean hasClothingReferences(String locationId) {
        try {
            return locationDao.hasClothingReferences(locationId);
        } catch (Exception e) {
            log.error("检查位置引用失败，位置ID: {}, 原因: {}", locationId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "位置引用检查失败");
        }
    }

    @Override
    public List<StorageLocationAggregate> findByUserId(String userId) {
        try {
            // 1.查询用户的所有位置基本信息
            List<LocationPO> locationPOs = locationDao.findByUserId(userId);

            // 2.转换为StorageLocationAggregate列表
            List<StorageLocationAggregate> aggregates = locationPOs.stream()
                    .map(LocationInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());

            // 3.批量加载图片
            if (!aggregates.isEmpty()) {
                loadImagesForAggregates(aggregates);
            }

            return aggregates;
        } catch (Exception e) {
            log.error("查询用户位置失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "用户位置查询失败");
        }
    }

    @Override
    public List<StorageLocationAggregate> findByIds(List<String> locationIds) {
        try {
            // 1.查询位置ID列表对应的位置基本信息
            List<LocationPO> locationPOs = locationDao.findByIds(locationIds);

            // 2.转换为StorageLocationAggregate列表
            List<StorageLocationAggregate> aggregates = locationPOs.stream()
                    .map(LocationInfraConvertor.INSTANCE::toAggregate)
                    .collect(Collectors.toList());

            // 3.批量加载图片
            if (!aggregates.isEmpty()) {
                loadImagesForAggregates(aggregates);
            }

            return aggregates;
        } catch (Exception e) {
            log.error("查询位置列表失败，位置ID列表: {}, 原因: {}", locationIds, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "位置列表查询失败");
        }
    }

    /**
     * 批量加载图片到聚合根列表
     *
     * @param aggregates 位置聚合根列表
     */
    private void loadImagesForAggregates(List<StorageLocationAggregate> aggregates) {
        if (aggregates == null || aggregates.isEmpty()) {
            return;
        }

        // 1.收集所有位置ID
        List<String> locationIds = aggregates.stream()
                .map(StorageLocationAggregate::getId)
                .collect(Collectors.toList());

        // 2.批量查询图片
        List<LocationImagePO> allImagePOs = locationImageDao.findByLocationIds(locationIds);

        // 3.按位置ID分组
        java.util.Map<String, List<LocationImagePO>> imagesByLocationId = allImagePOs.stream()
                .collect(Collectors.groupingBy(LocationImagePO::getLocationId));

        // 4.为每个聚合根添加图片
        for (StorageLocationAggregate aggregate : aggregates) {
            List<LocationImagePO> imagePOs = imagesByLocationId.get(aggregate.getId());
            if (imagePOs != null && !imagePOs.isEmpty()) {
                List<LocationImage> images = LocationInfraConvertor.INSTANCE.toImageEntities(imagePOs);
                aggregate.addImages(images);

                // 设置主图ID
                imagePOs.stream()
                        .filter(LocationImagePO::getIsPrimary)
                        .findFirst()
                        .ifPresent(primaryImage -> aggregate.setPrimaryImageId(primaryImage.getId()));
            }
        }
    }
}
