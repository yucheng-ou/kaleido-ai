package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.api.admin.auth.enums.PermissionType;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.PermissionConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.PermissionDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.PermissionPO;
import com.xiaoo.kaleido.api.admin.auth.request.PermissionPageQueryReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 权限仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements IPermissionRepository {

    private final PermissionDao permissionDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PermissionAggregate save(PermissionAggregate permission) {
        PermissionPO po = PermissionConvertor.INSTANCE.toPO(permission);
        if (po.getId() == null) {
            permissionDao.insert(po);
        } else {
            permissionDao.updateById(po);
        }
        return PermissionConvertor.INSTANCE.toEntity(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PermissionAggregate> saveAll(List<PermissionAggregate> permissions) {
        List<PermissionAggregate> savedList = new ArrayList<>();
        for (PermissionAggregate permission : permissions) {
            savedList.add(save(permission));
        }
        return savedList;
    }

    @Override
    public Optional<PermissionAggregate> findById(String id) {
        PermissionPO po = permissionDao.findById(id);
        return po != null ? Optional.of(PermissionConvertor.INSTANCE.toEntity(po)) : Optional.empty();
    }

    @Override
    public Optional<PermissionAggregate> findByCode(String code) {
        PermissionPO po = permissionDao.findByCode(code);
        return po != null ? Optional.of(PermissionConvertor.INSTANCE.toEntity(po)) : Optional.empty();
    }

    @Override
    public List<PermissionAggregate> findByParentId(String parentId) {
        List<PermissionPO> poList = permissionDao.findByParentId(parentId);
        return convertList(poList);
    }

    @Override
    public List<PermissionAggregate> findRootPermissions() {
        List<PermissionPO> poList = permissionDao.findRootPermissions();
        return convertList(poList);
    }

    @Override
    public List<PermissionAggregate> findByType(PermissionType type) {
        // 处理类型转换：如果type为null，传递null；否则传递code的字符串表示
        String typeCode = null;
        if (type != null) {
            // 直接使用枚举的name()方法，因为code现在是字符串
            typeCode = type.name();
        }
        List<PermissionPO> poList = permissionDao.findByType(typeCode);
        return convertList(poList);
    }

    @Override
    public List<PermissionAggregate> findAll() {
        List<PermissionPO> poList = permissionDao.findAll();
        return convertList(poList);
    }

    @Override
    public List<PermissionAggregate> findAllById(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<PermissionPO> poList = permissionDao.findAllById(ids);
        return convertList(poList);
    }

    @Override
    public List<PermissionAggregate> findAllByCode(List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return new ArrayList<>();
        }
        List<PermissionPO> poList = permissionDao.findAllByCode(codes);
        return convertList(poList);
    }

    @Override
    public List<PermissionAggregate> findByCondition(PermissionPageQueryReq req) {
        List<PermissionPO> poList = permissionDao.findByCondition(req);
        return convertList(poList);
    }

    @Override
    public List<PermissionAggregate> getPermissionTree() {
        List<PermissionPO> poList = permissionDao.getPermissionTree();
        return convertList(poList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        permissionDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllById(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (String id : ids) {
            deleteById(id);
        }
    }

    @Override
    public boolean existsById(String id) {
        return permissionDao.existsById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return permissionDao.existsByCode(code);
    }

    @Override
    public long count() {
        return permissionDao.count();
    }

    @Override
    public long countByParentId(String parentId) {
        return permissionDao.countByParentId(parentId);
    }

    @Override
    public long countByType(PermissionType type) {
        // 处理类型转换：如果type为null，传递null；否则传递code的字符串表示
        String typeCode = null;
        if (type != null) {
            // 直接使用枚举的name()方法，因为code现在是字符串
            typeCode = type.name();
        }
        return permissionDao.countByType(typeCode);
    }

    /**
     * 转换PO列表为聚合根列表
     */
    private List<PermissionAggregate> convertList(List<PermissionPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }
        return poList.stream()
                .map(PermissionConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findCodesByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return permissionDao.findCodesByIds(ids);
    }

    @Override
    public List<String> findCodesByRoleIds(List<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        return permissionDao.findCodesByRoleIds(roleIds);
    }
}
