package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.admin.auth.command.AddPermissionCommand;
import com.xiaoo.kaleido.api.admin.auth.command.DeletePermissionCommand;
import com.xiaoo.kaleido.api.admin.auth.command.UpdatePermissionCommand;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IPermissionRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.PermissionAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.PermissionDomainService;
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
public class PermissionCommandService {

    private final IPermissionRepository permissionRepository;
    private final PermissionDomainService permissionDomainService;

    /**
     * 创建权限
     *
     * @param command 创建权限命令
     * @return 权限ID
     */
    @Transactional
    public String createPermission(AddPermissionCommand command) {
        // 调用领域服务创建权限
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

        // 保存权限
        permissionRepository.save(permissionAggregate);

        log.info("权限创建成功，权限ID: {}, 权限编码: {}", 
                permissionAggregate.getId(), command.getCode());
        return permissionAggregate.getId();
    }

    /**
     * 更新权限信息
     *
     * @param command 更新权限信息命令
     */
    @Transactional
    public void updatePermission(UpdatePermissionCommand command) {
        // 调用领域服务更新权限信息
        PermissionAggregate permissionAggregate = permissionDomainService.updatePermission(
                command.getPermissionId(),
                command.getName(),
                command.getType(),
                command.getParentId(),
                command.getSort(),
                command.getIcon(),
                command.getPath(),
                command.getComponent(),
                command.getIsHidden()
        );

        // 保存权限
        permissionRepository.save(permissionAggregate);

        log.info("权限信息更新成功，权限ID: {}", command.getPermissionId());
    }

    /**
     * 更新权限编码
     *
     * @param permissionId 权限ID
     * @param code 新的权限编码
     */
    @Transactional
    public void updatePermissionCode(String permissionId, String code) {
        // 调用领域服务更新权限编码
        PermissionAggregate permissionAggregate = permissionDomainService.updatePermissionCode(permissionId, code);

        // 保存权限
        permissionRepository.save(permissionAggregate);

        log.info("权限编码更新成功，权限ID: {}, 新编码: {}", permissionId, code);
    }

    /**
     * 删除权限
     *
     * @param command 删除权限命令
     */
    @Transactional
    public void deletePermission(DeletePermissionCommand command) {
        // 调用领域服务删除权限
        permissionDomainService.deletePermission(command.getPermissionId());

        log.info("权限删除成功，权限ID: {}", command.getPermissionId());
    }

    /**
     * 检查权限编码是否可用
     *
     * @param code 权限编码
     * @return 是否可用
     */
    public boolean isCodeAvailable(String code) {
        return permissionDomainService.isCodeAvailable(code);
    }

    /**
     * 检查权限是否存在且有效
     *
     * @param permissionId 权限ID
     * @return 是否存在且有效
     */
    public boolean isValidPermission(String permissionId) {
        return permissionDomainService.isValidPermission(permissionId);
    }
}
