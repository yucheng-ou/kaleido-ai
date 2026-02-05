package com.xiaoo.kaleido.admin.application.command.impl;

import com.xiaoo.kaleido.admin.application.command.IAdminCommandService;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;
import com.xiaoo.kaleido.admin.domain.user.service.IAdminDomainService;
import com.xiaoo.kaleido.admin.domain.user.adapter.event.AuthChangeEvent;
import com.xiaoo.kaleido.mq.event.EventPublisher;
import com.xiaoo.kaleido.api.admin.user.command.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 管理员命令服务（应用层）
 * 负责管理员相关的写操作编排
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCommandService implements IAdminCommandService {

    private final IAdminRepository adminRepository;
    private final IAdminDomainService adminDomainService;
    private final EventPublisher eventPublisher;
    private final AuthChangeEvent authChangeEvent;

    @Override
    public String createAdmin(RegisterAdminCommand command) {
        // 1. 调用领域服务创建管理员
        AdminAggregate adminAggregate = adminDomainService.createAdmin(command.getMobile());

        // 2. 保存管理员
        adminRepository.save(adminAggregate);

        log.info("管理员创建成功，管理员ID: {}, 手机号: {}", adminAggregate.getId(), command.getMobile());
        return adminAggregate.getId();
    }

    @Override
    public void updateAdmin(String adminId, UpdateAdminCommand command) {
        // 1. 调用领域服务更新管理员信息
        AdminAggregate adminAggregate = adminDomainService.updateAdmin(adminId, command.getRealName(), command.getMobile());

        // 2. 保存管理员
        adminRepository.update(adminAggregate);

        log.info("管理员信息更新成功，管理员ID: {}", adminId);
    }

    @Override
    public void enableAdmin(String adminId) {
        // 1. 调用领域服务启用管理员
        AdminAggregate adminAggregate = adminDomainService.enableAdmin(adminId);

        // 2. 更新管理员状态
        adminRepository.update(adminAggregate);

        log.info("管理员启用成功，管理员ID: {}", adminId);
    }

    @Override
    public void freezeAdmin(String adminId) {
        // 1. 调用领域服务冻结管理员
        AdminAggregate adminAggregate = adminDomainService.freezeAdmin(adminId);

        // 2. 更新管理员状态
        adminRepository.update(adminAggregate);

        log.info("管理员冻结成功，管理员ID: {}", adminId);
    }

    @Override
    public void assignRoles(String adminId, AssignRolesToAdminCommand command) {
        // 1. 调用领域服务分配角色
        AdminAggregate adminAggregate = adminDomainService.assignRoles(adminId, command.getRoleIds());

        // 2. 调用仓储服务分配角色
        adminRepository.assignRoles(adminAggregate);

        // 3. 发布权限变更事件
        AuthChangeEvent.AuthChangeMessage message = AuthChangeEvent.AuthChangeMessage.builder()
                .adminId(adminId)
                .build();
        eventPublisher.publish(authChangeEvent.topic(), authChangeEvent.buildEventMessage(message));

        log.info("角色分配成功，管理员ID: {}, 角色数量: {}", adminId, command.getRoleIds().size());
    }

    @Override
    public void login(String adminId) {
        // 1. 调用领域服务处理登录逻辑
        AdminAggregate admin = adminDomainService.login(adminId);

        // 2. 更新管理员信息
        adminRepository.update(admin);

        log.info("管理员登录成功（生成token），管理员ID: {}", adminId);
    }
}
