package com.xiaoo.kaleido.notice.command;

import com.xiaoo.kaleido.notice.domain.service.INoticeDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 通知命令服务（应用层）
 * 负责通知相关的写操作编排
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeCommandService {

    private final INoticeDomainService noticeDomainService;


}
