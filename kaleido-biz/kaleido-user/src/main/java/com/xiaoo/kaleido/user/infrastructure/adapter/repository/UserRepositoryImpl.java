package com.xiaoo.kaleido.user.infrastructure.adapter.repository;

import com.xiaoo.kaleido.user.domain.adapter.port.UserRepository;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserOperateStreamPO;
import com.xiaoo.kaleido.user.infrastructure.mapper.UserMapper;
import com.xiaoo.kaleido.user.infrastructure.mapper.UserOperateStreamMapper;
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

    private final UserMapper userMapper;
    private final UserOperateStreamMapper userOperateStreamMapper;

    @Override
    @Transactional
    public UserAggregate save(UserAggregate userAggregate) {
        User user = userAggregate.getUser();
        UserPO userPO = convertToPO(user);
        
        // 根据业务主键userId查找现有记录
        UserPO existingPO = userMapper.findByUserId(user.getId());
        if (existingPO != null) {
            // 设置数据库主键id，以便更新
            userPO.setId(existingPO.getId());
            userMapper.updateById(userPO);
        } else {
            userMapper.insert(userPO);
        }
        
        // 保存操作流水
        List<UserOperateStream> streams = userAggregate.getAndClearOperateStreams();
        for (UserOperateStream stream : streams) {
            UserOperateStreamPO streamPO = convertToPO(stream);
            userOperateStreamMapper.insert(streamPO);
        }
        
        return userAggregate;
    }

    @Override
    public Optional<UserAggregate> findById(String id) {
        return Optional.ofNullable(userMapper.findByUserId(id))
                .map(this::convertToAggregate);
    }

    @Override
    public Optional<UserAggregate> findByTelephone(String telephone) {
        UserPO po = userMapper.findByTelephone(telephone);
        return po != null ? Optional.of(convertToAggregate(po)) : Optional.empty();
    }

    @Override
    public Optional<UserAggregate> findByInviteCode(String inviteCode) {
        UserPO po = userMapper.findByInviteCode(inviteCode);
        return po != null ? Optional.of(convertToAggregate(po)) : Optional.empty();
    }

    @Override
    public boolean existsByTelephone(String telephone) {
        return userMapper.existsByTelephone(telephone);
    }

    @Override
    public boolean existsByInviteCode(String inviteCode) {
        return userMapper.existsByInviteCode(inviteCode);
    }

    @Override
    public boolean existsByNickName(String nickName) {
        return userMapper.existsByNickName(nickName);
    }

    @Override
    public UserAggregate findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_EXIST));
    }

    @Override
    public UserAggregate findByTelephoneOrThrow(String telephone) {
        return findByTelephone(telephone)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_EXIST));
    }

    @Override
    public UserAggregate findByInviteCodeOrThrow(String inviteCode) {
        return findByInviteCode(inviteCode)
                .orElseThrow(() -> new UserException(UserErrorCode.INVALID_INVITE_CODE));
    }

    /**
     * 将用户实体转换为持久化对象
     */
    private UserPO convertToPO(User user) {
        UserPO po = new UserPO();
        // 注意：UserPO的id是数据库主键（Long），在插入时由数据库生成，更新时使用existingPO的id
        // 业务主键userId存储在UserPO的userId字段中
        po.setUserId(user.getId());
        po.setTelephone(user.getTelephone());
        po.setPasswordHash(user.getPasswordHash());
        po.setNickName(user.getNickName());
        po.setStatus(user.getStatus() != null ? user.getStatus().getCode() : null);
        po.setInviteCode(user.getInviteCode());
        po.setInviterId(user.getInviterId());
        po.setLastLoginTime(user.getLastLoginTime());
        po.setAvatarUrl(user.getAvatarUrl());
        po.setCreatedAt(user.getCreatedAt());
        po.setUpdatedAt(user.getUpdatedAt());
        po.setDeleted(user.getDeleted());
        return po;
    }

    /**
     * 将操作流水实体转换为持久化对象
     */
    private UserOperateStreamPO convertToPO(UserOperateStream stream) {
        UserOperateStreamPO po = new UserOperateStreamPO();
        po.setUserId(stream.getUserId());
        po.setOperateType(stream.getOperateType() != null ? stream.getOperateType().name() : null);
        po.setOperateDetail(stream.getOperateDetail());
        po.setOperatorId(stream.getOperatorId());
        po.setOperateTime(stream.getOperateTime());
        po.setCreatedAt(stream.getCreatedAt());
        po.setUpdatedAt(stream.getUpdatedAt());
        po.setDeleted(stream.getDeleted());
        return po;
    }

    /**
     * 将持久化对象转换为用户聚合根
     */
    private UserAggregate convertToAggregate(UserPO po) {
        User user = User.builder()
                .telephone(po.getTelephone())
                .passwordHash(po.getPasswordHash())
                .nickName(po.getNickName())
                .status(com.xiaoo.kaleido.user.domain.constant.UserStatus.fromCode(po.getStatus()))
                .inviteCode(po.getInviteCode())
                .inviterId(po.getInviterId())
                .lastLoginTime(po.getLastLoginTime())
                .avatarUrl(po.getAvatarUrl())
                .build();

        user.setId(po.getUserId());
        user.setCreatedAt(po.getCreatedAt());
        user.setUpdatedAt(po.getUpdatedAt());
        user.setDeleted(po.getDeleted());
        
        // 加载操作流水（最近100条）
        List<UserOperateStreamPO> streamPOs = userOperateStreamMapper.findByUserIdWithLimit(po.getUserId(), 100);
        List<UserOperateStream> streams = streamPOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        
        UserAggregate aggregate = UserAggregate.create(user);
        aggregate.getOperateStreams().addAll(streams);
        return aggregate;
    }

    /**
     * 将操作流水持久化对象转换为实体
     */
    private UserOperateStream convertToEntity(UserOperateStreamPO po) {
        UserOperateStream stream = UserOperateStream.builder()
                .userId(po.getUserId())
                .operateType(com.xiaoo.kaleido.user.domain.constant.UserOperateType.valueOf(po.getOperateType()))
                .operateDetail(po.getOperateDetail())
                .operatorId(po.getOperatorId())
                .operateTime(po.getOperateTime())
                .build();
        stream.setId(po.getId() != null ? po.getId().toString() : null);
        stream.setCreatedAt(po.getCreatedAt());
        stream.setUpdatedAt(po.getUpdatedAt());
        stream.setDeleted(po.getDeleted());
        return stream;
    }
}
