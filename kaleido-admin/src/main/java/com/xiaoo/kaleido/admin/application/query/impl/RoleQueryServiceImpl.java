package com.xiaoo.kaleido.admin.application.query.impl;

import com.xiaoo.kaleido.admin.application.query.RoleQueryService;
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
    public List<RoleInfoResponse> getRoleList() {
        log.debug("获取角色列表");

        List<RoleAggregate> aggregateList = roleRepository.findAll();
        return aggregateList.stream()
                .map(roleConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        log.debug("检查角色编码是否存在，code={}", code);

        return roleRepository.existsByCode(code);
    }

    @Override
    public Set<String> getRoleCodesId(String adminId) {
        log.debug("根据管理员ID查询角色编码，adminId={}", adminId);

        // 直接调用仓储层的新接口查询角色编码
        List<String> roleCodes = roleRepository.findCodesByAdminId(adminId);
        return new HashSet<>(roleCodes);
    }
}
