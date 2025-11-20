package com.xiaoo.kaleido.user.domain.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.xiaoo.kaleido.base.constant.CommonConstant;
import com.xiaoo.kaleido.user.domain.constant.UserConstant;
import com.xiaoo.kaleido.user.domain.service.IUserBasicInfoGenerator;
import com.xiaoo.kaleido.user.infrastructure.adapter.repository.impl.UserOperateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ouyucheng
 * @date 2025/11/19
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class IUserBasicInfoGeneratorImpl implements IUserBasicInfoGenerator {

    private final UserOperateRepository userRepository;

    @Override
    public String generatePassword() {
        return RandomUtil.randomString(UserConstant.BasicInfo.USER_RANDOM_PASSWORD_LENGTH);
    }

    @Override
    public String generateInviteCode() {
        //TODO 后期使用布隆过滤器优化
        String inviteCode = RandomUtil.randomString(UserConstant.BasicInfo.USER_INVITE_CODE_LENGTH);
        while (userRepository.getByInviteCode(inviteCode) != null) {
            inviteCode = RandomUtil.randomString(UserConstant.BasicInfo.USER_INVITE_CODE_LENGTH);
        }
        return inviteCode;
    }

    @Override
    public String generateNickname(String inviteCode, String telephone) {
        return UserConstant.BasicInfo.USER_NICK_NAME_PREFIX + inviteCode + CommonConstant.UNDERLINE + telephone.substring(7, 11);
    }
}
