package com.xiaoo.kaleido.admin.trigger.rpc;

import com.xiaoo.kaleido.admin.application.query.DictQueryService;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.api.admin.IRpcAdminDictService;
import com.xiaoo.kaleido.api.admin.response.DictResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * 管理后台RPC服务实现
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Slf4j
@Service
@RequiredArgsConstructor
@DubboService(
        version = RpcConstants.DUBBO_VERSION,
        group = RpcConstants.DUBBO_GROUP,
        timeout = RpcConstants.DEFAULT_TIMEOUT
)
public class RpcAdminDictServiceImpl implements IRpcAdminDictService {

    private final DictQueryService dictQueryService;

    @Override
    public Result<DictResponse> getDictByCode(String typeCode, String dictCode) {

            // 参数验证
            if (typeCode == null || typeCode.trim().isEmpty()) {
                return Result.error(AdminErrorCode.DICT_TYPE_CODE_EMPTY);
            }
            if (dictCode == null || dictCode.trim().isEmpty()) {
                return Result.error(AdminErrorCode.DICT_CODE_EMPTY);
            }

            // 查询字典
            DictResponse dictResponse = dictQueryService.findByTypeCodeAndDictCode(typeCode, dictCode);

            log.info("RPC查询字典成功，typeCode={}, dictCode={}", typeCode, dictCode);
            return Result.success(dictResponse);
    }
}
