package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminRepository;
import com.xiaoo.kaleido.admin.domain.user.constant.AdminStatus;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.AdminConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.AdminDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.AdminRoleDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminPO;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminRolePO;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.admin.user.request.AdminPageQueryReq;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 管理员仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements IAdminRepository {

    private final AdminDao adminDao;
    private final AdminRoleDao adminRoleDao;

    @Override
    public AdminAggregate save(AdminAggregate admin) {
        // 1. 转换并保存管理员
        AdminPO po = AdminConvertor.INSTANCE.toPO(admin);

        // 2. 插入角色
        try {
            adminDao.insert(po);
        } catch (DuplicateKeyException e) {
            throw AdminException.of(BizErrorCode.UNIQUE_INDEX_CONFLICT.getCode(), "手机号已存在");
        }

        return AdminConvertor.INSTANCE.toEntity(po);
    }

    @Override
    public AdminAggregate update(AdminAggregate admin) {
        // 1. 转换并保存管理员
        AdminPO po = AdminConvertor.INSTANCE.toPO(admin);

        // 2. 更新管理员信息
        adminDao.updateById(po);

        return AdminConvertor.INSTANCE.toEntity(po);
    }

    @Override
    public Optional<AdminAggregate> findById(String id) {
        // 1. 查询管理员PO
        AdminPO po = adminDao.findById(id);
        if (po == null) {
            return Optional.empty();
        }
        
        // 2. 转换为聚合根
        AdminAggregate aggregate = AdminConvertor.INSTANCE.toEntity(po);
        
        // 3. 加载角色ID
        loadRoleIds(aggregate);
        
        return Optional.of(aggregate);
    }

    @Override
    public Optional<AdminAggregate> findByMobile(String mobile) {
        // 1. 查询管理员PO
        AdminPO po = adminDao.findByMobile(mobile);
        if (po == null) {
            return Optional.empty();
        }
        
        // 2. 转换为聚合根
        AdminAggregate aggregate = AdminConvertor.INSTANCE.toEntity(po);
        
        // 3. 加载角色ID
        loadRoleIds(aggregate);
        
        return Optional.of(aggregate);
    }
    
    @Override
    public boolean existsByMobile(String mobile) {
        return adminDao.existsByMobile(mobile);
    }
    
    @Override
    public List<AdminAggregate> pageQuery(AdminPageQueryReq pageQueryReq) {
        log.debug("分页查询管理员，pageQueryReq={}", pageQueryReq);

        // 1. 执行分页查询（PageHelper已在Service层启动）
        List<AdminPO> poList = adminDao.pageQuery(pageQueryReq);

        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }

        // 2. 转换PO为聚合根并加载角色ID
        return convertAndLoadRoleIds(poList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(AdminAggregate admin) {
        if (CollectionUtils.isEmpty(admin.getRoleIds())) {
            return;
        }

        // 1. 删除旧的关联
        adminRoleDao.deleteByAdminId(admin.getId());

        // 2. 创建新的关联
        List<AdminRolePO> rolePOs = admin.getRoleIds().stream()
                .map(roleId -> {
                    AdminRolePO po = new AdminRolePO();
                    po.setAdminId(admin.getId());
                    po.setRoleId(roleId);
                    return po;
                })
                .collect(Collectors.toList());

        // 3. 批量插入
        if (!CollectionUtils.isEmpty(rolePOs)) {
            adminRoleDao.batchInsert(rolePOs);
        }
    }

    private void loadRoleIds(AdminAggregate aggregate) {
        if (aggregate == null || aggregate.getId() == null) {
            return;
        }
        
        // 1. 查询用户角色关联
        List<AdminRolePO> rolePOs = adminRoleDao.findByAdminId(aggregate.getId());
        
        // 2. 提取角色ID列表
        List<String> roleIds = rolePOs.stream()
                .map(AdminRolePO::getRoleId)
                .collect(Collectors.toList());
        
        // 3. 设置到聚合根
        aggregate.setRoleIds(roleIds);
    }

    private List<AdminAggregate> convertAndLoadRoleIds(List<AdminPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }

        // 1. 转换PO为聚合根
        List<AdminAggregate> aggregates = poList.stream()
                .map(AdminConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());

        // 2. 批量加载角色ID
        if (!CollectionUtils.isEmpty(aggregates)) {
            // 2.1 收集所有管理员ID
            List<String> adminIds = aggregates.stream()
                    .map(AdminAggregate::getId)
                    .collect(Collectors.toList());

            // 2.2 批量查询角色关联
            List<AdminRolePO> allRolePOs = adminRoleDao.findByAdminIds(adminIds);

            // 2.3 按用户ID分组
            var roleMap = allRolePOs.stream()
                    .collect(Collectors.groupingBy(AdminRolePO::getAdminId));

            // 2.4 设置角色ID列表
            for (AdminAggregate aggregate : aggregates) {
                List<AdminRolePO> userRolePOs = roleMap.get(aggregate.getId());
                if (userRolePOs != null) {
                    List<String> roleIds = userRolePOs.stream()
                            .map(AdminRolePO::getRoleId)
                            .collect(Collectors.toList());
                    aggregate.setRoleIds(roleIds);
                } else {
                    aggregate.setRoleIds(new ArrayList<>());
                }
            }
        }

        return aggregates;
    }
}
