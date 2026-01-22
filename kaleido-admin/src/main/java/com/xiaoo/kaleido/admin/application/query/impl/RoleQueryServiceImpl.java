package com.xiaoo.kaleido.admin.application.query.impl;

import com.xiaoo.kaleido.admin.application.query.IRoleQueryService;
import com.xiaoo.kaleido.admin.application.convertor.RoleConvertor;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.api.admin.user.response.RoleInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class RoleQueryServiceImpl implements IRoleQueryService {

    private final IRoleRepository roleRepository;
    private final RoleConvertor roleConvertor;

    @Override
    public RoleInfoResponse findById(String roleId) {
        // 1. 调用仓储层查询角色聚合根
        RoleAggregate role = roleRepository.findById(roleId);
        
        // 2. 如果存在则转换为响应对象，否则返回null
        if (role != null) {
            return roleConvertor.toResponse(role);
        }
        return null;
    }

    @Override
    public RoleInfoResponse findByCode(String code) {
        // 1. 调用仓储层根据编码查询角色聚合根
        RoleAggregate role = roleRepository.findByCode(code);
        
        // 2. 如果存在则转换为响应对象，否则返回null
        if (role != null) {
            return roleConvertor.toResponse(role);
        }
        return null;
    }

    @Override
    public List<RoleInfoResponse> getRoleList() {
        // 1. 调用仓储层查询所有角色聚合根列表
        List<RoleAggregate> aggregateList = roleRepository.findAll();
        
        // 2. 转换为响应对象列表
        return aggregateList.stream()
                .map(roleConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        // 调用仓储层检查角色编码是否存在
        return roleRepository.existsByCode(code);
    }

    @Override
    public Set<String> getRoleCodesId(String adminId) {
        // 1. 调用仓储层查询管理员关联的角色编码
        List<String> roleCodes = roleRepository.findCodesByAdminId(adminId);
        
        // 2. 转换为集合返回
        return new HashSet<>(roleCodes);
    }
}
