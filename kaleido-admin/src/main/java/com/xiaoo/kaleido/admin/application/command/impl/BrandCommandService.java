package com.xiaoo.kaleido.admin.application.command.impl;

import com.xiaoo.kaleido.admin.application.command.IBrandCommandService;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.wardrobe.IRpcBrandService;
import com.xiaoo.kaleido.api.wardrobe.command.CreateBrandCommand;
import com.xiaoo.kaleido.api.wardrobe.command.UpdateBrandCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * 品牌命令服务（应用层）
 * 负责品牌相关的写操作编排，通过RPC调用品牌服务
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandCommandService implements IBrandCommandService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcBrandService rpcBrandService;

    /**
     * 创建品牌
     *
     * @param command 创建品牌命令
     * @return 创建的品牌ID
     */
    @Override
    public String createBrand(CreateBrandCommand command) {
        Result<String> result = rpcBrandService.createBrand(command);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("创建品牌失败，品牌名称: {}, 错误信息: {}", command.getName(), result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        log.info("创建品牌成功，品牌名称: {}", command.getName());
        return result.getData();
    }

    /**
     * 更新品牌信息
     *
     * @param brandId 品牌ID
     * @param command 更新品牌命令
     */
    @Override
    public void updateBrand(String brandId, UpdateBrandCommand command) {
        Result<Void> result = rpcBrandService.updateBrand(brandId, command);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("更新品牌信息失败，品牌ID: {}, 错误信息: {}", brandId, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        log.info("更新品牌信息成功，品牌ID: {}", brandId);
    }

    /**
     * 删除品牌（逻辑删除）
     *
     * @param brandId 品牌ID
     */
    @Override
    public void deleteBrand(String brandId) {
        Result<Void> result = rpcBrandService.deleteBrand(brandId);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("删除品牌失败，品牌ID: {}, 错误信息: {}", brandId, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        log.info("删除品牌成功，品牌ID: {}", brandId);
    }
}
