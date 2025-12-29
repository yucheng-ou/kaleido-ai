package com.xiaoo.kaleido.user.infrastructure.adapter.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.base.response.PageResp;
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
import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户仓储实现（基础设施层）
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserAggregate userAggregate) {
        // 保存用户
        User user = userAggregate.getUser();
        UserPO userPO = UserConvertor.INSTANCE.toPO(user);
        userDao.insert(userPO);

        //保存用户操作流水
        batchSaveOperateStream(userAggregate.getOperateStreams());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserAggregate userAggregate) {
        // 修改用户信息
        User user = userAggregate.getUser();
        UserPO userPO = UserConvertor.INSTANCE.toPO(user);
        userDao.updateById(userPO);

        //保存用户操作流水
        batchSaveOperateStream(userAggregate.getOperateStreams());
    }

    @Override
    public Optional<UserAggregate> findById(String id) {
        UserPO userPO = userDao.findByUserId(id);
        if (userPO == null) {
            return Optional.empty();
        }
        
        // 转换用户实体
        User user = UserConvertor.INSTANCE.toEntity(userPO);
        
        // 创建聚合根（不加载操作流水，按需加载）
        UserAggregate aggregate = UserAggregate.create(user);
        return Optional.of(aggregate);
    }

    @Override
    public Optional<UserAggregate> findUserAndStreamById(String id) {
        UserPO userPO = userDao.findByUserId(id);
        if (userPO == null) {
            return Optional.empty();
        }
        
        // 转换用户实体
        User user = UserConvertor.INSTANCE.toEntity(userPO);
        
        // 加载操作流水（最近100条）
        List<UserOperateStreamPO> streamPOs = userOperateStreamDao.findByUserIdWithLimit(userPO.getUserId(), 100);
        List<UserOperateStream> streams = streamPOs.stream()
                .map(UserOperateStreamConvertor.INSTANCE::toEntity)
                .toList();

        UserAggregate aggregate = UserAggregate.create(user);
        aggregate.getOperateStreams().addAll(streams);
        return Optional.of(aggregate);
    }

    @Override
    public Optional<UserAggregate> findByTelephone(String telephone) {
        UserPO po = userDao.findByTelephone(telephone);
        return po != null ? Optional.of(UserAggregate.create(UserConvertor.INSTANCE.toEntity(po))) : Optional.empty();
    }

    @Override
    public Optional<UserAggregate> findByInviteCode(String inviteCode) {
        UserPO po = userDao.findByInviteCode(inviteCode);
        return po != null ? Optional.of(UserAggregate.create(UserConvertor.INSTANCE.toEntity(po))) : Optional.empty();
    }

    @Override
    public boolean existsByTelephone(String telephone) {
        return userDao.existsByTelephone(telephone);
    }

    @Override
    public boolean existsByInviteCode(String inviteCode) {
        return userDao.existsByInviteCode(inviteCode);
    }

    @Override
    public UserAggregate findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(() -> UserException.of(UserErrorCode.USER_NOT_EXIST));
    }

    @Override
    public PageResp<UserAggregate> pageQuery(UserPageQueryReq req) {
        // 使用PageHelper进行分页
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        
        // 执行查询
        List<UserPO> userPOList = userDao.selectByCondition(req);
        
        // 转换为PageInfo获取分页信息
        PageInfo<UserPO> pageInfo = new PageInfo<>(userPOList);
        
        // 转换为聚合根列表
        List<UserAggregate> aggregateList = userPOList.stream()
                .map(userPO -> {
                    User user = UserConvertor.INSTANCE.toEntity(userPO);
                    return UserAggregate.create(user);
                })
                .collect(Collectors.toList());
        
        // 构建分页响应
        return PageResp.success(
                aggregateList,
                pageInfo.getTotal(),
                pageInfo.getPageNum(),
                pageInfo.getPageSize()
        );
    }

    /**
     * 批量保存用户操作流水
     * @param userOperateStreamList 用户操作流水列表
     */
    private void batchSaveOperateStream(List<UserOperateStream> userOperateStreamList){
        for (UserOperateStream stream : userOperateStreamList) {
            UserOperateStreamPO streamPO = UserOperateStreamConvertor.INSTANCE.toPO(stream);
            userOperateStreamDao.insert(streamPO);
        }
    }
}
