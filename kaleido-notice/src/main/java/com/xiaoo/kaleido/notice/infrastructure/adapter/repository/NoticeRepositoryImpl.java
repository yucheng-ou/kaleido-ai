package com.xiaoo.kaleido.notice.infrastructure.adapter.repository;

import com.xiaoo.kaleido.api.notice.enums.TargetTypeEnum;
import com.xiaoo.kaleido.api.notice.query.NoticePageQueryReq;
import com.xiaoo.kaleido.notice.domain.adapter.repository.INoticeRepository;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.infrastructure.adapter.repository.convertor.NoticeConvertor;
import com.xiaoo.kaleido.notice.infrastructure.dao.NoticeDao;
import com.xiaoo.kaleido.notice.infrastructure.dao.po.NoticePO;
import com.xiaoo.kaleido.api.notice.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.notice.types.constant.RedisKeyConstant;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import com.xiaoo.kaleido.redis.constant.RedisConstant;
import com.xiaoo.kaleido.redis.service.RedissonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 通知仓储实现（基础设施层）
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements INoticeRepository {

    private final NoticeDao noticeDao;
    private final RedissonService redissonService;

    @Override
    public void cacheVerifyCode(TargetTypeEnum targetType, String mobile, String verifyCode) {
        redissonService.setValue(
                String.format(RedisKeyConstant.SmsVerifyCode.SMS_VERIFY_CODE_KEY, targetType.name().toLowerCase(), mobile),
                verifyCode,
                RedisKeyConstant.SmsVerifyCode.SMS_VERIFY_CODE_EXPIRE_TIME
        );
    }

    @Override
    public Boolean checkVerifyCode(TargetTypeEnum targetType, String mobile, String verifyCode) {
        // 1.从缓存取出验证码
        String value = redissonService.getValue(
                String.format(RedisKeyConstant.SmsVerifyCode.SMS_VERIFY_CODE_KEY, targetType.name().toLowerCase(), mobile)
        );

        // 2.校验验证码是否一致
        return verifyCode.equals(value);
    }

    @Override
    public NoticeAggregate findById(String id) {
        // 1.调用DAO层查询持久化对象
        NoticePO noticePO = noticeDao.selectById(id);

        // 2.判断查询结果
        if (noticePO == null) {
            return null;
        }

        // 3.转换为聚合根并返回
        return NoticeConvertor.INSTANCE.toAggregate(noticePO);
    }

    @Override
    public NoticeAggregate save(NoticeAggregate notice) {
        // 1.将聚合根转换为持久化对象
        NoticePO noticePO = NoticeConvertor.INSTANCE.toPO(notice);

        // 2.新增操作
        noticeDao.insert(noticePO);

        // 3.重新加载并返回聚合根
        return NoticeConvertor.INSTANCE.toAggregate(noticePO);
    }

    @Override
    public NoticeAggregate update(NoticeAggregate notice) {
        // 1.将聚合根转换为持久化对象
        NoticePO noticePO = NoticeConvertor.INSTANCE.toPO(notice);

        // 2.更新操作
        noticeDao.updateById(noticePO);

        // 3.重新加载并返回聚合根
        return NoticeConvertor.INSTANCE.toAggregate(noticePO);
    }

    @Override
    public void deleteById(String id) {
        // 1.调用DAO层删除记录
        noticeDao.deleteById(id);
    }

    @Override
    public List<NoticeAggregate> findByStatus(NoticeStatusEnum status) {
        // 1.调用DAO层根据状态查询持久化对象列表
        List<NoticePO> noticePOs = noticeDao.findByStatus(status);

        // 2.转换为聚合根列表并返回
        return noticePOs.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeAggregate> findByTarget(String target) {
        // 1.调用DAO层根据目标地址查询持久化对象列表
        List<NoticePO> noticePOs = noticeDao.findByTargetAddress(target);

        // 2.转换为聚合根列表并返回
        return noticePOs.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeAggregate> findByBusinessType(String businessType) {
        // 1.调用DAO层根据业务类型查询持久化对象列表
        List<NoticePO> noticePOs = noticeDao.findByBusinessType(businessType);

        // 2.转换为聚合根列表并返回
        return noticePOs.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    /**
     * 分页查询通知
     *
     * @param req 查询条件
     * @return 通知列表
     */
    public List<NoticeAggregate> pageQuery(NoticePageQueryReq req) {
        // 1.执行分页查询（PageHelper已在Service层启动）
        List<NoticePO> poList = noticeDao.selectByCondition(req);

        // 2.转换为聚合根列表
        return poList.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeAggregate> findRetryNotices(Integer limit) {
        // 1.调用DAO层查询需要重试的通知持久化对象
        List<NoticePO> noticePOs = noticeDao.findRetryNotices(limit);

        // 2.转换为聚合根列表并返回
        return noticePOs.stream()
                .map(NoticeConvertor.INSTANCE::toAggregate)
                .collect(Collectors.toList());
    }
}
