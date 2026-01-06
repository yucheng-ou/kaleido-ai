package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.base.constant.enums.DataStatusEnum;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.RoleConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.RoleDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.RolePermissionDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.RolePO;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.RolePermissionPO;
import com.xiaoo.kaleido.api.admin.auth.request.RolePageQueryReq;
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
 * 角色仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements IRoleRepository {

    private final RoleDao roleDao;
    private final RolePermissionDao rolePermissionDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleAggregate save(RoleAggregate role) {
        // 转换并保存角色
        RolePO po = RoleConvertor.INSTANCE.toPO(role);
        if (po.getId() == null) {
            roleDao.insert(po);
        } else {
            roleDao.updateById(po);
        }

        // 保存权限关联
        saveRolePermissions(role.getId(), role.getPermissionIds());

        return RoleConvertor.INSTANCE.toEntity(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RoleAggregate> saveAll(List<RoleAggregate> roles) {
        List<RoleAggregate> savedList = new ArrayList<>();
        for (RoleAggregate role : roles) {
            savedList.add(save(role));
        }
        return savedList;
    }

    @Override
    public Optional<RoleAggregate> findById(String id) {
        RolePO po = roleDao.findById(id);
        if (po == null) {
            return Optional.empty();
        }
        RoleAggregate aggregate = RoleConvertor.INSTANCE.toEntity(po);
        // 加载权限ID
        loadPermissionIds(aggregate);
        return Optional.of(aggregate);
    }

    @Override
    public Optional<RoleAggregate> findByCode(String code) {
        RolePO po = roleDao.findByCode(code);
        if (po == null) {
            return Optional.empty();
        }
        RoleAggregate aggregate = RoleConvertor.INSTANCE.toEntity(po);
        loadPermissionIds(aggregate);
        return Optional.of(aggregate);
    }

    @Override
    public List<RoleAggregate> findByStatus(DataStatusEnum status) {
        // 注意：这里需要将 DataStatusEnum 转换为数据库存储的值
        // 由于数据库存储的是枚举名称（字符串），我们使用 name() 方法
        List<RolePO> poList = roleDao.findByStatus(status != null ? status.name() : null);
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findByIsSystem(Boolean isSystem) {
        List<RolePO> poList = roleDao.findByIsSystem(isSystem);
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findAll() {
        List<RolePO> poList = roleDao.findAll();
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findAllById(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<RolePO> poList = roleDao.findAllById(ids);
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findAllByCode(List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return new ArrayList<>();
        }
        List<RolePO> poList = roleDao.findAllByCode(codes);
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findByPermissionId(String permissionId) {
        List<RolePO> poList = roleDao.findByPermissionId(permissionId);
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findByCondition(RolePageQueryReq req) {
        List<RolePO> poList = roleDao.findByCondition(req);
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findEnabledRoles() {
        List<RolePO> poList = roleDao.findEnabledRoles();
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findSystemRoles() {
        List<RolePO> poList = roleDao.findSystemRoles();
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> getRoleTree() {
        List<RolePO> poList = roleDao.getRoleTree();
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        // 删除权限关联
        rolePermissionDao.deleteByRoleId(id);
        // 删除角色
        roleDao.deleteById(id);
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
        return roleDao.existsById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return roleDao.existsByCode(code);
    }

    @Override
    public long count() {
        return roleDao.count();
    }

    @Override
    public long countByStatus(DataStatusEnum status) {
        // 注意：这里需要将 DataStatusEnum 转换为数据库存储的值
        // 由于数据库存储的是枚举名称（字符串），我们使用 name() 方法
        return roleDao.countByStatus(status != null ? status.name() : null);
    }

    @Override
    public long countByIsSystem(Boolean isSystem) {
        return roleDao.countByIsSystem(isSystem);
    }

    /**
     * 保存角色权限关联
     */
    private void saveRolePermissions(String roleId, List<String> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds)) {
            return;
        }

        // 删除旧的关联
        rolePermissionDao.deleteByRoleId(roleId);

        // 创建新的关联
        List<RolePermissionPO> permissionPOs = permissionIds.stream()
                .map(permissionId -> {
                    RolePermissionPO po = new RolePermissionPO();
                    po.setRoleId(roleId);
                    po.setPermissionId(permissionId);
                    return po;
                })
                .collect(Collectors.toList());

        // 批量插入
        if (!CollectionUtils.isEmpty(permissionPOs)) {
            rolePermissionDao.batchInsert(permissionPOs);
        }
    }

    /**
     * 加载角色的权限ID列表
     */
    private void loadPermissionIds(RoleAggregate aggregate) {
        if (aggregate == null || aggregate.getId() == null) {
            return;
        }
        List<RolePermissionPO> permissionPOs = rolePermissionDao.findByRoleId(aggregate.getId());
        List<String> permissionIds = permissionPOs.stream()
                .map(RolePermissionPO::getPermissionId)
                .collect(Collectors.toList());
        aggregate.setPermissionIds(permissionIds);
    }

    /**
     * 转换PO列表并加载权限ID
     */
    private List<RoleAggregate> convertAndLoadPermissionIds(List<RolePO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }

        // 转换PO为聚合根
        List<RoleAggregate> aggregates = poList.stream()
                .map(RoleConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());

        // 批量加载权限ID
        if (!CollectionUtils.isEmpty(aggregates)) {
            List<String> roleIds = aggregates.stream()
                    .map(RoleAggregate::getId)
                    .collect(Collectors.toList());

            List<RolePermissionPO> allPermissionPOs = rolePermissionDao.findByRoleIds(roleIds);

            // 按角色ID分组
            var permissionMap = allPermissionPOs.stream()
                    .collect(Collectors.groupingBy(RolePermissionPO::getRoleId));

            // 设置权限ID列表
            for (RoleAggregate aggregate : aggregates) {
                List<RolePermissionPO> rolePermissionPOs = permissionMap.get(aggregate.getId());
                if (rolePermissionPOs != null) {
                    List<String> permissionIds = rolePermissionPOs.stream()
                            .map(RolePermissionPO::getPermissionId)
                            .collect(Collectors.toList());
                    aggregate.setPermissionIds(permissionIds);
                } else {
                    aggregate.setPermissionIds(new ArrayList<>());
                }
            }
        }

        return aggregates;
    }

    @Override
    public List<String> findCodesByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return roleDao.findCodesByIds(ids);
    }

    @Override
    public List<String> findCodesByAdminId(String adminId) {
        if (adminId == null || adminId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return roleDao.findCodesByAdminId(adminId);
    }
}
