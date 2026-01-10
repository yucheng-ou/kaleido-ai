package com.xiaoo.kaleido.admin.application.query.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.admin.application.convertor.AdminConvertor;
import com.xiaoo.kaleido.admin.application.query.IAdminQueryService;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.IAdminDomainService;
import com.xiaoo.kaleido.api.admin.user.request.AdminPageQueryReq;
import com.xiaoo.kaleido.api.admin.user.response.AdminInfoResponse;
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
public class AdminQueryServiceImpl implements IAdminQueryService {

    private final IAdminRepository adminRepository;
    private final IAdminDomainService adminDomainService;
    private final AdminConvertor adminConvertor;

    @Override
    public AdminInfoResponse findById(String adminId) {
        log.debug("根据ID查询管理员信息，adminId={}", adminId);
        
        AdminAggregate adminAggregate = adminDomainService.findByIdOrThrow(adminId);
        return adminConvertor.toResponse(adminAggregate);
    }

    @Override
    public AdminInfoResponse findByMobile(String mobile) {
        log.debug("根据手机号查询管理员信息，mobile={}", mobile);
        
        AdminAggregate adminAggregate = adminDomainService.findByMobile(mobile);
        if (adminAggregate == null) {
            return null;
        }
        return adminConvertor.toResponse(adminAggregate);
    }

    @Override
    public PageInfo<AdminInfoResponse> pageQuery(AdminPageQueryReq pageQueryReq) {
        log.debug("分页查询管理员，pageQueryReq={}", pageQueryReq);
        
        // 启动PageHelper分页
        PageHelper.startPage(pageQueryReq.getPageNum(), pageQueryReq.getPageSize());
        
        // 调用仓储分页查询
        List<AdminAggregate> aggregates = adminRepository.pageQuery(pageQueryReq);
        
        // 转换为响应对象
        List<AdminInfoResponse> responseList = aggregates.stream()
                .map(adminConvertor::toResponse)
                .collect(Collectors.toList());
        
        // 构建PageInfo分页响应
        return new PageInfo<>(responseList);
    }


    @Override
    public List<String> getPermissionsByAdminId(String adminId) {
        log.debug("获取管理员的所有权限，adminId={}", adminId);
        
        return adminDomainService.getPermissionsByAdminId(adminId);
    }

}
