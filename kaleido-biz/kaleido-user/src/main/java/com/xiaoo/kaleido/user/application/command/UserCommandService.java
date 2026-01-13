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
 * <p>
 * 负责用户相关的写操作编排，协调领域服务和仓储完成业务操作
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
     * <p>
     * 处理用户注册请求，创建新用户并保存到数据库
     *
     * @param command 创建用户命令，包含手机号和邀请码等信息
     * @return 用户ID，新创建用户的唯一标识
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 当手机号已存在或邀请码无效时抛出
     */
    public String createUser(RegisterUserCommand command) {
        // 1.调用领域服务创建用户聚合根
        UserAggregate userAggregate = userDomainService.createUser(
                command.getTelephone(),
                command.getInviterCode()
        );

        // 2.保存用户到数据库
        userRepository.save(userAggregate);

        // 3.记录操作日志
        log.info("用户创建成功，用户ID: {}, 手机号: {}", userAggregate.getId(), command.getTelephone());

        return userAggregate.getId();
    }

    /**
     * 修改昵称
     * <p>
     * 处理用户昵称修改请求，更新用户昵称并记录操作流水
     *
     * @param command 修改昵称命令，包含用户ID和新昵称
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 当用户不存在或状态不允许修改时抛出
     */
    public void changeNickName(ChangeNickNameCommand command) {
        // 1.调用领域服务修改昵称
        UserAggregate userAggregate = userDomainService.changeNickName(
                command.getUserId(),
                command.getNickName()
        );

        // 2.保存更新后的用户
        userRepository.update(userAggregate);

        // 3.记录操作日志
        log.info("用户昵称修改成功，用户ID: {}, 新昵称: {}", command.getUserId(), command.getNickName());
    }

    /**
     * 冻结用户
     * <p>
     * 处理用户冻结请求，将用户状态设置为冻结并记录操作流水
     *
     * @param userId 用户ID，不能为空
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 当用户不存在或状态不允许冻结时抛出
     */
    public void freezeUser(String userId) {
        // 1.调用领域服务冻结用户
        UserAggregate userAggregate = userDomainService.freezeUser(userId);

        // 2.保存更新后的用户
        userRepository.update(userAggregate);

        // 3.记录操作日志
        log.info("用户冻结成功，用户ID: {}", userId);
    }

    /**
     * 解冻用户
     * <p>
     * 处理用户解冻请求，将用户状态恢复为活跃并记录操作流水
     *
     * @param userId 用户ID，不能为空
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 当用户不存在或状态不允许解冻时抛出
     */
    public void unfreezeUser(String userId) {
        // 1.调用领域服务解冻用户
        UserAggregate userAggregate = userDomainService.unfreezeUser(userId);

        // 2.保存更新后的用户
        userRepository.update(userAggregate);

        // 3.记录操作日志
        log.info("用户解冻成功，用户ID: {}", userId);
    }

    /**
     * 删除用户
     * <p>
     * 处理用户删除请求，将用户状态设置为删除（软删除）并记录操作流水
     *
     * @param userId 用户ID，不能为空
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 当用户不存在或状态不允许删除时抛出
     */
    public void deleteUser(String userId) {
        // 1.调用领域服务删除用户
        UserAggregate userAggregate = userDomainService.deleteUser(userId);

        // 2.保存更新后的用户
        userRepository.save(userAggregate);

        // 3.记录操作日志
        log.info("用户删除成功，用户ID: {}", userId);
    }

    /**
     * 更新用户头像
     * <p>
     * 处理用户头像更新请求，更新用户头像URL并记录操作流水
     *
     * @param userId    用户ID，不能为空
     * @param avatarUrl 头像URL，不能为空
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 当用户不存在或状态不允许修改时抛出
     */
    public void updateAvatar(String userId, String avatarUrl) {
        // 1.调用领域服务更新头像
        UserAggregate userAggregate = userDomainService.updateAvatar(userId, avatarUrl);

        // 2.保存更新后的用户
        userRepository.save(userAggregate);

        // 3.记录操作日志
        log.info("用户头像更新成功，用户ID: {}", userId);
    }

    /**
     * 记录用户登录
     * <p>
     * 处理用户登录请求，更新最后登录时间并记录登录操作流水
     *
     * @param userId 用户ID，不能为空
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 当用户不存在或状态不允许登录时抛出
     */
    @Transactional
    public void login(String userId) {
        // 1.调用领域服务更新最后登录时间
        UserAggregate userAggregate = userDomainService.login(userId);

        // 2.保存更新后的用户
        userRepository.update(userAggregate);

        // 3.记录操作日志
        log.info("用户登录记录成功，userId={}", userId);
    }

    /**
     * 记录用户登出
     * <p>
     * 处理用户登出请求，记录登出操作流水
     *
     * @param userId 用户ID，不能为空
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 当用户不存在时抛出
     */
    public void logout(String userId) {
        // 1.调用领域服务登出
        UserAggregate userAggregate = userDomainService.logout(userId);

        // 2.保存更新后的用户
        userRepository.update(userAggregate);

        // 3.记录操作日志
        log.info("用户登出记录成功，userId={}", userId);
    }
}
