package com.xiaoo.kaleido.admin.domain.user.service.impl;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.api.admin.user.enums.PermissionType;
import com.xiaoo.kaleido.admin.domain.user.service.IPermissionDomainService;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限领域服务实现
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionDomainServiceImpl implements IPermissionDomainService {

    private final IPermissionRepository permissionRepository;

    @Override
    public PermissionAggregate createPermission(String code, String name, PermissionType type,
                                                String parentId, Integer sort, String icon,
                                                String path, String component, Boolean isHidden) {
        // 验证权限编码唯一性
        if (permissionRepository.existsByCode(code)) {
            throw AdminException.of(AdminErrorCode.PERMISSION_CODE_EXIST);
        }

        // 验证父权限是否存在
        if (parentId != null && !parentId.trim().isEmpty()) {
            permissionRepository.findById(parentId)
                    .orElseThrow(() -> AdminException.of(AdminErrorCode.PARENT_PERMISSION_NOT_EXIST));
        }

        // 创建权限
        PermissionAggregate permission = PermissionAggregate.create(
                code, name, type, parentId, sort, icon, path, component, isHidden
        );

        log.info("权限领域服务创建权限，权限ID: {}, 权限编码: {}, 权限名称: {}",
                permission.getId(), code, name);

        return permission;
    }

    @Override
    public PermissionAggregate updatePermission(String permissionId, String name, PermissionType type,
                                                String parentId, Integer sort, String icon,
                                                String path, String component, Boolean isHidden) {
        // 获取权限
        PermissionAggregate permission = findByIdOrThrow(permissionId);

        // 验证父权限是否存在（如果父权限ID有变化）
        if (parentId != null && !parentId.equals(permission.getParentId())) {
            if (!parentId.trim().isEmpty()) {
                permissionRepository.findById(parentId)
                        .orElseThrow(() -> AdminException.of(AdminErrorCode.PARENT_PERMISSION_NOT_EXIST));
            }
        }

        // 更新权限信息
        permission.updateInfo(name, type, parentId, sort, icon, path, component, isHidden);

        log.info("权限领域服务更新权限，权限ID: {}, 权限名称: {}", permissionId, name);
        return permission;
    }

    @Override
    public PermissionAggregate updatePermissionCode(String permissionId, String code) {
        // 获取权限
        PermissionAggregate permission = findByIdOrThrow(permissionId);

        // 验证新编码是否可用
        if (!permission.getCode().equals(code) && permissionRepository.existsByCode(code)) {
            throw AdminException.of(AdminErrorCode.PERMISSION_CODE_EXIST);
        }

        // 更新权限编码
        permission.updateCode(code);

        log.info("权限领域服务更新权限编码，权限ID: {}, 新编码: {}", permissionId, code);
        return permission;
    }

    @Override
    public void deletePermission(String permissionId) {
        // 获取权限
        PermissionAggregate permission = findByIdOrThrow(permissionId);

        // 检查是否有子权限
        long childCount = permissionRepository.countByParentId(permissionId);
        if (childCount > 0) {
            throw AdminException.of(AdminErrorCode.PERMISSION_HAS_CHILDREN);
        }

        // 删除权限
        permissionRepository.deleteById(permissionId);

        log.info("权限领域服务删除权限，权限ID: {}", permissionId);
    }

    @Override
    public PermissionAggregate findByIdOrThrow(String permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.PERMISSION_HAS_CHILDREN));
    }

    @Override
    public PermissionAggregate findByCodeOrThrow(String code) {
        return permissionRepository.findByCode(code)
                .orElseThrow(() -> AdminException.of(AdminErrorCode.PERMISSION_NOT_EXIST));
    }

    @Override
    public List<PermissionAggregate> findByParentId(String parentId) {
        return permissionRepository.findByParentId(parentId);
    }

    @Override
    public List<PermissionAggregate> findRootPermissions() {
        return permissionRepository.findRootPermissions();
    }

    @Override
    public List<PermissionAggregate> getPermissionTree() {
        // 使用仓储层提供的权限树方法
        return permissionRepository.getPermissionTree();
    }

    @Override
    public boolean existsByCode(String code) {
        return permissionRepository.existsByCode(code);
    }

    @Override
    public boolean isValidPermission(String permissionId) {
        try {
            PermissionAggregate permission = findByIdOrThrow(permissionId);
            return true; // 这里可以添加更多验证逻辑，比如是否启用等
        } catch (AdminException e) {
            return false;
        }
    }

    @Override
    public boolean isCodeAvailable(String code) {
        return !permissionRepository.existsByCode(code);
    }
}
