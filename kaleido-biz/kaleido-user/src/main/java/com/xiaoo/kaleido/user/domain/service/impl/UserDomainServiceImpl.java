package com.xiaoo.kaleido.user.domain.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.user.domain.adapter.repository.UserRepository;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.user.domain.model.valobj.InvitationCode;
import com.xiaoo.kaleido.user.domain.service.IUserDomainService;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 用户领域服务实现
 * <p>
 * 用户领域服务的具体实现，处理跨实体的用户业务逻辑
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDomainServiceImpl implements IUserDomainService {

    private final UserRepository userRepository;

    @Override
    public UserAggregate createUser(String telephone, String inviteCode) {
        // 1.验证手机号是否已存在
        if (userRepository.existsByTelephone(telephone)) {
            throw UserException.of(UserErrorCode.DUPLICATE_TELEPHONE);
        }

        // 2.验证邀请码（如果提供）
        String inviterId = null;
        if (StrUtil.isNotBlank(inviteCode)) {
            UserAggregate inviter = userRepository.findByInviteCode(inviteCode)
                    .orElseThrow(() -> UserException.of(UserErrorCode.INVALID_INVITE_CODE));
            inviterId = inviter.getId();
        }

        // 3.生成用户邀请码
        InvitationCode invitationCode = generateInvitationCode();

        // 4.生成默认昵称（用户+手机号后4位）
        String defaultNickName = "用户" + telephone.substring(telephone.length() - 4);

        // 5.创建用户聚合根并返回
        return UserAggregate.create(telephone,
                defaultNickName,
                invitationCode.getValue(),
                inviterId);
    }

    @Override
    public UserAggregate findByIdOrThrow(String userId) {
        return userRepository.findByIdOrThrow(userId);
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
    public UserAggregate login(String userId) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        aggregate.login();
        return aggregate;
    }

    @Override
    public UserAggregate logout(String userId) {
        UserAggregate aggregate = userRepository.findByIdOrThrow(userId);
        aggregate.logout();
        return aggregate;
    }

    @Override
    public InvitationCode generateInvitationCode() {
        // 1.尝试生成唯一邀请码（最多重试10次）
        InvitationCode invitationCode;
        int maxRetry = 10;

        for (int i = 0; i < maxRetry; i++) {
            invitationCode = InvitationCode.generate();
            
            // 使用布隆过滤器快速判断邀请码是否可能已存在
            if (!userRepository.mightExistByInviteCode(invitationCode.getValue())) {
                // 布隆过滤器判断不存在，极大概率确实不存在
                return invitationCode;
            }
            
            // 布隆过滤器判断可能存在，查询数据库确认
            if (!userRepository.existsByInviteCode(invitationCode.getValue())) {
                // 数据库确认不存在，添加到布隆过滤器
                userRepository.addToInviteCodeFilter(invitationCode.getValue());
                return invitationCode;
            }
        }

        // 2.如果多次尝试后仍然冲突，使用基于UUID的确定性生成
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return InvitationCode.fromUserId(uuid);
    }
}
