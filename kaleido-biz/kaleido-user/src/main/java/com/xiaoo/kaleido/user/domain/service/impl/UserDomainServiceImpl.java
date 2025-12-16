package com.xiaoo.kaleido.user.domain.service.impl;

import com.xiaoo.kaleido.user.domain.adapter.port.UserRepository;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.model.valobj.InvitationCode;
import com.xiaoo.kaleido.user.domain.service.UserDomainService;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 用户领域服务实现
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;

    @Override
    public UserAggregate createUser(String telephone, String passwordHash, String inviterId) {
        
        // 生成邀请码
        InvitationCode invitationCode = generateInvitationCode();
        
        // 生成默认昵称（用户+手机号后4位）
        String defaultNickName = "用户" + telephone.substring(telephone.length() - 4);
        
        // 创建用户实体
        User user = User.create(
                telephone,
                passwordHash,
                defaultNickName,
                invitationCode.getValue(),
                inviterId
        );
        
        // 创建用户聚合根并添加创建操作流水
        UserAggregate userAggregate = UserAggregate.createWithOperateStream(user, inviterId);
        
        log.info("用户领域服务创建用户，用户ID: {}, 手机号: {}, 邀请码: {}",
                user.getId(), telephone, invitationCode.getValue());
        
        return userAggregate;
    }

    @Override
    public User findByIdOrThrow(String userId) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        return aggregate.getUser();
    }

    @Override
    public UserAggregate changeNickName(String userId, String nickName) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        aggregate.changeNickName(nickName);
        return aggregate;
    }

    @Override
    public UserAggregate freezeUser(String userId) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        aggregate.freeze();
        return aggregate;
    }

    @Override
    public UserAggregate unfreezeUser(String userId) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        aggregate.unfreeze();
        return aggregate;
    }

    @Override
    public UserAggregate deleteUser(String userId) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        aggregate.delete();
        return aggregate;
    }

    @Override
    public UserAggregate updateAvatar(String userId, String avatarUrl) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        aggregate.updateAvatar(avatarUrl);
        return aggregate;
    }

    @Override
    public boolean verifyPassword(String userId, String passwordHash) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        return aggregate.verifyPassword(passwordHash);
    }

    @Override
    public UserAggregate updateLastLoginTime(String userId) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        aggregate.updateLastLoginTime();
        return aggregate;
    }

    @Override
    public InvitationCode generateInvitationCode() {
        // 生成唯一邀请码
        InvitationCode invitationCode;
        int maxRetry = 10;
        
        for (int i = 0; i < maxRetry; i++) {
            invitationCode = InvitationCode.generate();
            if (!userRepository.existsByInviteCode(invitationCode.getValue())) {
                return invitationCode;
            }
        }
        
        // 如果多次尝试后仍然冲突，使用基于UUID的确定性生成
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return InvitationCode.fromUserId(uuid);
    }

    @Override
    public boolean validateInvitationCode(String inviteCode) {
        // 验证邀请码格式
        if (!InvitationCode.isValidFormat(inviteCode)) {
            return false;
        }
        
        // 验证邀请码是否存在
        return userRepository.findByInviteCode(inviteCode).isPresent();
    }

    @Override
    public User findInviterByInviteCode(String inviteCode) {
        UserAggregate inviterAggregate = userRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new UserException(UserErrorCode.INVALID_INVITE_CODE));
        
        return inviterAggregate.getUser();
    }
}
