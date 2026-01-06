package com.xiaoo.kaleido.notice.infrastructure.adapter.repository;

import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeTemplateRepository;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;
import com.xiaoo.kaleido.notice.infrastructure.adapter.repository.convertor.NoticeTemplateConvertor;
import com.xiaoo.kaleido.notice.infrastructure.dao.NoticeTemplateDao;
import com.xiaoo.kaleido.notice.infrastructure.dao.po.NoticeTemplatePO;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 通知模板仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class NoticeTemplateRepositoryImpl implements INoticeTemplateRepository {

    private final NoticeTemplateDao noticeTemplateDao;

    @Override
    public Optional<NoticeTemplateAggregate> findById(String id) {
        NoticeTemplatePO templatePO = noticeTemplateDao.selectById(id);
        if (templatePO == null) {
            return Optional.empty();
        }
        return Optional.of(NoticeTemplateConvertor.INSTANCE.toAggregate(templatePO));
    }

    @Override
    public Optional<NoticeTemplateAggregate> findByCode(String code) {
        NoticeTemplatePO templatePO = noticeTemplateDao.findByTemplateCode(code);
        if (templatePO == null) {
            return Optional.empty();
        }
        return Optional.of(NoticeTemplateConvertor.INSTANCE.toAggregate(templatePO));
    }

    @Override
    public void save(NoticeTemplateAggregate template) {
        NoticeTemplatePO templatePO = NoticeTemplateConvertor.INSTANCE.toPO(template);
        noticeTemplateDao.insert(templatePO);
    }

    @Override
    public void deleteById(String id) {
        noticeTemplateDao.deleteById(id);
    }

    @Override
    public NoticeTemplateAggregate findByIdOrThrow(String id) {
        return findById(id)
                .orElseThrow(() -> NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_NOT_FOUND));
    }

    @Override
    public NoticeTemplateAggregate findByCodeOrThrow(String code) {
        return findByCode(code)
                .orElseThrow(() -> NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_NOT_FOUND));
    }

    @Override
    public boolean existsByCode(String code) {
        return noticeTemplateDao.existsByTemplateCode(code);
    }

    /**
     * 根据通知类型查找模板列表
     *
     * @param noticeType 通知类型
     * @return 模板列表
     */
    public List<NoticeTemplateAggregate> findByNoticeType(String noticeType) {
        List<NoticeTemplatePO> templatePOs = noticeTemplateDao.findByNoticeType(noticeType);
        return templatePOs.stream()
                .map(NoticeTemplateConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    /**
     * 根据业务类型查找模板列表
     *
     * @param businessType 业务类型
     * @return 模板列表
     */
    public List<NoticeTemplateAggregate> findByBusinessType(String businessType) {
        List<NoticeTemplatePO> templatePOs = noticeTemplateDao.findByBusinessType(businessType);
        return templatePOs.stream()
                .map(NoticeTemplateConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeTemplateAggregate> pageQuery(NoticeTemplatePageQueryReq req) {
        // 执行分页查询（PageHelper已在Service层启动）
        List<NoticeTemplatePO> poList = noticeTemplateDao.selectByCondition(req);

        // 转换为聚合根列表
        return poList.stream()
                .map(NoticeTemplateConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    /**
     * 查找启用的模板列表
     *
     * @return 启用的模板列表
     */
    public List<NoticeTemplateAggregate> findEnabledTemplates() {
        List<NoticeTemplatePO> templatePOs = noticeTemplateDao.findEnabledTemplates();
        return templatePOs.stream()
                .map(NoticeTemplateConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }
}
