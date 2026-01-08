package com.xiaoo.kaleido.api.admin.dict;

import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.constraints.NotBlank;

/**
 * 管理后台RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
public interface IRpcAdminDictService {

    /**
     * 根据字典类型编码和字典编码查询字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典信息
     */
    Result<DictResponse> getDictByCode(@NotBlank String typeCode, @NotBlank String dictCode);

}
