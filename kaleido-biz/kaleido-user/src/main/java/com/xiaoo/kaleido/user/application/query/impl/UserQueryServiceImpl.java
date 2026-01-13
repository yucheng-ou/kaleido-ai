package com.xiaoo.kaleido.user.application.query.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.user.application.convertor.UserConvertor;
import com.xiaoo.kaleido.user.application.query.UserQueryService;
import com.xiaoo.kaleido.user.domain.adapter.repository.UserRepository;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户查询服务实现
 * <p>
 * 用户应用层查询服务的具体实现，负责用户相关的读操作
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
        // 1.调用仓储层查询用户聚合根
        // 2.使用转换器转换为响应对象
        // 3.如果用户不存在则返回null
        return userRepository.findById(userId)
                .map(userConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public UserInfoResponse findByTelephone(String telephone) {
        // 1.调用仓储层根据手机号查询用户聚合根
        // 2.使用转换器转换为响应对象
        // 3.如果用户不存在则返回null
        return userRepository.findByTelephone(telephone)
                .map(userConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public UserInfoResponse findByInviteCode(String inviteCode) {
        // 1.调用仓储层根据邀请码查询用户聚合根
        // 2.使用转换器转换为响应对象
        // 3.如果用户不存在则返回null
        return userRepository.findByInviteCode(inviteCode)
                .map(userConvertor::toResponse)
                .orElse(null);
    }

    @Override
    public PageInfo<UserInfoResponse> pageQuery(UserPageQueryReq req) {
        // 1.启动PageHelper分页
        PageHelper.startPage(req.getPageNum(), req.getPageSize());

        // 2.调用仓储层进行分页查询
        List<UserAggregate> aggregates = userRepository.pageQuery(req);

        // 3.转换为响应类型
        List<UserInfoResponse> responseList = aggregates.stream()
                .map(userConvertor::toResponse)
                .collect(Collectors.toList());

        // 4.构建PageInfo分页响应
        return new PageInfo<>(responseList);
    }

}
