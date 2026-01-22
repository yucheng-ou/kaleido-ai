package com.xiaoo.kaleido.admin.application.command.impl;

import com.xiaoo.kaleido.admin.application.command.IPermissionCommandService;
import com.xiaoo.kaleido.api.admin.user.command.AddPermissionCommand;
import com.xiaoo.kaleido.api.admin.user.command.UpdatePermissionCommand;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.IPermissionDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限命令服务（应用层）
 * 负责权限相关的写操作编排
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionCommandService implements IPermissionCommandService {

    private final IPermissionRepository permissionRepository;
    private final IPermissionDomainService permissionDomainService;

    @Transactional
    public String createPermission(AddPermissionCommand command) {
        // 1. 调用领域服务创建权限
        PermissionAggregate permissionAggregate = permissionDomainService.createPermission(
                command.getCode(),
                command.getName(),
                command.getType(),
                command.getParentId(),
                command.getSort(),
                command.getIcon(),
                command.getPath(),
                command.getComponent(),
                command.getIsHidden()
        );

        // 2. 保存权限
        permissionRepository.save(permissionAggregate);

        log.info("权限创建成功，权限ID: {}, 权限编码: {}", 
                permissionAggregate.getId(), command.getCode());
        return permissionAggregate.getId();
    }

    public void updatePermission(String permissionId, UpdatePermissionCommand command) {
        // 1. 调用领域服务更新权限信息
        PermissionAggregate permissionAggregate = permissionDomainService.updatePermission(
                permissionId,
                command.getName(),
                command.getType(),
                command.getParentId(),
                command.getSort(),
                command.getIcon(),
                command.getPath(),
                command.getComponent(),
                command.getIsHidden()
        );

        // 2. 保存权限
        permissionRepository.update(permissionAggregate);

        log.info("权限信息更新成功，权限ID: {}", permissionId);
    }

    public void updatePermissionCode(String permissionId, String code) {
        // 1. 调用领域服务更新权限编码
        PermissionAggregate permissionAggregate = permissionDomainService.updatePermissionCode(permissionId, code);

        // 2. 更新权限
        permissionRepository.update(permissionAggregate);

        log.info("权限编码更新成功，权限ID: {}, 新编码: {}", permissionId, code);
    }

    @Transactional
    public void deletePermission(String permissionId) {
        // 1. 调用领域服务获取要删除的权限（验证权限是否存在且无子权限）
        PermissionAggregate permissionAggregate = permissionDomainService.deletePermission(permissionId);

        // 2. 调用仓储层删除权限
        permissionRepository.deleteById(permissionAggregate.getId());

        log.info("权限删除成功，权限ID: {}", permissionId);
    }

    public boolean isCodeAvailable(String code) {
        return permissionDomainService.isCodeAvailable(code);
    }

    public boolean isValidPermission(String permissionId) {
        return permissionDomainService.isValidPermission(permissionId);
    }
}
