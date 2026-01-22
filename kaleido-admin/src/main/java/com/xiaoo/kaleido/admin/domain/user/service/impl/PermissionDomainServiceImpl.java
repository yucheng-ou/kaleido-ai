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
        // 1. 验证权限编码唯一性
        if (permissionRepository.existsByCode(code)) {
            throw AdminException.of(AdminErrorCode.PERMISSION_CODE_EXIST);
        }

        // 2. 验证父权限是否存在（如果提供了parentId）
        if (parentId != null && !parentId.trim().isEmpty()) {
            PermissionAggregate parentPermission = permissionRepository.findById(parentId);
            if (parentPermission == null) {
                throw AdminException.of(AdminErrorCode.PARENT_PERMISSION_NOT_EXIST);
            }
        }

        // 3. 创建权限
        return PermissionAggregate.create(
                code, name, type, parentId, sort, icon, path, component, isHidden
        );
    }

    @Override
    public PermissionAggregate updatePermission(String permissionId, String name, PermissionType type,
                                                String parentId, Integer sort, String icon,
                                                String path, String component, Boolean isHidden) {
        // 1. 获取权限
        PermissionAggregate permission = findByIdOrThrow(permissionId);

        // 2. 验证父权限是否存在（如果父权限ID有变化且提供了有效的parentId）
        if (parentId != null && !parentId.equals(permission.getParentId()) && !parentId.trim().isEmpty()) {
            PermissionAggregate parentPermission = permissionRepository.findById(parentId);
            if (parentPermission == null) {
                throw AdminException.of(AdminErrorCode.PARENT_PERMISSION_NOT_EXIST);
            }
        }

        // 3. 更新权限信息
        permission.updateInfo(name, type, parentId, sort, icon, path, component, isHidden);

        return permission;
    }

    @Override
    public PermissionAggregate updatePermissionCode(String permissionId, String code) {
        // 1. 获取权限
        PermissionAggregate permission = findByIdOrThrow(permissionId);

        // 2. 验证新编码是否可用
        if (!permission.getCode().equals(code) && permissionRepository.existsByCode(code)) {
            throw AdminException.of(AdminErrorCode.PERMISSION_CODE_EXIST);
        }

        // 3. 更新权限编码
        permission.updateCode(code);

        return permission;
    }

    @Override
    public PermissionAggregate deletePermission(String permissionId) {
        // 1. 获取权限
        PermissionAggregate permission = findByIdOrThrow(permissionId);

        // 2. 检查是否有子权限
        long childCount = permissionRepository.countByParentId(permissionId);
        if (childCount > 0) {
            throw AdminException.of(AdminErrorCode.PERMISSION_HAS_CHILDREN);
        }

        return permission;
    }

    @Override
    public PermissionAggregate findByIdOrThrow(String permissionId) {
        PermissionAggregate permission = permissionRepository.findById(permissionId);
        if (permission == null) {
            throw AdminException.of(AdminErrorCode.PERMISSION_HAS_CHILDREN);
        }
        return permission;
    }

    @Override
    public PermissionAggregate findByCodeOrThrow(String code) {
        PermissionAggregate permission = permissionRepository.findByCode(code);
        if (permission == null) {
            throw AdminException.of(AdminErrorCode.PERMISSION_NOT_EXIST);
        }
        return permission;
    }

    @Override
    public List<PermissionAggregate> findByParentId(String parentId) {
        return permissionRepository.findByParentId(parentId);
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
