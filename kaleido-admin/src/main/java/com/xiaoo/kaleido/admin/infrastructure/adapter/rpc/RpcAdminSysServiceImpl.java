package com.xiaoo.kaleido.admin.infrastructure.adapter.rpc;

import com.xiaoo.kaleido.admin.application.query.dto.DictDTO;
import com.xiaoo.kaleido.admin.application.query.service.DictQueryService;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.api.admin.IRpcAdminSysService;
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
public class RpcAdminSysServiceImpl implements IRpcAdminSysService {

    private final DictQueryService dictQueryService;

    @Override
    public Result<DictResponse> getDictByCode(String typeCode, String dictCode) {
        try {
            log.debug("RPC查询字典，typeCode={}, dictCode={}", typeCode, dictCode);

            // 参数验证
            if (typeCode == null || typeCode.trim().isEmpty()) {
                return Result.error(AdminErrorCode.DICT_TYPE_CODE_EMPTY);
            }
            if (dictCode == null || dictCode.trim().isEmpty()) {
                return Result.error(AdminErrorCode.DICT_CODE_EMPTY);
            }

            // 查询字典
            DictDTO dictDTO = dictQueryService.findByTypeCodeAndDictCode(typeCode, dictCode);

            // 转换为响应对象
            DictResponse response = convertToResponse(dictDTO);

            log.info("RPC查询字典成功，typeCode={}, dictCode={}", typeCode, dictCode);
            return Result.success(response);

        } catch (Exception e) {
            log.error("RPC查询字典异常，typeCode={}, dictCode={}", typeCode, dictCode, e);
            return Result.error(AdminErrorCode.ADMIN_SYSTEM_ERROR);
        }
    }

    /**
     * 将字典DTO转换为响应对象
     */
    private DictResponse convertToResponse(DictDTO dto) {
        DictResponse response = new DictResponse();
        response.setId(dto.getId());
        response.setTypeCode(dto.getTypeCode());
        response.setTypeName(dto.getTypeName());
        response.setDictCode(dto.getDictCode());
        response.setDictName(dto.getDictName());
        response.setDictValue(dto.getDictValue());
        return response;
    }
}
