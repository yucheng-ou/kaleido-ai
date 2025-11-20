package com.xiaoo.kaleido.user.infrastructure.adapter.repository.impl;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.DsErrorCode;
import com.xiaoo.kaleido.redis.service.IRedisService;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserOperateAggregate;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import com.xiaoo.kaleido.user.infrastructure.adapter.repository.IUserOperateRepository;
import com.xiaoo.kaleido.user.infrastructure.mapper.UserMapper;
import com.xiaoo.kaleido.user.infrastructure.mapper.UserOperateStreamMapper;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author ouyucheng
 * @date 2025/11/18
 * @description 用户仓储服务
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class UserOperateRepository implements IUserOperateRepository {

    private final UserMapper userMapper;

    private final UserOperateStreamMapper userOperateStreamMapper;

    private final IRedisService redisService;

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户唯一标识
     * @return 用户实体对象，如果不存在则返回null
     */
    @Cached(name = ":user:cache:id:", cacheType = CacheType.BOTH, key = "#id", cacheNullValue = true)
    @CacheRefresh(refresh = 30, timeUnit = TimeUnit.MINUTES)  //缓存过期一个小时前 如果有访问触发 会启动一个异步任务更新缓存
    public User getById(Long id) {
        return userMapper.getById(id);
    }

    /**
     * 根据邀请码查询用户信息
     *
     * @param inviteCode 邀请码
     * @return 用户信息
     */
    @Override
    public User getByInviteCode(String inviteCode) {
        return userMapper.getByInviteCode(inviteCode);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param telephone 用户手机号
     * @return 用户信息
     */
    @Override
    public User getByTelephone(String telephone) {
        return userMapper.getByTelephone(telephone);
    }

    @Override
    public void updateUser(User user) {
        int updateCount = userMapper.updateById(user);
        if (updateCount != 1) {
            log.error("用户信息更新失败，用户ID：{}, 昵称：{}, 手机号：{}", user.getId(), user.getNickName(), user.getTelephone());
            throw new UserException(DsErrorCode.UPDATE_FAILED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserOperateAggregate(UserOperateAggregate userOperateAggregate) {
        User user = userOperateAggregate.getUser();
        UserOperateStream userOperateStream = userOperateAggregate.getUserOperateStream();

        try {
            // 更新用户信息
            updateUser(user);

            // 插入操作流水记录
            userOperateStream.setUserId(user.getId());
            int userOperateStreamCount = userOperateStreamMapper.insert(userOperateStream);
            if (userOperateStreamCount != 1) {
                log.error("用户流水写入失败，用户ID：{}, 操作类型：{}", user.getId(), userOperateStream.getOperateType());
                throw new UserException(DsErrorCode.INSERT_FAILED);
            }
        } catch (DuplicateKeyException e) {
            log.error("写入用户操作流水唯一索引冲突，用户ID：{}, 操作类型：{}", user.getId(), userOperateStream.getOperateType());
            throw new UserException(BizErrorCode.UNIQUE_INDEX_CONFLICT);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserOperateAggregate(UserOperateAggregate userOperateAggregate) {
        User user = userOperateAggregate.getUser();
        UserOperateStream userOperateStream = userOperateAggregate.getUserOperateStream();

        try {
            int userInsertCount = userMapper.insert(user);
            if (userInsertCount != 1) {
                log.error("用户信息写入失败，用户昵称：{},手机号：{}，邀请码：{}", user.getNickName(), user.getTelephone(), user.getInviteCode());
                throw new UserException(DsErrorCode.INSERT_FAILED);
            }

            userOperateStream.setUserId(user.getId());
            int userOperateStreamCount = userOperateStreamMapper.insert(userOperateStream);
            if (userOperateStreamCount != 1) {
                log.error("用户流水写入失败，用户昵称：{},手机号：{}，邀请码：{}", user.getNickName(), user.getTelephone(), user.getInviteCode());
                throw new UserException(DsErrorCode.INSERT_FAILED);
            }
        } catch (DuplicateKeyException e) {
            log.error("写入用户信息唯一索引冲突，用户昵称：{},手机号：{}，邀请码：{}", user.getNickName(), user.getTelephone(), user.getInviteCode());
            throw new UserException(BizErrorCode.UNIQUE_INDEX_CONFLICT);
        }
    }
}
