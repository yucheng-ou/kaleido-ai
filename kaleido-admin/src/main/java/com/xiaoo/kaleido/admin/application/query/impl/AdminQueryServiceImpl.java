package com.xiaoo.kaleido.admin.application.query.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.admin.application.convertor.AdminConvertor;
import com.xiaoo.kaleido.admin.application.query.IAdminQueryService;
import com.xiaoo.kaleido.admin.application.query.PermissionQueryService;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.IAdminDomainService;
import com.xiaoo.kaleido.api.admin.user.request.AdminPageQueryReq;
import com.xiaoo.kaleido.api.admin.user.response.AdminInfoResponse;
import com.xiaoo.kaleido.api.admin.user.response.PermissionInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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
    private final PermissionQueryService permissionQueryService;

    @Override
    public AdminInfoResponse findById(String adminId) {
        log.debug("根据ID查询管理员信息，adminId={}", adminId);
        
        // 1. 调用领域服务查询管理员聚合根
        AdminAggregate adminAggregate = adminDomainService.findByIdOrThrow(adminId);
        
        // 2. 转换为响应对象
        return adminConvertor.toResponse(adminAggregate);
    }

    @Override
    public AdminInfoResponse findByMobile(String mobile) {
        log.debug("根据手机号查询管理员信息，mobile={}", mobile);
        
        // 1. 调用领域服务根据手机号查询管理员聚合根
        AdminAggregate adminAggregate = adminDomainService.findByMobile(mobile);
        
        // 2. 如果不存在则返回null
        if (adminAggregate == null) {
            return null;
        }
        
        // 3. 转换为响应对象
        return adminConvertor.toResponse(adminAggregate);
    }

    @Override
    public PageInfo<AdminInfoResponse> pageQuery(AdminPageQueryReq pageQueryReq) {
        log.debug("分页查询管理员，pageQueryReq={}", pageQueryReq);
        
        // 1. 启动PageHelper分页
        PageHelper.startPage(pageQueryReq.getPageNum(), pageQueryReq.getPageSize());
        
        // 2. 调用仓储分页查询
        List<AdminAggregate> aggregates = adminRepository.pageQuery(pageQueryReq);
        
        // 3. 转换为响应对象
        List<AdminInfoResponse> responseList = aggregates.stream()
                .map(adminConvertor::toResponse)
                .collect(Collectors.toList());
        
        // 4. 构建PageInfo分页响应
        return new PageInfo<>(responseList);
    }

    @Override
    public List<String> getPermissionsByAdminId(String adminId) {
        log.debug("获取管理员的所有权限，adminId={}", adminId);
        
        // 调用领域服务获取管理员的所有权限ID
        return adminDomainService.getPermissionsByAdminId(adminId);
    }

    @Override
    public List<PermissionInfoResponse> getDirectoryAndMenuTreeByAdminId(String adminId) {
        log.debug("获取管理员的目录和菜单树，adminId={}", adminId);
        
        // 1. 获取管理员的所有权限ID
        List<String> permissionIds = adminDomainService.getPermissionsByAdminId(adminId);
        if (CollectionUtils.isEmpty(permissionIds)) {
            return Collections.emptyList();
        }
        
        // 2. 查询权限详情并过滤掉按钮类型
        List<PermissionInfoResponse> allPermissions = new ArrayList<>();
        for (String permissionId : permissionIds) {
            PermissionInfoResponse permission = permissionQueryService.findById(permissionId);
            if (permission != null && !permission.getType().isButton()) {
                allPermissions.add(permission);
            }
        }
        
        // 3. 构建树形结构
        return buildPermissionTree(allPermissions);
    }

    private List<PermissionInfoResponse> buildPermissionTree(List<PermissionInfoResponse> permissions) {
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptyList();
        }
        
        // 1. 创建ID到权限的映射
        Map<String, PermissionInfoResponse> permissionMap = new HashMap<>();
        for (PermissionInfoResponse permission : permissions) {
            permissionMap.put(permission.getPermissionId(), permission);
        }
        
        // 2. 构建树形结构
        List<PermissionInfoResponse> tree = new ArrayList<>();
        for (PermissionInfoResponse permission : permissions) {
            String parentId = permission.getParentId();
            if (parentId == null || !permissionMap.containsKey(parentId)) {
                // 没有父节点或父节点不在当前权限列表中，作为根节点
                tree.add(permission);
            } else {
                // 添加到父节点的children中
                PermissionInfoResponse parent = permissionMap.get(parentId);
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(permission);
            }
        }
        
        // 3. 对每个节点的children按sort排序
        for (PermissionInfoResponse permission : permissions) {
            if (permission.getChildren() != null && !permission.getChildren().isEmpty()) {
                permission.getChildren().sort(Comparator.comparingInt(p -> p.getSort() != null ? p.getSort() : 0));
            }
        }
        
        // 4. 对根节点按sort排序
        tree.sort(Comparator.comparingInt(p -> p.getSort() != null ? p.getSort() : 0));
        
        return tree;
    }

}
