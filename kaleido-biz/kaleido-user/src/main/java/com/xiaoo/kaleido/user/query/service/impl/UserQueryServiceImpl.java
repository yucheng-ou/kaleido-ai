package com.xiaoo.kaleido.user.query.service.impl;

import com.xiaoo.kaleido.user.domain.constant.UserStatus;
import com.xiaoo.kaleido.user.infrastructure.dao.po.UserPO;
import com.xiaoo.kaleido.user.infrastructure.mapper.UserMapper;
import com.xiaoo.kaleido.user.infrastructure.mapper.UserMapperExt;
import com.xiaoo.kaleido.user.query.convertor.UserConvertor;
import com.xiaoo.kaleido.user.query.dto.UserDTO;
import com.xiaoo.kaleido.user.query.request.PageUserQueryRequest;
import com.xiaoo.kaleido.user.query.request.UserQueryRequest;
import com.xiaoo.kaleido.user.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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

    private final UserMapper userMapper;
    private final UserMapperExt userMapperExt;

    @Override
    public UserDTO findById(String userId) {
        UserPO userPO = userMapper.findByUserId(userId);
        if (userPO == null) {
            return null;
        }
        return UserConvertor.INSTANCE.toDTO(userPO);
    }

    @Override
    public UserDTO findByTelephone(String telephone) {
        UserPO userPO = userMapper.findByTelephone(telephone);
        if (userPO == null) {
            return null;
        }
        return UserConvertor.INSTANCE.toDTO(userPO);
    }

    @Override
    public UserDTO findByInviteCode(String inviteCode) {
        UserPO userPO = userMapper.findByInviteCode(inviteCode);
        if (userPO == null) {
            return null;
        }
        return UserConvertor.INSTANCE.toDTO(userPO);
    }

    @Override
    public List<UserDTO> findByNickNameLike(String nickName) {
        List<UserPO> userPOList = userMapperExt.findByNickNameLike(nickName);
        return UserConvertor.INSTANCE.toDTOList(userPOList);
    }

    @Override
    public List<UserDTO> findByStatus(UserStatus status) {
        List<UserPO> userPOList = userMapperExt.findByStatus(status.getCode());
        return UserConvertor.INSTANCE.toDTOList(userPOList);
    }

    @Override
    public List<UserDTO> findAll(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<UserPO> userPOList = userMapperExt.findAllWithPage(offset, pageSize);
        return UserConvertor.INSTANCE.toDTOList(userPOList);
    }

    @Override
    public List<UserDTO> findByInviterId(String inviterId) {
        List<UserPO> userPOList = userMapperExt.findByInviterId(inviterId);
        return UserConvertor.INSTANCE.toDTOList(userPOList);
    }

    @Override
    public long count() {
        return userMapperExt.countAll();
    }

    @Override
    public long countByStatus(UserStatus status) {
        return userMapperExt.countByStatus(status.getCode());
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
    public List<UserDTO> queryUsers(com.xiaoo.kaleido.user.query.request.UserQueryRequest queryRequest) {
        // 简化实现：根据条件组合查询
        // 实际项目中应使用动态SQL构建复杂查询
        if (queryRequest.getTelephone() != null && !queryRequest.getTelephone().trim().isEmpty()) {
            UserDTO user = findByTelephone(queryRequest.getTelephone());
            return user != null ? List.of(user) : List.of();
        }
        
        if (queryRequest.getNickName() != null && !queryRequest.getNickName().trim().isEmpty()) {
            return findByNickNameLike(queryRequest.getNickName());
        }
        
        if (queryRequest.getStatus() != null) {
            return findByStatus(queryRequest.getStatus());
        }
        
        if (queryRequest.getInviterId() != null && !queryRequest.getInviterId().trim().isEmpty()) {
            return findByInviterId(queryRequest.getInviterId());
        }
        
        // 默认返回空列表
        return List.of();
    }

    @Override
    public com.xiaoo.kaleido.base.response.PageResp<UserDTO> pageQueryUsers(
            com.xiaoo.kaleido.user.query.request.PageUserQueryRequest queryRequest) {
        
        // 简化实现：先获取所有数据再分页（实际项目中应使用数据库分页）
        List<UserDTO> allUsers = queryUsers(queryRequest);
        
        int pageNum = queryRequest.getPageNum() != null ? queryRequest.getPageNum() : 1;
        int pageSize = queryRequest.getPageSize() != null ? queryRequest.getPageSize() : 10;
        int total = allUsers.size();
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);
        
        if (fromIndex >= total) {
            return createEmptyPageResp(pageNum, pageSize);
        }
        
        List<UserDTO> pageList = allUsers.subList(fromIndex, toIndex);
        
        com.xiaoo.kaleido.base.response.PageResp<UserDTO> pageResp = 
            new com.xiaoo.kaleido.base.response.PageResp<>();
        pageResp.setList(pageList);
        pageResp.setTotal(total);
        pageResp.setPageNum(pageNum);
        pageResp.setPageSize(pageSize);
        
        return pageResp;
    }

    private com.xiaoo.kaleido.base.response.PageResp<UserDTO> createEmptyPageResp(int pageNum, int pageSize) {
        com.xiaoo.kaleido.base.response.PageResp<UserDTO> pageResp = 
            new com.xiaoo.kaleido.base.response.PageResp<>();
        pageResp.setList(List.of());
        pageResp.setTotal(0);
        pageResp.setPageNum(pageNum);
        pageResp.setPageSize(pageSize);
        return pageResp;
    }
}
