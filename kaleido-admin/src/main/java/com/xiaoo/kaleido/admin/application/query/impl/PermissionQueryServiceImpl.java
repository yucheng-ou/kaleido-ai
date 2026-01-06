package com.xiaoo.kaleido.admin.application.query.impl;

import com.xiaoo.kaleido.admin.application.query.PermissionQueryService;
import com.xiaoo.kaleido.admin.application.convertor.PermissionConvertor;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.api.admin.auth.response.PermissionInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限查询服务实现
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionQueryServiceImpl implements PermissionQueryService {

    private final IPermissionRepository permissionRepository;
    private final IRoleRepository roleRepository;
    private final PermissionConvertor permissionConvertor;

    @Override
    public PermissionInfoResponse findById(String permissionId) {
        log.debug("根据ID查询权限，permissionId={}", permissionId);
        
        return permissionRepository.findById(permissionId)
                .map(permissionConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public PermissionInfoResponse findByCode(String code) {
        log.debug("根据编码查询权限，code={}", code);
        
        return permissionRepository.findByCode(code)
                .map(permissionConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public List<PermissionInfoResponse> findByParentId(String parentId) {
        log.debug("根据父权限ID查询子权限，parentId={}", parentId);
        
        List<PermissionAggregate> aggregateList = permissionRepository.findByParentId(parentId);
        return aggregateList.stream()
                .map(permissionConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionInfoResponse> findRootPermissions() {
        log.debug("查询根权限列表");
        
        List<PermissionAggregate> aggregateList = permissionRepository.findRootPermissions();
        return aggregateList.stream()
                .map(permissionConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionInfoResponse> getPermissionTree() {
        log.debug("获取权限树");
        
        List<PermissionAggregate> aggregateList = permissionRepository.getPermissionTree();
        return aggregateList.stream()
                .map(permissionConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        log.debug("检查权限编码是否存在，code={}", code);
        
        return permissionRepository.existsByCode(code);
    }

    @Override
    public Set<String> getPermCodesById(String adminId) {
        log.debug("根据管理员ID查询权限编码，adminId={}", adminId);
        
        // 1. 先查询管理员的角色编码
        List<String> roleCodes = roleRepository.findCodesByAdminId(adminId);
        if (CollectionUtils.isEmpty(roleCodes)) {
            return Set.of();
        }
        
        // 2. 查询角色ID（需要根据编码查询ID）
        List<RoleAggregate> roles = roleRepository.findAllByCode(roleCodes);
        List<String> roleIds = roles.stream()
                .filter(RoleAggregate::isEnabled)
                .map(RoleAggregate::getId)
                .collect(Collectors.toList());
        
        if (CollectionUtils.isEmpty(roleIds)) {
            return Set.of();
        }
        
        // 3. 直接调用仓储层的新接口查询权限编码
        List<String> permCodes = permissionRepository.findCodesByRoleIds(roleIds);
        return new HashSet<>(permCodes);
    }
}
