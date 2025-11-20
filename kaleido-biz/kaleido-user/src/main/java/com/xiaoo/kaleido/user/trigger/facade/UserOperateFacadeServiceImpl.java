package com.xiaoo.kaleido.user.trigger.facade;

import com.xiaoo.kaleido.api.user.IUserOperateFacadeService;
import com.xiaoo.kaleido.api.user.request.UpdateUserInfoRequest;
import com.xiaoo.kaleido.api.user.request.UserQueryRequest;
import com.xiaoo.kaleido.api.user.request.UserRegisterRequest;
import com.xiaoo.kaleido.api.user.response.UserOperateVo;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.user.domain.model.convertor.UserConvertor;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.service.IUserOperateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ouyucheng
 * @version 1.0
 * @date 2025/11/19
 */
@Slf4j
@RequiredArgsConstructor
@DubboService(version = "1.0.0")
@Service
public class UserOperateFacadeServiceImpl implements IUserOperateFacadeService {

    /**
     * 用户操作领域服务 - 负责处理核心用户注册业务逻辑
     * 通过依赖注入方式获取，确保业务逻辑的封装性和可测试性
     */
    private final IUserOperateService userOperateService;

    /**
     * 用户注册
     *
     * @param request 用户注册请求参数，包含手机号、验证码等信息
     * @return 用户操作结果
     */
    @Override
    public Result<UserOperateVo> register(UserRegisterRequest request) {
        try {
            log.info("开始处理用户注册RPC请求，手机号：{}", request.getTelephone());

            // 调用领域服务处理用户注册业务逻辑
            User user = userOperateService.register(request.getTelephone(), request.getInviteCode());

            // 将领域实体转换为VO对象返回
            UserOperateVo userOperateVo = UserConvertor.INSTANCE.mapToVo(user);

            log.info("用户注册RPC请求处理成功，用户ID：{}", user.getId());
            return Result.success(userOperateVo);
        } catch (BizException e) {
            log.error("用户注册业务异常，手机号：{}，错误码：{}", request.getTelephone(), e.getErrorCode(), e);
            return Result.error(e);
        } catch (Exception e) {
            log.error("用户注册系统异常，手机号：{}", request.getTelephone(), e);
            return Result.error(BizErrorCode.UNKNOWN_ERROR);
        }
    }


    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户操作结果
     */
    @Override
    public Result<UserOperateVo> getById(Long userId) {
        try {
            User user = userOperateService.getById(userId);
            return Result.success(UserConvertor.INSTANCE.mapToVo(user));
        } catch (Exception e) {
            log.error("用户查询系统异常，用户ID：{}", userId, e);
            return Result.error(BizErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 更新用户基本信息
     *
     * @param request 更新用户信息请求参数
     * @return 用户操作结果
     */
    @Override
    public Result<UserOperateVo> updateUserInfo(UpdateUserInfoRequest request) {
        try {
            log.info("开始处理用户信息更新RPC请求，用户ID：{}", request.getUserId());

            // 调用领域服务处理用户信息更新业务逻辑
            User user = userOperateService.updateUserInfo(request);

            // 将领域实体转换为VO对象返回
            UserOperateVo userOperateVo = UserConvertor.INSTANCE.mapToVo(user);

            log.info("用户信息更新RPC请求处理成功，用户ID：{}", user.getId());
            return Result.success(userOperateVo);
        } catch (BizException e) {
            log.error("用户信息更新业务异常，用户ID：{}，错误码：{}", request.getUserId(), e.getErrorCode(), e);
            return Result.error(e);
        } catch (Exception e) {
            log.error("用户信息更新系统异常，用户ID：{}", request.getUserId(), e);
            return Result.error(BizErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 查询用户列表（不分页）
     * 根据查询条件返回匹配的用户列表
     *
     * @param request 用户查询请求参数
     * @return 用户操作结果
     */
    @Override
    public Result<List<UserOperateVo>> listUsers(UserQueryRequest request) {
        try {
            log.info("开始处理用户列表查询RPC请求，查询条件：ID={}, 手机号={}, 邀请码={}, 昵称={}", 
                    request.getId(), request.getTelephone(), request.getInviteCode(), request.getNickName());

            // 调用领域服务处理用户列表查询业务逻辑
            List<User> users = userOperateService.listUsers(request);

            // 将领域实体列表转换为VO对象列表返回
            List<UserOperateVo> userOperateVos = users.stream()
                    .map(UserConvertor.INSTANCE::mapToVo)
                    .collect(Collectors.toList());

            log.info("用户列表查询RPC请求处理成功，返回结果数量：{}", userOperateVos.size());
            return Result.success(userOperateVos);
        } catch (BizException e) {
            log.error("用户列表查询业务异常，查询条件：ID={}, 手机号={}, 邀请码={}, 昵称={}，错误码：{}", 
                    request.getId(), request.getTelephone(), request.getInviteCode(), request.getNickName(), e.getErrorCode(), e);
            return Result.error(e);
        } catch (Exception e) {
            log.error("用户列表查询系统异常，查询条件：ID={}, 手机号={}, 邀请码={}, 昵称={}", 
                    request.getId(), request.getTelephone(), request.getInviteCode(), request.getNickName(), e);
            return Result.error(BizErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 分页查询用户列表
     * 根据查询条件和分页参数返回分页结果
     *
     * @param request 用户查询请求参数
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @return 用户操作结果
     */
    @Override
    public Result<List<UserOperateVo>> listUsers(UserQueryRequest request, int page, int size) {
        try {
            log.info("开始处理分页用户列表查询RPC请求，查询条件：ID={}, 手机号={}, 邀请码={}, 昵称={}, 页码={}, 页大小={}", 
                    request.getId(), request.getTelephone(), request.getInviteCode(), request.getNickName(), page, size);

            // 调用领域服务处理分页用户列表查询业务逻辑
            List<User> users = userOperateService.listUsers(request, page, size);

            // 将领域实体列表转换为VO对象列表返回
            List<UserOperateVo> userOperateVos = users.stream()
                    .map(UserConvertor.INSTANCE::mapToVo)
                    .collect(Collectors.toList());

            log.info("分页用户列表查询RPC请求处理成功，返回结果数量：{}", userOperateVos.size());
            return Result.success(userOperateVos);
        } catch (BizException e) {
            log.error("分页用户列表查询业务异常，查询条件：ID={}, 手机号={}, 邀请码={}, 昵称={}, 页码={}, 页大小={}，错误码：{}", 
                    request.getId(), request.getTelephone(), request.getInviteCode(), request.getNickName(), page, size, e.getErrorCode(), e);
            return Result.error(e);
        } catch (Exception e) {
            log.error("分页用户列表查询系统异常，查询条件：ID={}, 手机号={}, 邀请码={}, 昵称={}, 页码={}, 页大小={}", 
                    request.getId(), request.getTelephone(), request.getInviteCode(), request.getNickName(), page, size, e);
            return Result.error(BizErrorCode.UNKNOWN_ERROR);
        }
    }
}
