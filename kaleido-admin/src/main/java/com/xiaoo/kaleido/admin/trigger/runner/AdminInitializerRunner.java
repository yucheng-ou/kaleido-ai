package com.xiaoo.kaleido.admin.trigger.runner;

import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IAdminRepository;
import com.xiaoo.kaleido.admin.domain.user.adapter.repository.IRoleRepository;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.AdminAggregate;
import com.xiaoo.kaleido.admin.domain.user.model.aggregate.RoleAggregate;
import com.xiaoo.kaleido.admin.types.constant.SysConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动注册默认超级管理员 避免项目上线后没有用户 无法配置权限
 * 个人感觉直接写在初始化sql里面更好 因为使用ApplicationRunner每次项目启动都要查一次 但其实只有第一次是有用的
 * 这里只是想在项目里用一下ApplicationRunner
 *
 * @author ouyucheng
 * @date 2025/12/25
 */

@Component
@Slf4j
public class AdminInitializerRunner implements ApplicationRunner {

    private final IRoleRepository roleRepository;
    private final IAdminRepository adminRepository;

    public AdminInitializerRunner(IRoleRepository roleRepository, IAdminRepository adminRepository) {
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        initSuperAdmin();
    }

    private void initSuperAdmin() {
        try {
            // 1. 检查并创建超级管理员角色
            RoleAggregate superRole = roleRepository.findByCode(SysConstants.SUPER_ROLE_CODE);
            if (superRole == null) {
                superRole = RoleAggregate.create(
                        SysConstants.SUPER_ROLE_CODE,
                        SysConstants.SUPER_ROLE_NAME,
                        SysConstants.SUPER_ROLE_DESC
                );
                roleRepository.save(superRole);
            }

            // 2. 检查并创建超级管理员用户
            AdminAggregate superAdmin = adminRepository.findByMobile(SysConstants.SUPER_ADMIN_MOBILE);
            boolean isNewAdmin = false;
            if (superAdmin == null) {
                superAdmin = AdminAggregate.create(SysConstants.SUPER_ADMIN_MOBILE);
                superAdmin.addRole(superRole.getId());
                adminRepository.save(superAdmin);
                isNewAdmin = true;
            }

            log.info("超级管理员初始化完成,管理员id:{},管理员手机号：{},是否为新创建:{}",
                    superAdmin.getId(), superAdmin.getMobile(), isNewAdmin);
        } catch (Exception e) {
            log.error("超级管理员创建失败", e);
        }
    }
}
