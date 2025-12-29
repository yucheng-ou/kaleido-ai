package com.xiaoo.kaleido.notice.trigger.rpc;

import com.xiaoo.kaleido.api.notice.IRpcNoticeService;
import com.xiaoo.kaleido.api.notice.command.CheckSmsVerifyCodeCommand;
import com.xiaoo.kaleido.api.notice.command.SendSmsVerifyCodeCommand;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.notice.command.NoticeCommandService;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeRepository;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.types.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * 通知RPC服务实现
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(
        version = RpcConstants.DUBBO_VERSION,
        group = RpcConstants.DUBBO_GROUP,
        timeout = RpcConstants.DEFAULT_TIMEOUT
)
@Tag(name = "通知RPC服务", description = "通知相关的RPC接口")
public class RpcNoticeServiceImpl implements IRpcNoticeService {

    private final NoticeCommandService noticeCommandService;
    private final INoticeRepository noticeRepository;

    @Override
    @Operation(summary = "生成并发送短信验证码", description = "生成短信验证码并发送到指定手机号")
    public Result<String> generateAndSendSmsVerifyCode(
            @Valid
            @Parameter(description = "发送短信验证码命令")
            SendSmsVerifyCodeCommand command) {
        return Result.success(noticeCommandService.sendSmsVerifyCode(command));
    }

    @Override
    @Operation(summary = "校验短信验证码", description = "校验短信验证码是否正确且未过期")
    public Result<Boolean> checkSmsVerifyCode(
            @Valid
            @Parameter(description = "校验短信验证码命令")
            CheckSmsVerifyCodeCommand command) {
        String mobile = command.getMobile().trim();
        String verifyCode = command.getVerifyCode().trim();
        
        // 查找该手机号最近发送的验证码通知
        List<NoticeAggregate> notices = noticeRepository.findByTarget(mobile);
        
        // 过滤出验证码通知（可以根据业务类型进一步过滤）
        Optional<NoticeAggregate> latestNotice = notices.stream()
                .filter(notice -> notice.getContent() != null && notice.getContent().contains(verifyCode))
                .filter(notice -> notice.getStatus() == NoticeStatusEnum.SUCCESS)
                .filter(notice -> notice.getSentAt() != null)
                .filter(notice -> notice.getSentAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
                        .isAfter(java.time.LocalDateTime.now().minusMinutes(5))) // 5分钟内有效
                .max((n1, n2) -> n2.getSentAt().compareTo(n1.getSentAt()));
        
        if (latestNotice.isPresent()) {
            // 验证码正确且有效
            return Result.success(true);
        } else {
            // 验证码错误或已过期
            return Result.success(false);
        }
    }
}
