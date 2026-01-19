package com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.wardrobe.domain.location.adapter.repository.ILocationRecordRepository;
import com.xiaoo.kaleido.wardrobe.domain.location.model.aggregate.LocationRecordAggregate;
import com.xiaoo.kaleido.wardrobe.infrastructure.adapter.repository.convertor.LocationRecordInfraConvertor;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.LocationRecordDao;
import com.xiaoo.kaleido.wardrobe.infrastructure.dao.po.LocationRecordPO;
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
 * 位置记录仓储实现（基础设施层）
 * <p>
 * 位置记录仓储接口的具体实现，负责位置记录聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LocationRecordRepositoryImpl implements ILocationRecordRepository {

    private final LocationRecordDao locationRecordDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(LocationRecordAggregate locationRecordAggregate) {
        try {
            // 转换LocationRecordAggregate为LocationRecordPO
            LocationRecordPO locationRecordPO = LocationRecordInfraConvertor.INSTANCE.toPO(locationRecordAggregate);

            // 保存位置记录
            locationRecordDao.insert(locationRecordPO);

            log.info("位置记录保存成功，记录ID: {}, 服装ID: {}, 位置ID: {}, 用户ID: {}",
                    locationRecordAggregate.getId(),
                    locationRecordAggregate.getClothingId(),
                    locationRecordAggregate.getLocationId(),
                    locationRecordAggregate.getUserId());
        } catch (Exception e) {
            log.error("位置记录保存失败，服装ID: {}, 位置ID: {}, 原因: {}",
                    locationRecordAggregate.getClothingId(),
                    locationRecordAggregate.getLocationId(),
                    e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.OPERATE_FAILED, "位置记录保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LocationRecordAggregate locationRecordAggregate) {
        try {
            // 转换LocationRecordAggregate为LocationRecordPO
            LocationRecordPO locationRecordPO = LocationRecordInfraConvertor.INSTANCE.toPO(locationRecordAggregate);

            // 更新位置记录
            int updated = locationRecordDao.updateCurrentStatus(locationRecordPO.getId(),
                    locationRecordAggregate.isCurrentRecord() ? 1 : 0);
            if (updated == 0) {
                throw WardrobeException.of(WardrobeErrorCode.DATA_NOT_FOUND);
            }

            log.info("位置记录更新成功，记录ID: {}, 当前状态: {}",
                    locationRecordAggregate.getId(),
                    locationRecordAggregate.isCurrentRecord());
        } catch (Exception e) {
            log.error("位置记录更新失败，记录ID: {}, 原因: {}",
                    locationRecordAggregate.getId(),
                    e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.OPERATE_FAILED, "位置记录更新失败");
        }
    }

    @Override
    public Optional<LocationRecordAggregate> findById(String id) {
        try {
            // 查询位置记录
            LocationRecordPO locationRecordPO = locationRecordDao.selectById(id);
            if (locationRecordPO == null) {
                return Optional.empty();
            }

            // 转换为LocationRecordAggregate
            LocationRecordAggregate locationRecordAggregate = LocationRecordInfraConvertor.INSTANCE.toAggregate(locationRecordPO);
            return Optional.of(locationRecordAggregate);
        } catch (Exception e) {
            log.error("查询位置记录失败，记录ID: {}, 原因: {}", id, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "位置记录查询失败");
        }
    }

    @Override
    public LocationRecordAggregate findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(() -> WardrobeException.of(BizErrorCode.DATA_NOT_EXIST));
    }

    @Override
    public Optional<LocationRecordAggregate> findCurrentByClothingId(String clothingId) {
        try {
            // 查询当前位置记录
            LocationRecordPO locationRecordPO = locationRecordDao.selectCurrentByClothingId(clothingId);
            if (locationRecordPO == null) {
                return Optional.empty();
            }

            // 转换为LocationRecordAggregate
            LocationRecordAggregate locationRecordAggregate = LocationRecordInfraConvertor.INSTANCE.toAggregate(locationRecordPO);
            return Optional.of(locationRecordAggregate);
        } catch (Exception e) {
            log.error("查询服装当前位置记录失败，服装ID: {}, 原因: {}", clothingId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "服装当前位置记录查询失败");
        }
    }

    @Override
    public List<LocationRecordAggregate> findCurrentByLocationId(String locationId) {
        try {
            // 查询位置的所有当前位置记录
            List<LocationRecordPO> locationRecordPOs = locationRecordDao.selectCurrentByLocationId(locationId);

            // 转换为LocationRecordAggregate列表
            return LocationRecordInfraConvertor.INSTANCE.toAggregateList(locationRecordPOs);
        } catch (Exception e) {
            log.error("查询位置当前位置记录失败，位置ID: {}, 原因: {}", locationId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "位置当前位置记录查询失败");
        }
    }

    @Override
    public List<LocationRecordAggregate> findCurrentByUserId(String userId) {
        try {
            // 查询用户的所有当前位置记录
            List<LocationRecordPO> locationRecordPOs = locationRecordDao.selectCurrentByUserId(userId);

            // 转换为LocationRecordAggregate列表
            return LocationRecordInfraConvertor.INSTANCE.toAggregateList(locationRecordPOs);
        } catch (Exception e) {
            log.error("查询用户当前位置记录失败，用户ID: {}, 原因: {}", userId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "用户当前位置记录查询失败");
        }
    }

    @Override
    public List<LocationRecordAggregate> findByClothingId(String clothingId) {
        try {
            // 查询服装的所有位置记录
            List<LocationRecordPO> locationRecordPOs = locationRecordDao.selectByClothingId(clothingId);

            // 转换为LocationRecordAggregate列表
            return LocationRecordInfraConvertor.INSTANCE.toAggregateList(locationRecordPOs);
        } catch (Exception e) {
            log.error("查询服装位置记录失败，服装ID: {}, 原因: {}", clothingId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "服装位置记录查询失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markAllAsNotCurrentByClothingId(String clothingId) {
        try {
            // 将服装的所有位置记录标记为非当前
            int updated = locationRecordDao.markAllAsNotCurrentByClothingId(clothingId);
            log.info("服装位置记录标记为非当前成功，服装ID: {}, 更新记录数: {}", clothingId, updated);
        } catch (Exception e) {
            log.error("服装位置记录标记为非当前失败，服装ID: {}, 原因: {}", clothingId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.OPERATE_FAILED, "服装位置记录标记为非当前失败");
        }
    }

    @Override
    public boolean existsCurrentByClothingIdAndLocationId(String clothingId, String locationId) {
        try {
            return locationRecordDao.existsCurrentByClothingIdAndLocationId(clothingId, locationId);
        } catch (Exception e) {
            log.error("检查服装是否在位置失败，服装ID: {}, 位置ID: {}, 原因: {}",
                    clothingId, locationId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "检查服装是否在位置失败");
        }
    }

    @Override
    public int countCurrentByLocationId(String locationId) {
        try {
            return locationRecordDao.countCurrentByLocationId(locationId);
        } catch (Exception e) {
            log.error("统计位置中服装数量失败，位置ID: {}, 原因: {}", locationId, e.getMessage(), e);
            throw WardrobeException.of(WardrobeErrorCode.QUERY_FAIL, "统计位置中服装数量失败");
        }
    }
}
