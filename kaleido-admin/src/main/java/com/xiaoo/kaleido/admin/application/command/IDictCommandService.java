package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.admin.dict.command.AddDictCommand;
import com.xiaoo.kaleido.api.admin.dict.command.UpdateDictCommand;

/**
 * 字典命令服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface IDictCommandService {

    /**
     * 创建字典
     *
     * @param command 添加字典命令
     * @return 字典ID
     */
    String createDict(AddDictCommand command);

    /**
     * 更新字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     * @param command 更新字典命令
     */
    void updateDict(String typeCode, String dictCode, UpdateDictCommand command);

    /**
     * 删除字典
     *
     * @param typeCode 字典类型编码
     * @param dictCode 字典编码
     */
    void deleteDict(String typeCode, String dictCode);
}
