package com.xiaoo.kaleido.admin.trigger.rpc;

import com.xiaoo.kaleido.admin.application.query.IDictQueryService;
import com.xiaoo.kaleido.api.admin.dict.IRpcAdminDictService;
import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 管理后台RPC服务实现
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Slf4j
@Service
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
@Validated
public class RpcAdminDictServiceImpl implements IRpcAdminDictService {

    private final IDictQueryService IDictQueryService;

    @Override
    public Result<DictResponse> getDictByCode(String typeCode, String dictCode) {

        // 查询字典
        DictResponse dictResponse = IDictQueryService.findByTypeCodeAndDictCode(typeCode, dictCode);

        log.info("RPC查询字典成功，typeCode={}, dictCode={}", typeCode, dictCode);
        return Result.success(dictResponse);
    }
}
