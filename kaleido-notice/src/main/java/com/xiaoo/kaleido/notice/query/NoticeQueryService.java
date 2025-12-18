package com.xiaoo.kaleido.sms.query;

import com.xiaoo.kaleido.sms.domain.adapter.repository.NoticeRecordRepository;
import com.xiaoo.kaleido.sms.domain.adapter.repository.NoticeTemplateRepository;
import com.xiaoo.kaleido.sms.types.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.sms.types.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.sms.domain.model.entity.NoticeTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知查询服务（应用层）
 * 负责通知相关的读操作
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeQueryService {


}
