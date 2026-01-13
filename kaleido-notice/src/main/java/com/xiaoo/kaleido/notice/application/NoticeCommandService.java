package com.xiaoo.kaleido.notice.application;

import cn.hutool.json.JSONUtil;
import com.xiaoo.kaleido.api.admin.dict.IRpcAdminDictService;
import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.api.notice.command.AddNoticeTemplateCommand;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.notice.domain.service.INoticeTemplateDomainService;
import com.xiaoo.kaleido.base.constant.DictConstant;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.domain.adapter.port.INoticeAdapterService;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeRepository;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeTemplateRepository;
import com.xiaoo.kaleido.notice.domain.factory.INoticeServiceFactory;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import com.xiaoo.kaleido.notice.domain.service.INoticeDomainService;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final INoticeTemplateDomainService noticeTemplateDomainService;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcAdminDictService rpcAdminSysService;


    @Transactional(rollbackFor = Exception.class)
    public String sendSmsVerifyCode(SendSmsVerifyCodeCommand command) {
        // 1.参数处理
        String mobile = command.getMobile().trim();

        // 2.根据目标类型确定字典编码
        String dictCode = switch (command.getTargetType()) {
            case USER -> DictConstant.NoticeTemplateDict.DICT_CODE_VERIFY_CODE_USER;
            case ADMIN -> DictConstant.NoticeTemplateDict.DICT_CODE_VERIFY_CODE_ADMIN;
        };

        // 3.远程调用获取验证码通知模板编码
        Result<DictResponse> dictResult = rpcAdminSysService.getDictByCode(DictConstant.NoticeTemplateDict.TYPE_CODE, dictCode);

        // 4.检查远程调用结果
        if (dictResult == null || !dictResult.getSuccess() || dictResult.getData() == null) {
            throw NoticeException.of(NoticeErrorCode.VERIFICATION_CODE_TEMPLATE_EMPTY);
        }

        String templateCode = dictResult.getData().getDictValue();

        // 5.根据编码查找模板
        Optional<NoticeTemplateAggregate> templateOpt = noticeTemplateRepository.findByCode(templateCode);
        if (templateOpt.isEmpty()) {
            throw NoticeException.of(NoticeErrorCode.VERIFICATION_CODE_TEMPLATE_EMPTY);
        }

        NoticeTemplateAggregate template = templateOpt.get();

        // 6.生成验证码
        String verifyCode = NoticeAggregate.generateVerifyCode();

        // 7.渲染模板内容
        String noticeContent = template.render(verifyCode);

        // 8.创建通知聚合根
        NoticeAggregate smsVerifyCodeAggregate = noticeDomainService.createSmsVerifyCodeAggregate(mobile, noticeContent, command.getTargetType());

        // 9.发送通知
        INoticeAdapterService noticeAdapterService = noticeServiceFactory.getNoticeAdapterService(NoticeTypeEnum.SMS);
        Result<Void> sendResult = noticeAdapterService.sendNotice(smsVerifyCodeAggregate.getTargetAddress(), noticeContent);

        // 10.保存通知记录
        if (sendResult.getSuccess()) {
            smsVerifyCodeAggregate.markAsSuccess(JSONUtil.toJsonStr(sendResult));
        } else {
            smsVerifyCodeAggregate.markAsFailed(JSONUtil.toJsonStr(sendResult));
        }
        noticeRepository.save(smsVerifyCodeAggregate);

        // 11.保存验证码到缓存
        noticeRepository.cacheVerifyCode(command.getTargetType(), command.getMobile(), verifyCode);

        log.info("短信验证码发送成功，手机号: {}, 目标类型: {}, 验证码: {}", mobile, command.getTargetType(), verifyCode);
        return verifyCode;
    }

    public Boolean checkSmsVerifyCode(CheckSmsVerifyCodeCommand command) {
        //直接从缓存取出验证码进行校验
        return noticeRepository.checkVerifyCode(command.getTargetType(), command.getMobile(), command.getVerifyCode());
    }

    public String addNoticeTemplate(AddNoticeTemplateCommand command) {
        // 1.调用领域服务处理业务逻辑
        NoticeTemplateAggregate template = noticeTemplateDomainService.createNoticeTemplate(command.getName(), command.getCode(), command.getContent());

        // 2.保存模板
        noticeTemplateRepository.save(template);

        log.info("通知模板添加成功，模板ID: {}, 模板编码: {}", template.getId(), template.getCode());
        return template.getId();
    }
}
