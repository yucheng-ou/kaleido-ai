package com.xiaoo.kaleido.admin.application.command;

import com.xiaoo.kaleido.api.notice.command.AddNoticeTemplateCommand;

/**
 * 通知命令服务接口
 *
 * @author ouyucheng
 * @date 2026/1/22
 */
public interface INoticeCommandService {

    /**
     * 添加通知模板
     *
     * @param command 添加通知模板命令
     * @return 模板ID
     */
    String addNoticeTemplate(AddNoticeTemplateCommand command);
}
