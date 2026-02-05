package com.xiaoo.kaleido.user.domain.model.valobj;

import com.xiaoo.kaleido.user.types.exception.UserErrorCode;
import com.xiaoo.kaleido.user.types.exception.UserException;
import lombok.Getter;
import lombok.Value;

import java.util.Random;

/**
 * 邀请码值对象
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Getter
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
     * -- GETTER --
     * 获取邀请码字符串
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
     * <p>
     * 从字符串创建邀请码值对象，会进行格式验证
     *
     * @param code 邀请码字符串，不能为空且必须符合格式要求
     * @return 邀请码值对象
     */
    public static InvitationCode fromString(String code) {
        validate(code);
        return new InvitationCode(code);
    }

    /**
     * 生成随机邀请码
     * <p>
     * 生成一个随机的6位邀请码，使用排除易混淆字符的字符集
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
     * <p>
     * 验证邀请码是否符合格式要求，包括长度、字符集等
     *
     * @param code 待验证的邀请码，不能为空
     */
    private static void validate(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw UserException.of(UserErrorCode.INVITE_CODE_EMPTY);
        }

        if (code.length() != CODE_LENGTH) {
            throw UserException.of(UserErrorCode.INVITE_CODE_LENGTH_ERROR);
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
                throw UserException.of(UserErrorCode.INVITE_CODE_FORMAT_ERROR);
            }
        }
    }


    /**
     * 判断邀请码是否有效格式（静态方法，用于验证）
     * <p>
     * 验证邀请码格式但不抛出异常，返回布尔值结果
     *
     * @param code 邀请码字符串，不能为空
     * @return 是否有效格式，true表示格式正确，false表示格式错误
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
     * <p>
     * 根据用户ID生成确定性的邀请码，相同用户ID总是生成相同的邀请码
     * 注意：主要用于测试或特定场景，生产环境通常使用随机生成
     *
     * @param userId 用户ID，不能为空
     * @return 确定性邀请码，如果用户ID太短则回退到随机生成
     */
    public static InvitationCode fromUserId(String userId) {
        if (userId == null || userId.length() < CODE_LENGTH) {
            // 如果用户ID太短，回退到随机生成
            return generate();
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
