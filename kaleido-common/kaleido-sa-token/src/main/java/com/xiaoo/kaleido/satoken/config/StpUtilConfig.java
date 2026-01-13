package com.xiaoo.kaleido.satoken.config;

import cn.dev33.satoken.config.SaTokenConfig;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;


public class StpUtilConfig {
    public void init() {
        SaTokenConfig adminConfig = new SaTokenConfig();
        adminConfig.setTokenName("kaleido-admin-token");
        adminConfig.setTimeout(2592000);
        adminConfig.setActiveTimeout(-1L);
        adminConfig.setIsConcurrent(false);
        adminConfig.setIsShare(true);
        adminConfig.setIsLog(true);
        adminConfig.setTokenStyle("random-64");
        StpAdminUtil.getStpLogic().setConfig(adminConfig);

        SaTokenConfig userConfig = new SaTokenConfig();
        userConfig.setTokenName("kaleido-user-token");
        userConfig.setTimeout(2592000);
        userConfig.setActiveTimeout(-1);
        userConfig.setIsConcurrent(false);
        userConfig.setIsShare(true);
        userConfig.setIsLog(true);
        userConfig.setTokenStyle("random-64");
        StpUserUtil.getStpLogic().setConfig(userConfig);
    }
}
