package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.PermissionConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.PermissionDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.PermissionPO;
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
        // 1. 转换聚合根为PO
        PermissionPO po = PermissionConvertor.INSTANCE.toPO(permission);
        
        // 2. 保存到数据库
        permissionDao.insert(po);
        
        // 3. 转换PO为聚合根并返回
        return PermissionConvertor.INSTANCE.toEntity(po);
    }

    @Override
    public PermissionAggregate update(PermissionAggregate permission) {
        // 1. 转换聚合根为PO
        PermissionPO po = PermissionConvertor.INSTANCE.toPO(permission);
        
        // 2. 更新到数据库
        permissionDao.updateById(po);
        
        // 3. 转换PO为聚合根并返回
        return PermissionConvertor.INSTANCE.toEntity(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PermissionAggregate> saveAll(List<PermissionAggregate> permissions) {
        // 1. 创建保存结果列表
        List<PermissionAggregate> savedList = new ArrayList<>();
        
        // 2. 遍历权限列表，逐个保存
        for (PermissionAggregate permission : permissions) {
            savedList.add(save(permission));
        }
        
        // 3. 返回保存结果列表
        return savedList;
    }

    @Override
    public Optional<PermissionAggregate> findById(String id) {
        // 1. 根据ID查询PO
        PermissionPO po = permissionDao.findById(id);
        
        // 2. 如果PO存在则转换为聚合根，否则返回空Optional
        return po != null ? Optional.of(PermissionConvertor.INSTANCE.toEntity(po)) : Optional.empty();
    }

    @Override
    public Optional<PermissionAggregate> findByCode(String code) {
        // 1. 根据编码查询PO
        PermissionPO po = permissionDao.findByCode(code);
        
        // 2. 如果PO存在则转换为聚合根，否则返回空Optional
        return po != null ? Optional.of(PermissionConvertor.INSTANCE.toEntity(po)) : Optional.empty();
    }

    @Override
    public List<PermissionAggregate> findByParentId(String parentId) {
        // 1. 根据父权限ID查询PO列表
        List<PermissionPO> poList = permissionDao.findByParentId(parentId);
        
        // 2. 转换PO列表为聚合根列表
        return convertList(poList);
    }

    @Override
    public List<PermissionAggregate> findRootPermissions() {
        // 1. 查询根权限PO列表
        List<PermissionPO> poList = permissionDao.findRootPermissions();
        
        // 2. 转换PO列表为聚合根列表
        return convertList(poList);
    }

    @Override
    public List<PermissionAggregate> getPermissionTree() {
        // 1. 查询权限树PO列表
        List<PermissionPO> poList = permissionDao.getPermissionTree();
        
        // 2. 转换PO列表为聚合根列表
        return convertList(poList);
    }

    @Override
    public void deleteById(String id) {
        // 根据ID删除权限
        permissionDao.deleteById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        // 检查权限编码是否存在
        return permissionDao.existsByCode(code);
    }

    @Override
    public long countByParentId(String parentId) {
        // 统计指定父权限ID下的子权限数量
        return permissionDao.countByParentId(parentId);
    }


    private List<PermissionAggregate> convertList(List<PermissionPO> poList) {
        // 1. 检查PO列表是否为空
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }
        
        // 2. 转换PO列表为聚合根列表
        return poList.stream()
                .map(PermissionConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findCodesByRoleIds(List<String> roleIds) {
        // 1. 检查角色ID列表是否为空
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        
        // 2. 查询角色关联的权限编码列表
        return permissionDao.findCodesByRoleIds(roleIds);
    }
}
