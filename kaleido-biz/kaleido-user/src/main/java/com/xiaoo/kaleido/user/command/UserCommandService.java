package com.xiaoo.kaleido.user.command;

import com.xiaoo.kaleido.user.domain.adapter.port.UserRepository;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.user.domain.service.UserDomainService;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
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
    private final UserDomainService userDomainService;

    /**
     * 创建用户
     *
     * @param command 创建用户命令
     * @return 用户ID
     */
    @Transactional
    public String createUser(AddUserCommand command) {
        // 验证手机号是否已存在
        if (userRepository.existsByTelephone(command.getTelephone())) {
            throw UserException.of(UserErrorCode.DUPLICATE_TELEPHONE);
        }

        // 验证邀请码（如果提供）
        String inviterId = null;
        if (command.getInviterCode() != null && !command.getInviterCode().trim().isEmpty()) {
            UserAggregate inviter = userRepository.findByInviteCode(command.getInviterCode())
                    .orElseThrow(() -> new UserException(UserErrorCode.INVALID_INVITE_CODE));
            inviterId = inviter.getId();
        }

        // 调用领域服务创建用户
        UserAggregate userAggregate = userDomainService.createUser(
                command.getTelephone(),
                command.getPassword(),
                inviterId
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
    @Transactional
    public void changeNickName(ChangeNickNameCommand command) {
        // 权限验证：用户只能修改自己的信息
        if (!command.getUserId().equals(command.getOperatorId())) {
            throw UserException.of(UserErrorCode.USER_OPERATE_FAILED);
        }

        // 验证昵称是否已存在
        if (userRepository.existsByNickName(command.getNickName())) {
            throw new UserException(UserErrorCode.NICK_NAME_EXIST);
        }

        // 调用领域服务修改昵称
        UserAggregate userAggregate = userDomainService.changeNickName(
                command.getUserId(),
                command.getNickName()
        );

        // 保存用户
        userRepository.save(userAggregate);

        log.info("用户昵称修改成功，用户ID: {}, 新昵称: {}", command.getUserId(), command.getNickName());
    }

    /**
     * 冻结用户
     *
     * @param command 冻结用户命令
     */
    @Transactional
    public void freezeUser(FreezeUserCommand command) {
        // 权限验证：用户只能冻结自己（或管理员权限，这里简化处理）
        if (!command.getUserId().equals(command.getOperatorId())) {
            throw UserException.of(UserErrorCode.USER_OPERATE_FAILED);
        }

        // 调用领域服务冻结用户
        UserAggregate userAggregate = userDomainService.freezeUser(command.getUserId());

        // 保存用户
        userRepository.save(userAggregate);

        log.info("用户冻结成功，用户ID: {}, 原因: {}", command.getUserId(), command.getReason());
    }

    /**
     * 解冻用户
     *
     * @param command 解冻用户命令
     */
    @Transactional
    public void unfreezeUser(UnfreezeUserCommand command) {
        // 权限验证：用户只能解冻自己
        if (!command.getUserId().equals(command.getOperatorId())) {
            throw UserException.of(UserErrorCode.USER_OPERATE_FAILED);
        }

        // 调用领域服务解冻用户
        UserAggregate userAggregate = userDomainService.unfreezeUser(command.getUserId());

        // 保存用户
        userRepository.save(userAggregate);

        log.info("用户解冻成功，用户ID: {}, 原因: {}", command.getUserId(), command.getReason());
    }

    /**
     * 删除用户（软删除）
     *
     * @param userId     用户ID
     * @param operatorId 操作者ID
     */
    @Transactional
    public void deleteUser(String userId, String operatorId) {
        // 权限验证：用户只能删除自己
        if (!userId.equals(operatorId)) {
            throw UserException.of(UserErrorCode.USER_OPERATE_FAILED);
        }

        // 调用领域服务删除用户
        UserAggregate userAggregate = userDomainService.deleteUser(userId);

        // 保存用户
        userRepository.save(userAggregate);

        log.info("用户删除成功，用户ID: {}", userId);
    }

    /**
     * 更新用户头像
     *
     * @param userId     用户ID
     * @param avatarUrl  头像URL
     * @param operatorId 操作者ID
     */
    @Transactional
    public void updateAvatar(String userId, String avatarUrl, String operatorId) {
        // 权限验证：用户只能更新自己的头像
        if (!userId.equals(operatorId)) {
            throw UserException.of(UserErrorCode.USER_OPERATE_FAILED);
        }

        // 调用领域服务更新头像
        UserAggregate userAggregate = userDomainService.updateAvatar(userId, avatarUrl);

        // 保存用户
        userRepository.save(userAggregate);

        log.info("用户头像更新成功，用户ID: {}", userId);
    }

    /**
     * 更新用户信息
     *
     * @param command 更新用户信息命令
     */
    @Transactional
    public void updateUserInfo(UpdateUserInfoCommand command) {
        // 权限验证：用户只能修改自己的信息
        if (!command.getUserId().equals(command.getOperatorId())) {
            throw UserException.of(UserErrorCode.USER_OPERATE_FAILED);
        }

        // 获取用户聚合根
        UserAggregate userAggregate = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_EXIST));

        // 更新昵称（如果提供）
        if (command.getNickName() != null && !command.getNickName().trim().isEmpty()) {
            // 验证昵称是否已存在（排除自己）
            if (userRepository.existsByNickNameAndIdNot(command.getNickName(), command.getUserId())) {
                throw new UserException(UserErrorCode.NICK_NAME_EXIST);
            }
            userAggregate = userDomainService.changeNickName(command.getUserId(), command.getNickName());
        }

        // 更新头像（如果提供）
        if (command.getAvatarUrl() != null && !command.getAvatarUrl().trim().isEmpty()) {
            userAggregate = userDomainService.updateAvatar(command.getUserId(), command.getAvatarUrl());
        }

        // 更新扩展信息（如果提供）
        if (command.getExtraInfo() != null && !command.getExtraInfo().trim().isEmpty()) {
            userAggregate = userDomainService.updateExtraInfo(command.getUserId(), command.getExtraInfo());
        }

        // 保存用户
        userRepository.save(userAggregate);

        log.info("用户信息更新成功，用户ID: {}", command.getUserId());
    }
}
