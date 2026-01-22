package com.xiaoo.kaleido.admin.application.command.impl;

import com.xiaoo.kaleido.admin.application.command.INoticeCommandService;
import com.xiaoo.kaleido.admin.types.exception.AdminException;
import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.AddNoticeTemplateCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * 通知命令服务（应用层）
 * 负责通知相关的写操作编排，通过RPC调用通知服务
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeCommandService implements INoticeCommandService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcNoticeService rpcNoticeService;

    /**
     * 添加通知模板
     *
     * @param command 添加通知模板命令
     * @return 模板ID
     */
    @Override
    public String addNoticeTemplate(AddNoticeTemplateCommand command) {
        Result<String> result = rpcNoticeService.addNoticeTemplate(command);
        if (!Boolean.TRUE.equals(result.getSuccess())) {
            log.error("添加通知模板失败，模板编码: {}, 错误信息: {}", command.getCode(), result.getMsg());
            throw AdminException.of(result.getCode(), result.getMsg());
        }
        log.info("添加通知模板成功，模板编码: {}", command.getCode());
        return result.getData();
    }
}
