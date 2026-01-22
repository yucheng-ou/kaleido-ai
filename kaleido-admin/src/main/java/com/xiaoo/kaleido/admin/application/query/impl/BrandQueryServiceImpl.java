package com.xiaoo.kaleido.admin.application.query.impl;

import com.xiaoo.kaleido.admin.application.query.IBrandQueryService;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.wardrobe.IRpcBrandService;
import com.xiaoo.kaleido.api.wardrobe.response.BrandInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌查询服务实现
 * 负责品牌相关的读操作编排，通过RPC调用品牌服务
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrandQueryServiceImpl implements IBrandQueryService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcBrandService rpcBrandService;

    @Override
    public List<BrandInfoResponse> findAll() {
        Result<List<BrandInfoResponse>> result = rpcBrandService.getAllBrands();
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("查询所有品牌列表失败，错误信息: {}", result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public BrandInfoResponse findById(String brandId) {
        Result<BrandInfoResponse> result = rpcBrandService.getBrandById(brandId);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("根据ID查询品牌详情失败，品牌ID: {}, 错误信息: {}", brandId, result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        return result.getData();
    }
}
