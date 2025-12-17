package com.xiaoo.kaleido.notice.domain.model.valobj;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 验证码值对象
 * <p>
 * 验证码业务逻辑，包含验证码值、过期时间、使用状态等
 * </p>
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Data
@Builder
public class VerificationCode {
    /**
     * 验证码值
     */
    private final String code;

    /**
     * 手机号
     */
    private final String mobile;

    /**
     * 业务类型
     */
    private final String businessType;

    /**
     * 创建时间
     */
    private final LocalDateTime createdAt;

    /**
     * 过期时间
     */
    private final LocalDateTime expiredAt;

    /**
     * 是否已使用
     */
    private final boolean used;

    /**
     * 使用时间
     */
    private final LocalDateTime usedAt;

    /**
     * 验证尝试次数
     */
    private final int attemptCount;

    /**
     * 创建验证码值对象
     *
     * @param code          验证码值
     * @param mobile        手机号
     * @param businessType  业务类型
     * @param createdAt     创建时间
     * @param expiredAt     过期时间
     * @param used          是否已使用
     * @param usedAt        使用时间
     * @param attemptCount  验证尝试次数
     */
    public VerificationCode(String code, String mobile, String businessType, 
                           LocalDateTime createdAt, LocalDateTime expiredAt, 
                           boolean used, LocalDateTime usedAt, int attemptCount) {
        this.code = code;
        this.mobile = mobile;
        this.businessType = businessType;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.used = used;
        this.usedAt = usedAt;
        this.attemptCount = attemptCount;
    }

    /**
     * 验证码是否已过期
     *
     * @return true 表示已过期
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }

    /**
     * 验证码是否有效（未使用且未过期）
     *
     * @return true 表示有效
     */
    public boolean isValid() {
        return !used && !isExpired();
    }

    /**
     * 标记为已使用
     *
     * @return 新的验证码对象（已使用状态）
     */
    public VerificationCode markAsUsed() {
        return VerificationCode.builder()
                .code(this.code)
                .mobile(this.mobile)
                .businessType(this.businessType)
                .createdAt(this.createdAt)
                .expiredAt(this.expiredAt)
                .used(true)
                .usedAt(LocalDateTime.now())
                .attemptCount(this.attemptCount)
                .build();
    }

    /**
     * 增加验证尝试次数
     *
     * @return 新的验证码对象（尝试次数+1）
     */
    public VerificationCode incrementAttemptCount() {
        return VerificationCode.builder()
                .code(this.code)
                .mobile(this.mobile)
                .businessType(this.businessType)
                .createdAt(this.createdAt)
                .expiredAt(this.expiredAt)
                .used(this.used)
                .usedAt(this.usedAt)
                .attemptCount(this.attemptCount + 1)
                .build();
    }

    /**
     * 生成新的验证码
     *
     * @param mobile        手机号
     * @param businessType  业务类型
     * @param code          验证码值
     * @param expireMinutes 过期时间（分钟）
     * @return 新的验证码对象
     */
    public static VerificationCode generate(String mobile, String businessType, String code, int expireMinutes) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMinutes(expireMinutes);
        
        return VerificationCode.builder()
                .code(code)
                .mobile(mobile)
                .businessType(businessType)
                .createdAt(now)
                .expiredAt(expiredAt)
                .used(false)
                .usedAt(null)
                .attemptCount(0)
                .build();
    }
}
