package com.xiaoo.kaleido.admin.infrastructure.adapter.repository;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminUserRepository;
import com.xiaoo.kaleido.admin.domain.user.constant.AdminUserStatus;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminUserAggregate;
import com.xiaoo.kaleido.admin.infrastructure.convertor.AdminUserConvertor;
import com.xiaoo.kaleido.admin.infrastructure.dao.AdminUserDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.AdminUserRoleDao;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminUserPO;
import com.xiaoo.kaleido.admin.infrastructure.dao.po.AdminUserRolePO;
import com.xiaoo.kaleido.api.admin.auth.request.AdminUserPageQueryReq;
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
 * 管理员仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminUserRepositoryImpl implements IAdminUserRepository {

    private final AdminUserDao adminUserDao;
    private final AdminUserRoleDao adminUserRoleDao;

    @Override
    public AdminUserAggregate save(AdminUserAggregate adminUser) {
        // 转换并保存管理员
        AdminUserPO po = AdminUserConvertor.INSTANCE.toPO(adminUser);

        //插入角色
        adminUserDao.insert(po);

        // 保存角色关联
        saveUserRoles(adminUser.getId(), adminUser.getRoleIds());

        return AdminUserConvertor.INSTANCE.toEntity(po);
    }

    @Override
    public AdminUserAggregate update(AdminUserAggregate adminUser) {
        // 转换并保存管理员
        AdminUserPO po = AdminUserConvertor.INSTANCE.toPO(adminUser);

        //更新管理员信息
        adminUserDao.updateById(po);

        // 保存角色关联
        saveUserRoles(adminUser.getId(), adminUser.getRoleIds());

        return AdminUserConvertor.INSTANCE.toEntity(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<AdminUserAggregate> saveAll(List<AdminUserAggregate> adminUsers) {
        List<AdminUserAggregate> savedList = new ArrayList<>();
        for (AdminUserAggregate adminUser : adminUsers) {
            savedList.add(save(adminUser));
        }
        return savedList;
    }

    @Override
    public Optional<AdminUserAggregate> findById(String id) {
        AdminUserPO po = adminUserDao.findById(id);
        if (po == null) {
            return Optional.empty();
        }
        AdminUserAggregate aggregate = AdminUserConvertor.INSTANCE.toEntity(po);
        // 加载角色ID
        loadRoleIds(aggregate);
        return Optional.of(aggregate);
    }

    @Override
    public Optional<AdminUserAggregate> findByUsername(String username) {
        AdminUserPO po = adminUserDao.findByUsername(username);
        if (po == null) {
            return Optional.empty();
        }
        AdminUserAggregate aggregate = AdminUserConvertor.INSTANCE.toEntity(po);
        loadRoleIds(aggregate);
        return Optional.of(aggregate);
    }

    @Override
    public Optional<AdminUserAggregate> findByMobile(String mobile) {
        AdminUserPO po = adminUserDao.findByMobile(mobile);
        if (po == null) {
            return Optional.empty();
        }
        AdminUserAggregate aggregate = AdminUserConvertor.INSTANCE.toEntity(po);
        loadRoleIds(aggregate);
        return Optional.of(aggregate);
    }

    @Override
    public List<AdminUserAggregate> findByStatus(AdminUserStatus status) {
        List<AdminUserPO> poList = adminUserDao.findByStatus(status != null ? status.getCode() : null);
        return convertAndLoadRoleIds(poList);
    }

    @Override
    public List<AdminUserAggregate> findAll() {
        List<AdminUserPO> poList = adminUserDao.findAll();
        return convertAndLoadRoleIds(poList);
    }

    @Override
    public List<AdminUserAggregate> findAllById(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<AdminUserPO> poList = adminUserDao.findAllById(ids);
        return convertAndLoadRoleIds(poList);
    }

    @Override
    public List<AdminUserAggregate> findAllByUsername(List<String> usernames) {
        if (CollectionUtils.isEmpty(usernames)) {
            return new ArrayList<>();
        }
        List<AdminUserPO> poList = adminUserDao.findAllByUsername(usernames);
        return convertAndLoadRoleIds(poList);
    }

    @Override
    public List<AdminUserAggregate> findByRoleId(String roleId) {
        List<AdminUserPO> poList = adminUserDao.findByRoleId(roleId);
        return convertAndLoadRoleIds(poList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        // 删除角色关联
        adminUserRoleDao.deleteByAdminUserId(id);
        // 删除管理员
        adminUserDao.deleteById(id);
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
        return adminUserDao.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return adminUserDao.existsByUsername(username);
    }

    @Override
    public boolean existsByMobile(String mobile) {
        return adminUserDao.existsByMobile(mobile);
    }

    @Override
    public long count() {
        return adminUserDao.count();
    }

    @Override
    public long countByStatus(AdminUserStatus status) {
        return adminUserDao.countByStatus(status != null ? status.getCode() : null);
    }

    @Override
    public List<AdminUserAggregate> pageQuery(AdminUserPageQueryReq pageQueryReq) {
        log.debug("分页查询管理员，pageQueryReq={}", pageQueryReq);

        // 执行分页查询（PageHelper已在Service层启动）
        List<AdminUserPO> poList = adminUserDao.pageQuery(pageQueryReq);

        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }

        // 转换PO为聚合根并加载角色ID
        return convertAndLoadRoleIds(poList);
    }

    /**
     * 保存用户角色关联
     */
    private void saveUserRoles(String adminUserId, List<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }

        // 删除旧的关联
        adminUserRoleDao.deleteByAdminUserId(adminUserId);

        // 创建新的关联
        List<AdminUserRolePO> rolePOs = roleIds.stream()
                .map(roleId -> {
                    AdminUserRolePO po = new AdminUserRolePO();
                    po.setAdminUserId(adminUserId);
                    po.setRoleId(roleId);
                    return po;
                })
                .collect(Collectors.toList());

        // 批量插入
        if (!CollectionUtils.isEmpty(rolePOs)) {
            adminUserRoleDao.batchInsert(rolePOs);
        }
    }

    /**
     * 加载用户的角色ID列表
     */
    private void loadRoleIds(AdminUserAggregate aggregate) {
        if (aggregate == null || aggregate.getId() == null) {
            return;
        }
        List<AdminUserRolePO> rolePOs = adminUserRoleDao.findByAdminUserId(aggregate.getId());
        List<String> roleIds = rolePOs.stream()
                .map(AdminUserRolePO::getRoleId)
                .collect(Collectors.toList());
        aggregate.setRoleIds(roleIds);
    }

    /**
     * 转换PO列表并加载角色ID
     */
    private List<AdminUserAggregate> convertAndLoadRoleIds(List<AdminUserPO> poList) {
        if (CollectionUtils.isEmpty(poList)) {
            return new ArrayList<>();
        }

        // 转换PO为聚合根
        List<AdminUserAggregate> aggregates = poList.stream()
                .map(AdminUserConvertor.INSTANCE::toEntity)
                .collect(Collectors.toList());

        // 批量加载角色ID
        if (!CollectionUtils.isEmpty(aggregates)) {
            List<String> adminUserIds = aggregates.stream()
                    .map(AdminUserAggregate::getId)
                    .collect(Collectors.toList());

            List<AdminUserRolePO> allRolePOs = adminUserRoleDao.findByAdminUserIds(adminUserIds);

            // 按用户ID分组
            var roleMap = allRolePOs.stream()
                    .collect(Collectors.groupingBy(AdminUserRolePO::getAdminUserId));

            // 设置角色ID列表
            for (AdminUserAggregate aggregate : aggregates) {
                List<AdminUserRolePO> userRolePOs = roleMap.get(aggregate.getId());
                if (userRolePOs != null) {
                    List<String> roleIds = userRolePOs.stream()
                            .map(AdminUserRolePO::getRoleId)
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
