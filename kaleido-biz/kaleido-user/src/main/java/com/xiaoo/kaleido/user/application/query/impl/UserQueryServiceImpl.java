package com.xiaoo.kaleido.user.application.query.impl;

import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.response.PageResp;
import com.xiaoo.kaleido.user.application.convertor.UserConvertor;
import com.xiaoo.kaleido.user.application.query.UserQueryService;
import com.xiaoo.kaleido.user.domain.adapter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 用户查询服务实现
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final UserConvertor userConvertor;

    @Override
    public UserInfoResponse findById(String userId) {
        return userRepository.findById(userId)
                .map(userConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public UserInfoResponse findByTelephone(String telephone) {
        return userRepository.findByTelephone(telephone)
                .map(userConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public UserInfoResponse findByInviteCode(String inviteCode) {
        return userRepository.findByInviteCode(inviteCode)
                .map(userConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public PageResp<UserInfoResponse> pageQuery(UserPageQueryReq req) {
        // 调用仓储层进行分页查询
        PageResp<com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate> pageResult = 
                userRepository.pageQuery(req);
        
        // 转换为响应类型
        return PageResp.of(
                pageResult.getList().stream()
                        .map(userConvertor::toResponse)
                        .collect(Collectors.toList()),
                pageResult.getTotal(),
                pageResult.getPageNum(),
                pageResult.getPageSize()
        );
    }

}
