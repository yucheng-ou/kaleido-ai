package com.xiaoo.kaleido.user.domain.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.api.user.constant.UserOperateTypeEnum;
import com.xiaoo.kaleido.api.user.request.UpdateUserInfoRequest;
import com.xiaoo.kaleido.api.user.request.UserQueryRequest;
import com.xiaoo.kaleido.redis.service.RedissonService;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserOperateAggregate;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import com.xiaoo.kaleido.user.domain.service.IUserBasicInfoGenerator;
import com.xiaoo.kaleido.user.domain.service.IUserOperateService;
import com.xiaoo.kaleido.user.infrastructure.adapter.repository.impl.UserOperateRepository;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户操作服务实现类
 * 负责用户注册、信息修改等核心业务逻辑的实现
 * 遵循领域驱动设计(DDD)原则，作为领域服务层的重要组成部分
 *
 * @author ouyucheng
 * @date 2025/11/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserOperateServiceImpl implements IUserOperateService {

    private final RedissonService redissonService;

    /**
     * 用户仓储服务 - 负责用户数据的持久化操作
     */
    private final UserOperateRepository userRepository;

    /**
     * 用户基础信息生成器 - 负责生成邀请码、密码、昵称等用户基础信息
     */
    private final IUserBasicInfoGenerator userBasicInfoGenerator;

    /**
     * 注册新用户
     * 处理用户注册的核心业务逻辑，包括邀请码验证、用户信息生成和持久化
     *
     * @param telephone  用户手机号（必填，用于唯一标识用户）
     * @param inviteCode 邀请码（可选，用于关联邀请关系）
     * @return 注册成功的用户实体对象
     * @throws UserException 当邀请码无效或手机号已存在时抛出业务异常
     */

    @Override
    public User register(String telephone, String inviteCode) {

        //查询邀请人信息
        Long inviterId = null;
        if (StrUtil.isNotBlank(inviteCode)) {
            User inviter = userRepository.getByInviteCode(inviteCode);
            Assert.notNull(inviter, () -> new UserException(UserErrorCode.INVALID_INVITE_CODE));
            inviterId = inviter.getId();
        }

        //生成邀请码
        String curUserInviteCode = userBasicInfoGenerator.generateInviteCode();
        //生成密码
        String password = userBasicInfoGenerator.generatePassword();
        //生成昵称
        String nickName = userBasicInfoGenerator.generateNickname(curUserInviteCode, telephone);

        return register(telephone, curUserInviteCode, nickName, password, inviterId);
    }


    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户操作结果
     */
    @Override
    public User getById(Long userId) {
        Assert.notNull(userId, () -> new UserException(UserErrorCode.USER_ID_NOT_NULL));
        return userRepository.getById(userId);
    }

    /**
     * 更新用户基本信息
     * 包括昵称、头像和手机号的更新，支持部分更新（null值表示不更新该字段）
     *
     * @param request 更新用户信息请求参数
     * @return 更新后的用户实体对象
     * @throws UserException 当用户不存在或手机号已存在时抛出业务异常
     */
    @Override
    public User updateUserInfo(UpdateUserInfoRequest request) {
        // 验证请求参数
        Assert.notNull(request, () -> new UserException(UserErrorCode.REQUEST_PARAM_NULL));
        Assert.notNull(request.getUserId(), () -> new UserException(UserErrorCode.USER_ID_NOT_NULL));

        // 查询用户信息
        User user = userRepository.getById(request.getUserId());
        Assert.notNull(user, () -> new UserException(UserErrorCode.USER_NOT_EXIST));

        // 如果更新手机号，验证手机号唯一性
        if (StrUtil.isNotBlank(request.getTelephone())) {
            User existingUser = userRepository.getByTelephone(request.getTelephone());
            if (existingUser != null && !existingUser.getId().equals(request.getUserId())) {
                throw new UserException(UserErrorCode.DUPLICATE_TELEPHONE);
            }
        }

        // 更新用户基本信息
        user.updateBasicInfo(request.getNickName(), request.getAvatar(), request.getTelephone());

        // 创建用户操作流水记录
        UserOperateStream userOperateStream = UserOperateStream.operateStream(user, UserOperateTypeEnum.MODIFY);

        // 保存用户更新（用户实体 + 操作流水）
        userRepository.updateUserOperateAggregate(
                UserOperateAggregate.builder()
                        .user(user)
                        .userOperateStream(userOperateStream)
                        .build()
        );

        // 记录更新完成日志
        log.info("用户信息更新完成，用户ID：{}, 昵称：{}, 手机号：{}", 
                user.getId(), user.getNickName(), user.getTelephone());

        return user;
    }

    /**
     * 内部注册方法 - 执行具体的用户注册逻辑
     * 包括手机号唯一性验证、用户实体创建、操作流水记录和聚合根持久化
     *
     * @param telephone  用户手机号（必填）
     * @param inviteCode 用户邀请码（系统生成）
     * @param nickName   用户昵称（系统生成）
     * @param password   用户密码（系统生成）
     * @param inviterId  邀请人ID（可选，可为null）
     * @return 注册成功的用户实体对象
     * @throws UserException 当手机号已存在时抛出业务异常
     */
    private User register(String telephone, String inviteCode, String nickName, String password, Long inviterId) {
        // 验证手机号唯一性
        Assert.isNull(userRepository.getByTelephone(telephone), () -> new UserException(UserErrorCode.DUPLICATE_TELEPHONE));

        // 创建用户实体
        User user = User.register(telephone, inviteCode, nickName, password, inviterId);

        // 创建用户操作流水记录
        UserOperateStream userOperateStream = UserOperateStream.operateStream(user, UserOperateTypeEnum.REGISTER);

        // 保存用户聚合根（用户实体 + 操作流水）
        userRepository.saveUserOperateAggregate(
                UserOperateAggregate.builder()
                        .user(user)
                        .userOperateStream(userOperateStream)
                        .build()
        );

        // 记录注册完成日志
        log.info("用户注册完成，用户昵称：{},手机号：{}", user.getNickName(), user.getTelephone());

        return user;
    }

    /**
     * 查询用户列表（不分页）
     * 根据查询条件返回匹配的用户列表
     *
     * @param request 用户查询请求参数
     * @return 用户列表
     */
    @Override
    public List<User> listUsers(UserQueryRequest request) {
        // 验证请求参数
        Assert.notNull(request, () -> new UserException(UserErrorCode.REQUEST_PARAM_NULL));
        
        // 调用仓储层查询用户列表
        List<User> users = userRepository.listUsers(request);
        
        log.info("查询用户列表完成，查询条件：ID={}, 手机号={}, 邀请码={}, 昵称={}, 返回结果数量：{}",
                request.getId(), request.getTelephone(), request.getInviteCode(), request.getNickName(), users.size());
        
        return users;
    }

    /**
     * 分页查询用户列表
     * 根据查询条件和分页参数返回分页结果
     *
     * @param request 用户查询请求参数
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @return 用户列表
     */
    @Override
    public List<User> listUsers(UserQueryRequest request, int page, int size) {
        // 验证请求参数
        Assert.notNull(request, () -> new UserException(UserErrorCode.REQUEST_PARAM_NULL));
        Assert.isTrue(page > 0, () -> new UserException(UserErrorCode.PAGE_PARAM_INVALID));
        Assert.isTrue(size > 0 && size <= 100, () -> new UserException(UserErrorCode.SIZE_PARAM_INVALID));
        
        // 调用仓储层分页查询用户列表
        List<User> users = userRepository.listUsers(request, page, size);
        
        log.info("分页查询用户列表完成，查询条件：ID={}, 手机号={}, 邀请码={}, 昵称={}, 页码={}, 页大小={}, 返回结果数量：{}",
                request.getId(), request.getTelephone(), request.getInviteCode(), request.getNickName(), 
                page, size, users.size());
        
        return users;
    }
}
