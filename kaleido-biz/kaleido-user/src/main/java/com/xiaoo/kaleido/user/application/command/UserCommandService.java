package com.xiaoo.kaleido.user.application.command;

import com.xiaoo.kaleido.api.user.command.RegisterUserCommand;
import com.xiaoo.kaleido.api.user.command.ChangeNickNameCommand;
import com.xiaoo.kaleido.user.domain.adapter.repository.UserRepository;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.user.domain.service.IUserDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户命令服务（应用层）
 * 负责用户相关的写操作编排
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final IUserDomainService userDomainService;

    /**
     * 创建用户
     *
     * @param command 创建用户命令
     * @return 用户ID
     */
    public String createUser(RegisterUserCommand command) {

        // 调用领域服务创建用户聚合根
        UserAggregate userAggregate = userDomainService.createUser(
                command.getTelephone(),
                command.getPassword(),
                command.getInviterCode()
        );

        // 保存用户
        userRepository.save(userAggregate);

        log.info("用户创建成功，用户ID: {}, 手机号: {}", userAggregate.getId(), command.getTelephone());
        return userAggregate.getId();
    }

    /**
     * 修改昵称
     *
     * @param command 修改昵称命令
     */
    public void changeNickName(ChangeNickNameCommand command) {

        // 调用领域服务修改昵称
        UserAggregate userAggregate = userDomainService.changeNickName(
                command.getUserId(),
                command.getNickName()
        );

        // 保存用户
        userRepository.update(userAggregate);

        log.info("用户昵称修改成功，用户ID: {}, 新昵称: {}", command.getUserId(), command.getNickName());
    }

    /**
     * 冻结用户
     *
     * @param userId 用户id
     */
    public void freezeUser(String userId) {

        // 调用领域服务冻结用户
        UserAggregate userAggregate = userDomainService.freezeUser(userId);

        // 保存用户
        userRepository.update(userAggregate);

        log.info("用户冻结成功，用户ID: {}", userId);
    }

    /**
     * 解冻用户
     *
     * @param userId 用户id
     */
    public void unfreezeUser(String userId) {

        // 调用领域服务解冻用户
        UserAggregate userAggregate = userDomainService.unfreezeUser(userId);

        // 保存用户
        userRepository.update(userAggregate);

        log.info("用户解冻成功，用户ID: {}", userId);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    public void deleteUser(String userId) {

        // 调用领域服务删除用户
        UserAggregate userAggregate = userDomainService.deleteUser(userId);

        // 保存用户
        userRepository.save(userAggregate);

        log.info("用户删除成功，用户ID: {}", userId);
    }

    /**
     * 更新用户头像
     *
     * @param userId    用户ID
     * @param avatarUrl 头像URL
     */
    public void updateAvatar(String userId, String avatarUrl) {

        // 调用领域服务更新头像
        UserAggregate userAggregate = userDomainService.updateAvatar(userId, avatarUrl);

        // 保存用户
        userRepository.save(userAggregate);

        log.info("用户头像更新成功，用户ID: {}", userId);
    }

    /**
     * 记录用户登录
     * - 更新用户最后登录时间
     * - 记录登录日志
     *
     * @param userId 用户ID
     */
    @Transactional
    public void login(String userId) {
        // 调用领域服务更新最后登录时间
        UserAggregate userAggregate = userDomainService.login(userId);

        // 保存更新后的用户
        userRepository.update(userAggregate);

        log.info("用户登录记录成功，userId={}", userId);
    }

    /**
     * 记录用户登出
     * - 记录登出日志
     *
     * @param userId 用户ID
     */
    public void logout(String userId) {
        // 调用领域服务登出
        UserAggregate userAggregate = userDomainService.logout(userId);

        // 保存更新后的用户
        userRepository.update(userAggregate);

        // 仅记录登出日志，无状态变更
        log.info("用户登出记录成功，userId={}", userId);
    }
}
