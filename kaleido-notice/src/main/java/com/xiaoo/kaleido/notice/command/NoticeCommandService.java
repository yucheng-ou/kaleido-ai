package com.xiaoo.kaleido.notice.command;

import com.xiaoo.kaleido.api.admin.IRpcAdminSysService;
import com.xiaoo.kaleido.api.admin.response.DictResponse;
import com.xiaoo.kaleido.base.constant.DictConstant;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.domain.adapter.port.INoticeAdapterService;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeRepository;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeTemplateRepository;
import com.xiaoo.kaleido.notice.domain.factory.INoticeServiceFactory;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import com.xiaoo.kaleido.notice.domain.service.INoticeDomainService;
import com.xiaoo.kaleido.notice.domain.service.INoticeTemplateDomainService;
import com.xiaoo.kaleido.notice.types.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 通知命令服务（应用层）
 * 负责通知相关的写操作编排，包括事务管理、仓储协调和外部服务调用
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeCommandService {

    private final INoticeDomainService noticeDomainService;
    private final INoticeServiceFactory noticeServiceFactory;
    private final INoticeTemplateRepository noticeTemplateRepository;
    private final INoticeRepository noticeRepository;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcAdminSysService rpcAdminSysService;


    public String sendSmsVerifyCode(SendSmsVerifyCodeCommand command) {
        String mobile = command.getMobile().trim();
        
        // 查找验证码通知模板code
        Result<DictResponse> dictResult = rpcAdminSysService.getDictByCode(
                DictConstant.NoticeTypeDict.TYPE_CODE, 
                DictConstant.NoticeTypeDict.DICT_CODE_VERIFY_CODE
        );

        // 检查远程调用结果
        if (dictResult == null || !dictResult.getSuccess() || dictResult.getData() == null) {
            throw NoticeException.of(NoticeErrorCode.VERIFICATION_CODE_TEMPLATE_EMPTY);
        }

        String templateCode = dictResult.getData().getDictValue();

        // 根据code查找模板
        Optional<NoticeTemplateAggregate> templateOpt = noticeTemplateRepository.findByCode(templateCode);
        if (templateOpt.isEmpty()) {
            throw NoticeException.of(NoticeErrorCode.VERIFICATION_CODE_TEMPLATE_EMPTY);
        }

        NoticeTemplateAggregate template = templateOpt.get();
        
        // 生成验证码
        String verifyCode = NoticeAggregate.generateVerifyCode();

        // 渲染模板内容
        String noticeContent = template.render(verifyCode);

        // 创建通知聚合根
        NoticeAggregate smsVerifyCodeAggregate = noticeDomainService.createSmsVerifyCodeAggregate(mobile, noticeContent);

        // 发送通知
        INoticeAdapterService noticeAdapterService = noticeServiceFactory.getNoticeAdapterService(NoticeTypeEnum.SMS);
        noticeAdapterService.sendNotice(smsVerifyCodeAggregate.getTargetAddress(), noticeContent);
        
        // 保存通知记录
        noticeRepository.save(smsVerifyCodeAggregate);

        return verifyCode;
    }
}
