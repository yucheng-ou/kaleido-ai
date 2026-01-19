package com.xiaoo.kaleido.coin.domain.account.service.dto;

import com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream;
import lombok.Data;

/**
 * 金币操作DTO
 * <p>
 * 用于领域服务方法的参数传递
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
public class CoinOperationDTO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 操作金额
     */
    private Long amount;

    /**
     * 业务类型
     */
    private CoinStream.BizType bizType;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建收入操作DTO
     *
     * @param userId 用户ID
     * @param amount 收入金额
     * @param bizType 业务类型
     * @param bizId 业务ID
     * @param remark 备注
     * @return 收入操作DTO
     */
    public static CoinOperationDTO createIncome(
            String userId,
            Long amount,
            CoinStream.BizType bizType,
            String bizId,
            String remark) {
        CoinOperationDTO dto = new CoinOperationDTO();
        dto.setUserId(userId);
        dto.setAmount(amount);
        dto.setBizType(bizType);
        dto.setBizId(bizId);
        dto.setRemark(remark);
        return dto;
    }

    /**
     * 创建支出操作DTO
     *
     * @param userId 用户ID
     * @param amount 支出金额
     * @param bizType 业务类型
     * @param bizId 业务ID
     * @param remark 备注
     * @return 支出操作DTO
     */
    public static CoinOperationDTO createExpense(
            String userId,
            Long amount,
            CoinStream.BizType bizType,
            String bizId,
            String remark) {
        CoinOperationDTO dto = new CoinOperationDTO();
        dto.setUserId(userId);
        dto.setAmount(amount);
        dto.setBizType(bizType);
        dto.setBizId(bizId);
        dto.setRemark(remark);
        return dto;
    }

    /**
     * 验证DTO有效性
     *
     * @return true-有效，false-无效
     */
    public boolean isValid() {
        return userId != null && !userId.trim().isEmpty()
                && amount != null && amount > 0
                && bizType != null;
    }

    /**
     * 获取操作描述
     *
     * @return 操作描述字符串
     */
    public String getDescription() {
        return String.format("用户[%s] %s %d 金币，业务类型：%s，业务ID：%s",
                userId,
                bizType != null ? bizType.name() : "未知",
                amount,
                bizType != null ? bizType.name() : "未知",
                bizId != null ? bizId : "无");
    }
}
