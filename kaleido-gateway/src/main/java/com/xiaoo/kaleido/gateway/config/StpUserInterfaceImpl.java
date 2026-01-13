package com.xiaoo.kaleido.gateway.config;

import cn.dev33.satoken.stp.StpInterface;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StpUserInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //根据用户状态作为权限进行鉴权
        //用户活跃状态 冻结状态 删除状态 所调用的接口范围不同
        UserInfoResponse userInfoResponse = (UserInfoResponse) StpUserUtil.stpLogic.getSessionByLoginId(loginId).get((String) loginId);
        return List.of(userInfoResponse.getStatus());
    }


    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        //普通用户暂时不设计角色 只根据权限鉴权
        return List.of();
    }
}
