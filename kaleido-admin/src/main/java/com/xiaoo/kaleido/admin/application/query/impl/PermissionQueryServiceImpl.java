package com.xiaoo.kaleido.admin.application.query.impl;

import com.xiaoo.kaleido.admin.application.query.IPermissionQueryService;
import com.xiaoo.kaleido.admin.application.convertor.PermissionConvertor;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.api.admin.user.response.PermissionInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class PermissionQueryServiceImpl implements IPermissionQueryService {

    private final IPermissionRepository permissionRepository;
    private final PermissionConvertor permissionConvertor;

    @Override
    public PermissionInfoResponse findById(String permissionId) {
        // 1. 调用仓储层查询权限聚合根
        PermissionAggregate permission = permissionRepository.findById(permissionId);
        
        // 2. 如果存在则转换为响应对象，否则返回null
        if (permission != null) {
            return permissionConvertor.toResponse(permission);
        }
        return null;
    }

    @Override
    public PermissionInfoResponse findByCode(String code) {
        // 1. 调用仓储层根据编码查询权限聚合根
        PermissionAggregate permission = permissionRepository.findByCode(code);
        
        // 2. 如果存在则转换为响应对象，否则返回null
        if (permission != null) {
            return permissionConvertor.toResponse(permission);
        }
        return null;
    }

    @Override
    public List<PermissionInfoResponse> findByParentId(String parentId) {
        // 1. 调用仓储层根据父权限ID查询子权限聚合根列表
        List<PermissionAggregate> aggregateList = permissionRepository.findByParentId(parentId);
        
        // 2. 转换为响应对象列表
        return aggregateList.stream()
                .map(permissionConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionInfoResponse> getPermissionTree() {
        // 1. 调用仓储层获取权限树聚合根列表
        List<PermissionAggregate> aggregateList = permissionRepository.getPermissionTree();
        
        // 2. 转换为响应对象列表
        return aggregateList.stream()
                .map(permissionConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        // 调用仓储层检查权限编码是否存在
        return permissionRepository.existsByCode(code);
    }

    @Override
    public Set<String> getPermCodesById(String adminId) {
        // 直接调用仓储层的新接口查询权限编码
        List<String> permCodes = permissionRepository.findCodesByAdminId(adminId);
        return new HashSet<>(permCodes);
    }
}
