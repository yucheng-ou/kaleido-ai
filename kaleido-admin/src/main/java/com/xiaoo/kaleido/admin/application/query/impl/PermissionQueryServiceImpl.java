package com.xiaoo.kaleido.admin.application.query.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.admin.application.query.PermissionQueryService;
import com.xiaoo.kaleido.admin.application.convertor.PermissionConvertor;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.api.admin.auth.request.PermissionPageQueryReq;
import com.xiaoo.kaleido.api.admin.auth.response.PermissionInfoResponse;
import com.xiaoo.kaleido.base.response.PageResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public PageResp<PermissionInfoResponse> pageQuery(PermissionPageQueryReq req) {
        log.debug("分页查询权限，条件: {}", req);
        
        // 使用PageHelper分页
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        
        // 执行查询
        List<PermissionAggregate> aggregateList = permissionRepository.findByCondition(req);
        
        // 转换为PageInfo获取分页信息
        PageInfo<PermissionAggregate> pageInfo = new PageInfo<>(aggregateList);
        
        // 转换为响应列表
        List<PermissionInfoResponse> responseList = aggregateList.stream()
                .map(permissionConvertor::toResponse)
                .collect(Collectors.toList());
        
        // 构建分页响应
        return PageResp.success(
            responseList,
            pageInfo.getTotal(),
            pageInfo.getPageNum(),
            pageInfo.getPageSize()
        );
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
                .map(permissionConvertor::toTreeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code) {
        log.debug("检查权限编码是否存在，code={}", code);
        
        return permissionRepository.existsByCode(code);
    }
}
