package com.xiaoo.kaleido.api.admin;

import com.xiaoo.kaleido.api.admin.response.DictResponse;
import com.xiaoo.kaleido.base.result.Result;

/**
 * 管理后台RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
public interface IRpcAdminSysService {

    /**
     * 根据字典类型编码和字典编码查询字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @return 字典信息
     */
    Result<DictResponse> getDictByCode(String typeCode, String dictCode);

}
