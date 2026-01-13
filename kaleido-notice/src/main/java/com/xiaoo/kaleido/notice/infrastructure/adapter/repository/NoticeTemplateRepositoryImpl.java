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
        // 1.调用DAO层查询持久化对象
        NoticeTemplatePO templatePO = noticeTemplateDao.selectById(id);
        
        // 2.判断查询结果
        if (templatePO == null) {
            return Optional.empty();
        }
        
        // 3.转换为聚合根并返回
        return Optional.of(NoticeTemplateConvertor.INSTANCE.toAggregate(templatePO));
    }

    @Override
    public Optional<NoticeTemplateAggregate> findByCode(String code) {
        // 1.调用DAO层根据编码查询持久化对象
        NoticeTemplatePO templatePO = noticeTemplateDao.findByTemplateCode(code);
        
        // 2.判断查询结果
        if (templatePO == null) {
            return Optional.empty();
        }
        
        // 3.转换为聚合根并返回
        return Optional.of(NoticeTemplateConvertor.INSTANCE.toAggregate(templatePO));
    }

    @Override
    public void save(NoticeTemplateAggregate template) {
        // 1.将聚合根转换为持久化对象
        NoticeTemplatePO templatePO = NoticeTemplateConvertor.INSTANCE.toPO(template);
        
        // 2.调用DAO层插入数据
        noticeTemplateDao.insert(templatePO);
    }

    @Override
    public void deleteById(String id) {
        // 1.调用DAO层删除记录
        noticeTemplateDao.deleteById(id);
    }

    @Override
    public NoticeTemplateAggregate findByIdOrThrow(String id) {
        // 1.调用findById方法查询
        // 2.如果不存在则抛出异常
        return findById(id)
                .orElseThrow(() -> NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_NOT_FOUND));
    }

    @Override
    public NoticeTemplateAggregate findByCodeOrThrow(String code) {
        // 1.调用findByCode方法查询
        // 2.如果不存在则抛出异常
        return findByCode(code)
                .orElseThrow(() -> NoticeException.of(NoticeErrorCode.NOTICE_TEMPLATE_NOT_FOUND));
    }

    @Override
    public boolean existsByCode(String code) {
        // 1.调用DAO层检查编码是否存在
        return noticeTemplateDao.existsByTemplateCode(code);
    }

    /**
     * 根据通知类型查找模板列表
     *
     * @param noticeType 通知类型
     * @return 模板列表
     */
    public List<NoticeTemplateAggregate> findByNoticeType(String noticeType) {
        // 1.调用DAO层根据通知类型查询持久化对象列表
        List<NoticeTemplatePO> templatePOs = noticeTemplateDao.findByNoticeType(noticeType);
        
        // 2.转换为聚合根列表并返回
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
        // 1.调用DAO层根据业务类型查询持久化对象列表
        List<NoticeTemplatePO> templatePOs = noticeTemplateDao.findByBusinessType(businessType);
        
        // 2.转换为聚合根列表并返回
        return templatePOs.stream()
                .map(NoticeTemplateConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeTemplateAggregate> pageQuery(NoticeTemplatePageQueryReq req) {
        // 1.执行分页查询（PageHelper已在Service层启动）
        List<NoticeTemplatePO> poList = noticeTemplateDao.selectByCondition(req);

        // 2.转换为聚合根列表
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
        // 1.调用DAO层查询启用的模板持久化对象
        List<NoticeTemplatePO> templatePOs = noticeTemplateDao.findEnabledTemplates();
        
        // 2.转换为聚合根列表并返回
        return templatePOs.stream()
                .map(NoticeTemplateConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }
}
