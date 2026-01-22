package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.wardrobe.command.CreateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateBrandCommand;

/**
 * 品牌命令服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface IBrandCommandService {

    /**
     * 创建品牌
     *
     * @param command 创建品牌命令
     * @return 创建的品牌ID
     */
    String createBrand(CreateBrandCommand command);

    /**
     * 更新品牌信息
     *
     * @param brandId 品牌ID
     * @param command 更新品牌命令
     */
    void updateBrand(String brandId, UpdateBrandCommand command);

    /**
     * 删除品牌（逻辑删除）
     *
     * @param brandId 品牌ID
     */
    void deleteBrand(String brandId);
}
