package com.xiaoo.kaleido.user.infrastructure.adapter.repository;

import com.xiaoo.kaleido.user.domain.adapter.repository.UserOperateStreamRepository;
import com.xiaoo.kaleido.user.domain.constant.UserOperateType;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserOperateStreamAggregate;
import com.xiaoo.kaleido.user.domain.model.entity.UserOperateStream;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserOperateStreamPO;
import com.xiaoo.kaleido.user.infrastructure.mapper.UserOperateStreamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户操作流水仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserOperateStreamRepositoryImpl implements UserOperateStreamRepository {

    private final UserOperateStreamMapper userOperateStreamMapper;

    @Override
    public UserOperateStreamAggregate save(UserOperateStreamAggregate userOperateStreamAggregate) {
        UserOperateStream stream = userOperateStreamAggregate.getUserOperateStream();
        UserOperateStreamPO po = convertToPO(stream);
        
        if (stream.getId() != null && !stream.getId().trim().isEmpty()) {
            // 如果已有ID，则更新
            UserOperateStreamPO existingPO = userOperateStreamMapper.selectById(stream.getId());
            if (existingPO != null) {
                po.setId(existingPO.getId());
                userOperateStreamMapper.updateById(po);
            } else {
                userOperateStreamMapper.insert(po);
                // 设置生成的ID回聚合根
                userOperateStreamAggregate.setId(po.getId().toString());
            }
        } else {
            userOperateStreamMapper.insert(po);
            // 设置生成的ID回聚合根
            userOperateStreamAggregate.setId(po.getId().toString());
        }
        
        return userOperateStreamAggregate;
    }

    @Override
    public UserOperateStreamAggregate findById(String id) {
        UserOperateStreamPO po = userOperateStreamMapper.selectById(Long.valueOf(id));
        return po != null ? convertToAggregate(po) : null;
    }

    @Override
    public List<UserOperateStreamAggregate> findByUserId(String userId) {
        List<UserOperateStreamPO> pos = userOperateStreamMapper.findByUserId(userId);
        return pos.stream()
                .map(this::convertToAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOperateStreamAggregate> findByUserIdAndOperateType(String userId, String operateType) {
        List<UserOperateStreamPO> pos = userOperateStreamMapper.findByUserIdAndOperateType(userId, operateType);
        return pos.stream()
                .map(this::convertToAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOperateStreamAggregate> findByOperatorId(String operatorId) {
        List<UserOperateStreamPO> pos = userOperateStreamMapper.findByOperatorId(operatorId);
        return pos.stream()
                .map(this::convertToAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOperateStreamAggregate> findByUserIdWithPage(String userId, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<UserOperateStreamPO> pos = userOperateStreamMapper.findByUserIdWithPage(userId, offset, pageSize);
        return pos.stream()
                .map(this::convertToAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public long countByUserId(String userId) {
        return userOperateStreamMapper.countByUserId(userId);
    }

    /**
     * 将用户操作流水实体转换为持久化对象
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
     * 将持久化对象转换为用户操作流水聚合根
     */
    private UserOperateStreamAggregate convertToAggregate(UserOperateStreamPO po) {
        UserOperateType operateType = null;
        if (po.getOperateType() != null && !po.getOperateType().trim().isEmpty()) {
            try {
                operateType = UserOperateType.valueOf(po.getOperateType());
            } catch (IllegalArgumentException e) {
                log.warn("无效的操作类型: {}, 使用null代替", po.getOperateType());
                operateType = null;
            }
        }
        
        UserOperateStream stream = UserOperateStream.builder()
                .userId(po.getUserId())
                .operateType(operateType)
                .operateDetail(po.getOperateDetail())
                .operatorId(po.getOperatorId())
                .operateTime(po.getOperateTime())
                .build();
        
        if (po.getId() != null) {
            stream.setId(po.getId().toString());
        }
        stream.setCreatedAt(po.getCreatedAt());
        stream.setUpdatedAt(po.getUpdatedAt());
        stream.setDeleted(po.getDeleted());
        
        return new UserOperateStreamAggregate(stream);
    }
}
