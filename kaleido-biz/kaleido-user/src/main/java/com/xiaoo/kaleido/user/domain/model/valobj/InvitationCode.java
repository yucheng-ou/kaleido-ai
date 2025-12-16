package com.xiaoo.kaleido.user.domain.model.valobj;

import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.Value;

import java.util.Random;

/**
 * 邀请码值对象
 * 封装邀请码的生成、验证和业务规则
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Value
public class InvitationCode {

    /**
     * 邀请码长度
     */
    private static final int CODE_LENGTH = 6;

    /**
     * 有效字符集（排除易混淆字符：0/O, 1/I, l）
     */
    private static final char[] VALID_CHARS = {
        '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 
        'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
        'W', 'X', 'Y', 'Z'
    };

    /**
     * 邀请码值
     */
    String value;

    /**
     * 私有构造方法，通过工厂方法创建
     */
    private InvitationCode(String value) {
        this.value = value;
    }

    /**
     * 创建邀请码（从字符串）
     *
     * @param code 邀请码字符串
     * @return 邀请码值对象
     * @throws UserException 如果邀请码无效
     */
    public static InvitationCode fromString(String code) {
        validate(code);
        return new InvitationCode(code);
    }

    /**
     * 生成随机邀请码
     *
     * @return 新的邀请码值对象
     */
    public static InvitationCode generate() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(VALID_CHARS.length);
            sb.append(VALID_CHARS[index]);
        }
        
        return new InvitationCode(sb.toString());
    }

    /**
     * 验证邀请码格式
     *
     * @param code 待验证的邀请码
     * @throws UserException 如果邀请码无效
     */
    private static void validate(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new UserException(UserErrorCode.INVITE_CODE_EMPTY);
        }
        
        if (code.length() != CODE_LENGTH) {
            throw new UserException(UserErrorCode.INVITE_CODE_LENGTH_ERROR);
        }
        
        // 验证字符是否都在有效字符集中
        for (char c : code.toCharArray()) {
            boolean valid = false;
            for (char validChar : VALID_CHARS) {
                if (c == validChar) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                throw new UserException(UserErrorCode.INVITE_CODE_FORMAT_ERROR);
            }
        }
    }

    /**
     * 获取邀请码字符串
     */
    public String getValue() {
        return value;
    }

    /**
     * 判断是否相等
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvitationCode that = (InvitationCode) o;
        return value.equals(that.value);
    }

    /**
     * 哈希码
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * 字符串表示
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * 判断邀请码是否有效格式（静态方法，用于验证）
     *
     * @param code 邀请码字符串
     * @return 是否有效格式
     */
    public static boolean isValidFormat(String code) {
        try {
            validate(code);
            return true;
        } catch (UserException e) {
            return false;
        }
    }

    /**
     * 生成基于用户ID的确定性邀请码（可选，用于测试或特定场景）
     *
     * @param userId 用户ID
     * @return 确定性邀请码
     */
    public static InvitationCode fromUserId(String userId) {
        if (userId == null || userId.length() < CODE_LENGTH) {
            return generate(); // 如果用户ID太短，回退到随机生成
        }
        
        // 使用用户ID的哈希值生成确定性邀请码
        int hash = userId.hashCode();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        
        for (int i = 0; i < CODE_LENGTH; i++) {
            // 使用哈希值的不同部分选择字符
            int index = Math.abs((hash + i * 31) % VALID_CHARS.length);
            sb.append(VALID_CHARS[index]);
        }
        
        return new InvitationCode(sb.toString());
    }
}
