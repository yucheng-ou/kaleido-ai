package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.RoleConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.RoleDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.RolePermissionDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.RolePO;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.RolePermissionPO;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
    public RoleAggregate save(RoleAggregate role) {
        // 1. 转换聚合根为PO
        RolePO po = RoleConvertor.INSTANCE.toPO(role);
        
        // 2. 保存到数据库
        roleDao.insert(po);

        // 3. 转换PO为聚合根并返回
        return RoleConvertor.INSTANCE.toEntity(po);
    }

    @Override
    public RoleAggregate update(RoleAggregate role) {
        // 1. 转换聚合根为PO
        RolePO po = RoleConvertor.INSTANCE.toPO(role);

        // 2. 更新到数据库
        roleDao.updateById(po);

        // 3. 转换PO为聚合根并返回
        return RoleConvertor.INSTANCE.toEntity(po);
    }

    @Override
    public RoleAggregate findById(String id) {
        // 1. 根据ID查询PO
        RolePO po = roleDao.findById(id);
        if (po == null) {
            return null;
        }
        
        // 2. 转换PO为聚合根
        RoleAggregate aggregate = RoleConvertor.INSTANCE.toEntity(po);
        
        // 3. 加载权限ID
        loadPermissionIds(aggregate);
        
        // 4. 返回结果
        return aggregate;
    }

    @Override
    public RoleAggregate findByCode(String code) {
        // 1. 根据编码查询PO
        RolePO po = roleDao.findByCode(code);
        if (po == null) {
            return null;
        }
        
        // 2. 转换PO为聚合根
        RoleAggregate aggregate = RoleConvertor.INSTANCE.toEntity(po);
        
        // 3. 加载权限ID
        loadPermissionIds(aggregate);
        
        // 4. 返回结果
        return aggregate;
    }

    @Override
    public List<RoleAggregate> findAllById(List<String> ids) {
        // 1. 检查ID列表是否为空
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        
        // 2. 根据ID列表查询PO列表
        List<RolePO> poList = roleDao.findAllById(ids);
        
        // 3. 转换PO列表并加载权限ID
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findAllByCode(List<String> codes) {
        // 1. 检查编码列表是否为空
        if (CollectionUtils.isEmpty(codes)) {
            return new ArrayList<>();
        }
        
        // 2. 根据编码列表查询PO列表
        List<RolePO> poList = roleDao.findAllByCode(codes);
        
        // 3. 转换PO列表并加载权限ID
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    public List<RoleAggregate> findAll() {
        // 1. 查询所有PO列表
        List<RolePO> poList = roleDao.findAll();
        
        // 2. 转换PO列表并加载权限ID
        return convertAndLoadPermissionIds(poList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        // 1. 删除角色权限关联
        rolePermissionDao.deleteByRoleId(id);
        
        // 2. 删除角色
        roleDao.deleteById(id);
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
    @Transactional(rollbackFor = Exception.class)
    public void saveRolePermissions(String roleId, List<String> permissionIds) {
        // 1. 检查权限ID列表是否为空
        if (CollectionUtils.isEmpty(permissionIds)) {
            return;
        }

        // 2. 删除旧的权限关联
        rolePermissionDao.deleteByRoleId(roleId);

        // 3. 创建新的权限关联PO列表
        List<RolePermissionPO> permissionPOs = permissionIds.stream()
                .map(permissionId -> {
                    RolePermissionPO po = new RolePermissionPO();
                    po.setId(SnowflakeUtil.newSnowflakeId());
                    po.setRoleId(roleId);
                    po.setPermissionId(permissionId);
                    return po;
                })
                .collect(Collectors.toList());

        // 4. 批量插入新的权限关联
        if (!CollectionUtils.isEmpty(permissionPOs)) {
            rolePermissionDao.batchInsert(permissionPOs);
        }
    }

    private void loadPermissionIds(RoleAggregate aggregate) {
        // 1. 检查聚合根是否有效
        if (aggregate == null || aggregate.getId() == null) {
            return;
        }
        
        // 2. 根据角色ID查询权限关联PO列表
        List<RolePermissionPO> permissionPOs = rolePermissionDao.findByRoleId(aggregate.getId());
        
        // 3. 提取权限ID列表
        List<String> permissionIds = permissionPOs.stream()
                .map(RolePermissionPO::getPermissionId)
                .collect(Collectors.toList());
        
        // 4. 设置权限ID列表到聚合根
        aggregate.setPermissionIds(permissionIds);
    }

    private List<RoleAggregate> convertAndLoadPermissionIds(List<RolePO> poList) {
        // 1. 检查PO列表是否为空
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }

        // 2. 转换PO列表为聚合根列表
        List<RoleAggregate> aggregates = poList.stream()
                .map(RoleConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());

        // 3. 批量加载权限ID
        if (!CollectionUtils.isEmpty(aggregates)) {
            // 3.1 提取角色ID列表
            List<String> roleIds = aggregates.stream()
                    .map(RoleAggregate::getId)
                    .collect(Collectors.toList());

            // 3.2 批量查询权限关联PO列表
            List<RolePermissionPO> allPermissionPOs = rolePermissionDao.findByRoleIds(roleIds);

            // 3.3 按角色ID分组
            var permissionMap = allPermissionPOs.stream()
                    .collect(Collectors.groupingBy(RolePermissionPO::getRoleId));

            // 3.4 为每个聚合根设置权限ID列表
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

        // 4. 返回聚合根列表
        return aggregates;
    }

    @Override
    @Cached(name = ":admin:roles:", key = "#adminId", expire = 60, cacheType = CacheType.REMOTE, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public List<String> findCodesByAdminId(String adminId) {
        // 1. 检查管理员ID是否有效
        if (StrUtil.isBlank(adminId)) {
            return new ArrayList<>();
        }
        
        // 2. 查询管理员关联的角色编码
        return roleDao.findCodesByAdminId(adminId);
    }
}
