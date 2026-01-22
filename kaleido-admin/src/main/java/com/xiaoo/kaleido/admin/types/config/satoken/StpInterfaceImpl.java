package com.xiaoo.kaleido.admin.types.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.xiaoo.kaleido.admin.application.query.IPermissionQueryService;
import com.xiaoo.kaleido.admin.application.query.IRoleQueryService;
import com.xiaoo.kaleido.admin.types.constant.SysConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final IPermissionQueryService IPermissionQueryService;
    private final IRoleQueryService IRoleQueryService;


    /**
     * 获取用户权限列表
     * 
     * @param loginId 登录ID
     * @param loginType 登录类型
     * @return 权限代码列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String adminId = String.valueOf(loginId);
        Set<String> roleCodeSet = IRoleQueryService.getRoleCodesId(adminId);
        if(roleCodeSet.contains(SysConstants.SUPER_ROLE_CODE)){
            return CollUtil.newArrayList(SysConstants.SUPER_ROLE_PERMISSION);
        }
        Set<String> permCodes = IPermissionQueryService.getPermCodesById(adminId);
        return CollUtil.newArrayList(permCodes);
    }

    /**
     * 获取用户角色列表
     * 根据登录ID获取用户的角色代码列表，用于权限验证
     * 
     * @param loginId 登录ID
     * @param loginType 登录类型
     * @return 角色代码列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String adminId = String.valueOf(loginId);
        Set<String> roles = IRoleQueryService.getRoleCodesId(adminId);
        return CollUtil.newArrayList(roles);
    }
}
