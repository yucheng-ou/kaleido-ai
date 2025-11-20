package com.xiaoo.kaleido.user.domain.service;

/**
 * @author ouyucheng
 * @date 2025/11/19
 * @description
 */
public interface IUserBasicInfoGenerator {

    String generatePassword();

    String generateInviteCode();

    String generateNickname(String inviteCode, String nickname);
}
