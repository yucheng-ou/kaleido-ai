package com.xiaoo.kaleido.user.infrastructure.adapter.repository;

import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.user.domain.adapter.repository.UserRepository;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import com.xiaoo.kaleido.user.infrastructure.adapter.repository.convertor.UserConvertor;
import com.xiaoo.kaleido.user.infrastructure.adapter.repository.convertor.UserOperateStreamConvertor;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserOperateStreamPO;
import com.xiaoo.kaleido.user.infrastructure.dao.UserDao;
import com.xiaoo.kaleido.user.infrastructure.dao.UserOperateStreamDao;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import com.xiaoo.kaleido.redis.service.DelayDeleteService;
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户仓储实现（基础设施层）
 * <p>
 * 用户仓储接口的具体实现，负责用户聚合根的持久化和查询
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;
    private final UserOperateStreamDao userOperateStreamDao;
    private final RBloomFilter<String> inviteCodeBloomFilter;
    private final CacheManager cacheManager;
    private final DelayDeleteService delayDeleteService;

    @Lazy
    @Resource
    private UserRepository self;

    /**
     * 通过用户ID对用户信息做的缓存
     */
    private Cache<String, UserAggregate> userCache;

    /**
     * 初始化用户id缓存 cacheManager
     */
    @PostConstruct
    public void init() {
        QuickConfig idQc = QuickConfig.newBuilder(":user:")
                .cacheType(CacheType.REMOTE)
                .expire(Duration.ofHours(1))
                .syncLocal(true)
                .build();
        userCache = cacheManager.getOrCreateCache(idQc);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserAggregate userAggregate) {
        // 1.保存用户实体
        User user = userAggregate.getUser();
        UserPO userPO = UserConvertor.INSTANCE.toPO(user);
        userDao.insert(userPO);

        // 2.保存用户操作流水
        batchSaveOperateStream(userAggregate.getOperateStreams());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserAggregate userAggregate) {
        // 1.第一次手动删除缓存
        userCache.remove(userAggregate.getUser().getId());

        // 2.修改用户信息
        User user = userAggregate.getUser();
        UserPO userPO = UserConvertor.INSTANCE.toPO(user);
        userDao.updateById(userPO);

        // 3.保存用户操作流水
        batchSaveOperateStream(userAggregate.getOperateStreams());

        // 4.第二次延迟双删
        delayDeleteService.delayedCacheDelete(userCache, userAggregate.getUser().getId());
    }

    @Override
    @Cached(name = ":user:", key = "#id", expire = 60, cacheType = CacheType.REMOTE, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    @CacheRefresh(refresh = 50, timeUnit = TimeUnit.MINUTES)
    public UserAggregate findById(String id) {
        // 1.查询用户持久化对象
        UserPO userPO = userDao.findById(id);
        if (userPO == null) {
            return null;
        }

        // 2.转换为用户实体
        User user = UserConvertor.INSTANCE.toEntity(userPO);

        // 3.创建聚合根（不加载操作流水，按需加载）
        return UserAggregate.create(user);
    }

    @Override
    public UserAggregate findUserAndStreamById(String id) {
        // 1.查询用户持久化对象
        UserPO userPO = userDao.findById(id);
        if (userPO == null) {
            return null;
        }

        // 2.转换为用户实体
        User user = UserConvertor.INSTANCE.toEntity(userPO);

        // 3.加载操作流水（最近100条）
        List<UserOperateStreamPO> streamPOs = userOperateStreamDao.findByUserIdWithLimit(userPO.getId(), 100);
        List<UserOperateStream> streams = streamPOs.stream()
                .map(UserOperateStreamConvertor.INSTANCE::toEntity)
                .toList();

        // 4.创建聚合根并添加操作流水
        UserAggregate aggregate = UserAggregate.create(user);
        aggregate.getOperateStreams().addAll(streams);
        return aggregate;
    }

    @Override
    public UserAggregate findByTelephone(String telephone) {
        // 1.根据手机号查询用户持久化对象
        UserPO po = userDao.findByTelephone(telephone);

        // 2.转换为用户聚合根
        return po != null ? UserAggregate.create(UserConvertor.INSTANCE.toEntity(po)) : null;
    }

    @Override
    public UserAggregate findByInviteCode(String inviteCode) {
        // 1.根据邀请码查询用户持久化对象
        UserPO po = userDao.findByInviteCode(inviteCode);

        // 2.转换为用户聚合根
        return po != null ? UserAggregate.create(UserConvertor.INSTANCE.toEntity(po)) : null;
    }

    @Override
    public boolean existsByTelephone(String telephone) {
        // 检查手机号是否已存在
        // 使用findByTelephone替代existsByTelephone，避免分库分表下的TooManyResultsException
        UserPO po = userDao.findByTelephone(telephone);
        return po != null;
    }

    @Override
    public boolean existsByInviteCode(String inviteCode) {
        // 检查邀请码是否已存在
        // 使用findByInviteCode替代existsByInviteCode，避免分库分表下的TooManyResultsException
        UserPO po = userDao.findByInviteCode(inviteCode);
        return po != null;
    }

    @Override
    public List<UserAggregate> pageQuery(UserPageQueryReq req) {
        // 1.执行分页查询（PageHelper已在Service层启动）
        List<UserPO> poList = userDao.selectByCondition(req);

        // 2.转换为聚合根列表
        return poList.stream()
                .map(userPO -> {
                    User user = UserConvertor.INSTANCE.toEntity(userPO);
                    return UserAggregate.create(user);
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean mightExistByInviteCode(String inviteCode) {
        // 使用布隆过滤器快速判断邀请码是否可能已存在
        // 注意：布隆过滤器有误判率，返回false表示一定不存在，返回true表示可能存在
        return inviteCodeBloomFilter.contains(inviteCode);
    }

    @Override
    public void addToInviteCodeFilter(String inviteCode) {
        // 将邀请码添加到布隆过滤器
        // 注意：此方法不执行数据库操作，仅更新布隆过滤器
        inviteCodeBloomFilter.add(inviteCode);
    }

    /**
     * 批量保存用户操作流水
     * 将用户操作流水列表批量保存到数据库
     *
     * @param userOperateStreamList 用户操作流水列表，不能为空
     */
    private void batchSaveOperateStream(List<UserOperateStream> userOperateStreamList) {
        for (UserOperateStream stream : userOperateStreamList) {
            UserOperateStreamPO streamPO = UserOperateStreamConvertor.INSTANCE.toPO(stream);
            userOperateStreamDao.insert(streamPO);
        }
    }
}
