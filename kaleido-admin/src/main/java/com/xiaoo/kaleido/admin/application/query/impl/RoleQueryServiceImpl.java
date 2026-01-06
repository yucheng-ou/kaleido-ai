package com.xiaoo.kaleido.admin.application.query.impl;

import com.xiaoo.kaleido.admin.application.query.RoleQueryService;
import com.xiaoo.kaleido.admin.application.convertor.RoleConvertor;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.api.admin.auth.request.RolePageQueryReq;
import com.xiaoo.kaleido.api.admin.auth.response.RoleInfoResponse;
import com.xiaoo.kaleido.api.admin.auth.response.RoleTreeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色查询服务实现
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleQueryServiceImpl implements RoleQueryService {

    private final IRoleRepository roleRepository;
    private final RoleConvertor roleConvertor;

    @Override
    public RoleInfoResponse findById(String roleId) {
        log.debug("根据ID查询角色，roleId={}", roleId);
        
        return roleRepository.findById(roleId)
                .map(roleConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public RoleInfoResponse findByCode(String code) {
        log.debug("根据编码查询角色，code={}", code);
        
        return roleRepository.findByCode(code)
                .map(roleConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public List<RoleInfoResponse> findEnabledRoles() {
        log.debug("查询启用的角色列表");
        
        List<RoleAggregate> aggregateList = roleRepository.findEnabledRoles();
        return aggregateList.stream()
                .map(roleConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleInfoResponse> findSystemRoles() {
        log.debug("查询系统角色列表");
        
        List<RoleAggregate> aggregateList = roleRepository.findSystemRoles();
        return aggregateList.stream()
                .map(roleConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleInfoResponse> findByPermissionId(String permissionId) {
        log.debug("根据权限ID查询角色，permissionId={}", permissionId);
        
        List<RoleAggregate> aggregateList = roleRepository.findByPermissionId(permissionId);
        return aggregateList.stream()
                .map(roleConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleTreeResponse> getRoleTree() {
        log.debug("获取角色树");
        
        List<RoleAggregate> aggregateList = roleRepository.getRoleTree();
        return aggregateList.stream()
                .map(roleConvertor::toTreeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        log.debug("检查角色编码是否存在，code={}", code);
        
        return roleRepository.existsByCode(code);
    }

    @Override
    public boolean isValidRole(String roleId) {
        log.debug("检查角色是否存在且启用，roleId={}", roleId);
        
        return roleRepository.findById(roleId)
                .map(role -> role.getCode() != null && role.isEnabled())
                .orElse(false);
    }

    @Override
    public boolean hasPermission(String roleId, String permissionId) {
        log.debug("验证角色是否拥有权限，roleId={}, permissionId={}", roleId, permissionId);
        
        return roleRepository.findById(roleId)
                .map(role -> role.getPermissionIds().contains(permissionId))
                .orElse(false);
    }

    @Override
    public Set<String> getRoleCodesId(String adminId) {
        log.debug("根据管理员ID查询角色编码，adminId={}", adminId);
        
        // 直接调用仓储层的新接口查询角色编码
        List<String> roleCodes = roleRepository.findCodesByAdminId(adminId);
        return new HashSet<>(roleCodes);
    }
}
