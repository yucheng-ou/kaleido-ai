package com.xiaoo.kaleido.notice.domain.adapter.port;

import com.xiaoo.kaleido.notice.domain.model.valobj.VerificationCode;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 验证码仓储接口（领域层）
 * 定义验证码值对象的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public interface VerificationCodeRepository {

    /**
     * 保存验证码
     *
     * @param verificationCode 验证码值对象
     * @return 保存后的验证码值对象
     */
    VerificationCode save(VerificationCode verificationCode);

    /**
     * 根据手机号和业务类型查找最新的有效验证码
     *
     * @param mobile       手机号
     * @param businessType 业务类型
     * @return 验证码值对象（如果存在）
     */
    Optional<VerificationCode> findLatestValidByMobileAndBusinessType(String mobile, String businessType);

    /**
     * 根据手机号、验证码和业务类型查找验证码
     *
     * @param mobile       手机号
     * @param code         验证码
     * @param businessType 业务类型
     * @return 验证码值对象（如果存在）
     */
    Optional<VerificationCode> findByMobileAndCodeAndBusinessType(String mobile, String code, String businessType);

    /**
     * 更新验证码状态
     *
     * @param verificationCode 验证码值对象
     * @return 更新后的验证码值对象
     */
    VerificationCode update(VerificationCode verificationCode);

    /**
     * 删除过期的验证码
     *
     * @param beforeTime 在此之前创建的验证码
     * @return 删除的数量
     */
    int deleteExpiredCodes(LocalDateTime beforeTime);

    /**
     * 统计某个手机号在时间段内的验证码发送次数
     *
     * @param mobile     手机号
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 发送次数
     */
    long countByMobileAndCreatedAtBetween(String mobile, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 使某个手机号的所有验证码失效
     *
     * @param mobile       手机号
     * @param businessType 业务类型
     * @return 更新的数量
     */
    int invalidateAllByMobileAndBusinessType(String mobile, String businessType);
}
