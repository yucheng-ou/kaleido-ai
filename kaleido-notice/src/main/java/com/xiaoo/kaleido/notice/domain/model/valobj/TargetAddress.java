package com.xiaoo.kaleido.notice.domain.model.valobj;

import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.api.notice.enums.TargetTypeEnum;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 目标地址值对象
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
@Getter
public class TargetAddress {

    /**
     * 目标地址
     */
    private final String address;

    /**
     * 通知类型
     */
    private final NoticeTypeEnum noticeType;

    /**
     * 目标类型
     */
    private final TargetTypeEnum targetType;

    // 正则表达式模式
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern WECHAT_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{28}$");

    private TargetAddress(String address, NoticeTypeEnum noticeType, TargetTypeEnum targetType) {
        this.address = address;
        this.noticeType = noticeType;
        this.targetType = targetType;
        validate();
    }

    /**
     * 创建目标地址
     *
     * @param address 地址字符串
     * @param noticeType 通知类型
     * @return 目标地址
     */
    public static TargetAddress create(String address, NoticeTypeEnum noticeType) {
        return new TargetAddress(address, noticeType, TargetTypeEnum.USER);
    }

    /**
     * 创建目标地址
     *
     * @param address 地址字符串
     * @param noticeType 通知类型
     * @param targetType 目标类型
     * @return 目标地址
     */
    public static TargetAddress create(String address, NoticeTypeEnum noticeType, TargetTypeEnum targetType) {
        return new TargetAddress(address, noticeType, targetType);
    }

    /**
     * 验证地址格式
     */
    private void validate() {
        if (address == null || address.trim().isEmpty()) {
            throw NoticeException.of(NoticeErrorCode.TARGET_USER_EMPTY);
        }
        if (noticeType == null) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TYPE_EMPTY);
        }
        if (targetType == null) {
            throw NoticeException.of(NoticeErrorCode.TARGET_USER_EMPTY);
        }

        String trimmedAddress = address.trim();
        boolean isValid = switch (noticeType) {
            case SMS -> PHONE_PATTERN.matcher(trimmedAddress).matches();
            case EMAIL -> EMAIL_PATTERN.matcher(trimmedAddress).matches();
            case WECHAT ->
                // 微信OpenID格式可能变化，这里使用较宽松的验证
                    WECHAT_PATTERN.matcher(trimmedAddress).matches() ||
                            trimmedAddress.length() >= 20 && trimmedAddress.length() <= 50;
        };

        if (!isValid) {
            throw NoticeException.of(NoticeErrorCode.TARGET_USER_EMPTY);
        }
    }

    /**
     * 获取格式化地址（去除空格）
     */
    public String getFormattedAddress() {
        return address.trim();
    }

    /**
     * 获取地址类型描述
     */
    public String getAddressType() {
        return switch (noticeType) {
            case SMS -> "手机号";
            case EMAIL -> "邮箱";
            case WECHAT -> "微信OpenID";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetAddress that = (TargetAddress) o;
        return Objects.equals(address, that.address) &&
                noticeType == that.noticeType &&
                targetType == that.targetType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, noticeType, targetType);
    }

    @Override
    public String toString() {
        return "TargetAddress{" +
                "address='" + address + '\'' +
                ", noticeType=" + noticeType +
                ", targetType=" + targetType +
                '}';
    }
}
