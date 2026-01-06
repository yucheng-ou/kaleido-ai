package com.xiaoo.kaleido.admin.application.query.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.admin.application.convertor.AdminUserConvertor;
import com.xiaoo.kaleido.admin.application.query.AdminUserQueryService;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminUserRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminUserAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.AdminUserDomainService;
import com.xiaoo.kaleido.api.admin.auth.request.AdminUserPageQueryReq;
import com.xiaoo.kaleido.api.admin.auth.response.AdminUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理员查询服务实现
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserQueryServiceImpl implements AdminUserQueryService {

    private final IAdminUserRepository adminUserRepository;
    private final AdminUserDomainService adminUserDomainService;
    private final AdminUserConvertor adminUserConvertor;

    @Override
    public AdminUserInfoResponse findById(String adminUserId) {
        log.debug("根据ID查询管理员信息，adminUserId={}", adminUserId);
        
        AdminUserAggregate adminUserAggregate = adminUserDomainService.findByIdOrThrow(adminUserId);
        return adminUserConvertor.toResponse(adminUserAggregate);
    }

    @Override
    public AdminUserInfoResponse findByUsername(String username) {
        log.debug("根据账号查询管理员信息，username={}", username);
        
        AdminUserAggregate adminUserAggregate = adminUserDomainService.findByUsernameOrThrow(username);
        return adminUserConvertor.toResponse(adminUserAggregate);
    }

    @Override
    public AdminUserInfoResponse findByMobile(String mobile) {
        log.debug("根据手机号查询管理员信息，mobile={}", mobile);
        
        AdminUserAggregate adminUserAggregate = adminUserDomainService.findByMobile(mobile);
        if (adminUserAggregate == null) {
            return null;
        }
        return adminUserConvertor.toResponse(adminUserAggregate);
    }


    @Override
    public List<AdminUserInfoResponse> findNormalAdminUsers() {
        log.debug("查询所有正常的管理员");
        
        List<AdminUserAggregate> adminUserAggregates = adminUserDomainService.findNormalAdminUsers();
        return adminUserAggregates.stream()
                .map(adminUserConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminUserInfoResponse> findByRoleId(String roleId) {
        log.debug("根据角色ID查询拥有该角色的管理员，roleId={}", roleId);
        
        List<AdminUserAggregate> adminUserAggregates = adminUserDomainService.findByRoleId(roleId);
        return adminUserAggregates.stream()
                .map(adminUserConvertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<AdminUserInfoResponse> pageQuery(AdminUserPageQueryReq pageQueryReq) {
        log.debug("分页查询管理员，pageQueryReq={}", pageQueryReq);
        
        // 启动PageHelper分页
        PageHelper.startPage(pageQueryReq.getPageNum(), pageQueryReq.getPageSize());
        
        // 调用仓储分页查询
        List<AdminUserAggregate> aggregates = adminUserRepository.pageQuery(pageQueryReq);
        
        // 转换为响应对象
        List<AdminUserInfoResponse> responseList = aggregates.stream()
                .map(adminUserConvertor::toResponse)
                .collect(Collectors.toList());
        
        // 构建PageInfo分页响应
        return new PageInfo<>(responseList);
    }

    @Override
    public boolean existsByUsername(String username) {
        log.debug("检查管理员账号是否存在，username={}", username);
        
        return adminUserDomainService.existsByUsername(username);
    }

    @Override
    public boolean existsByMobile(String mobile) {
        log.debug("检查手机号是否存在，mobile={}", mobile);
        
        return adminUserDomainService.existsByMobile(mobile);
    }

    @Override
    public List<String> getPermissionsByAdminUserId(String adminUserId) {
        log.debug("获取管理员的所有权限，adminUserId={}", adminUserId);
        
        return adminUserDomainService.getPermissionsByAdminUserId(adminUserId);
    }

    @Override
    public boolean hasPermission(String adminUserId, String permissionId) {
        log.debug("验证管理员是否有某个权限，adminUserId={}, permissionId={}", adminUserId, permissionId);
        
        return adminUserDomainService.hasPermission(adminUserId, permissionId);
    }
}
