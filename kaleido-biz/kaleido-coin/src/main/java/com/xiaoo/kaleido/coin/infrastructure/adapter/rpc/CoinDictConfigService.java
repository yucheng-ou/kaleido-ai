package com.xiaoo.kaleido.coin.infrastructure.adapter.rpc;

import com.xiaoo.kaleido.api.admin.dict.IRpcAdminDictService;
import com.xiaoo.kaleido.api.admin.dict.response.DictResponse;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.coin.types.exception.CoinErrorCode;
import com.xiaoo.kaleido.coin.types.exception.CoinException;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * 金币字典配置服务
 * <p>
 * 从字典表实时获取金币服务配置
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CoinDictConfigService {

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcAdminDictService dictRpcService;

    /**
     * 字典类型编码：金币服务配置
     */
    private static final String DICT_TYPE_CODE = "COIN_CONFIG";

    /**
     * 获取初始余额
     *
     * @return 初始余额
     */
    public Long getInitialBalance() {
        return getDictValue("INITIAL_BALANCE", 100L);
    }

    /**
     * 获取邀请奖励
     *
     * @return 邀请奖励金额
     */
    public Long getInviteReward() {
        return getDictValue("INVITE_REWARD", 100L);
    }

    /**
     * 获取位置创建消耗
     *
     * @return 位置创建消耗金额
     */
    public Long getLocationCost() {
        return getDictValue("LOCATION_COST", 50L);
    }

    /**
     * 获取搭配创建消耗
     *
     * @return 搭配创建消耗金额
     */
    public Long getOutfitCost() {
        return getDictValue("OUTFIT_COST", 80L);
    }

    /**
     * 根据业务类型获取金额
     *
     * @param bizType 业务类型
     * @return 对应的金额
     */
    public Long getAmountByBizType(String bizType) {
        if (bizType == null) {
            throw new CoinException(CoinErrorCode.STREAM_BIZ_TYPE_INVALID);
        }

        return switch (bizType.toUpperCase()) {
            case "INVITE" -> getInviteReward();
            case "LOCATION" -> getLocationCost();
            case "OUTFIT" -> getOutfitCost();
            case "INITIAL" -> getInitialBalance();
            default -> throw new CoinException(CoinErrorCode.STREAM_BIZ_TYPE_INVALID);
        };
    }

    /**
     * 获取字典值
     *
     * @param dictCode     字典编码
     * @param defaultValue 默认值
     * @return 字典值
     */
    private Long getDictValue(String dictCode, Long defaultValue) {
        try {
            Result<DictResponse> result = dictRpcService.getDictByCode(DICT_TYPE_CODE, dictCode);
            if (result != null && result.getSuccess() && result.getData() != null) {
                String dictValue = result.getData().getDictValue();
                if (dictValue != null && !dictValue.trim().isEmpty()) {
                    return Long.parseLong(dictValue.trim());
                }
            }
        } catch (Exception e) {
            log.warn("查询字典失败，使用默认值，typeCode={}, dictCode={}, defaultValue={}",
                    DICT_TYPE_CODE, dictCode, defaultValue, e);
        }

        log.debug("使用字典默认值，dictCode={}, defaultValue={}", dictCode, defaultValue);
        return defaultValue;
    }

    /**
     * 验证配置有效性
     */
    public void validateConfig() {
        Long initialBalance = getInitialBalance();
        Long inviteReward = getInviteReward();
        Long locationCost = getLocationCost();
        Long outfitCost = getOutfitCost();

        if (initialBalance == null || initialBalance < 0) {
            throw new CoinException(CoinErrorCode.INITIAL_BALANCE_INVALID);
        }
        if (inviteReward == null || inviteReward <= 0) {
            throw new CoinException(CoinErrorCode.INVITE_REWARD_INVALID);
        }
        if (locationCost == null || locationCost <= 0) {
            throw new CoinException(CoinErrorCode.LOCATION_COST_INVALID);
        }
        if (outfitCost == null || outfitCost <= 0) {
            throw new CoinException(CoinErrorCode.OUTFIT_COST_INVALID);
        }
    }

    /**
     * 获取配置描述
     *
     * @return 配置描述字符串
     */
    public String getDescription() {
        return String.format(
                "初始余额：%d，邀请奖励：%d，位置创建消耗：%d，搭配创建消耗：%d",
                getInitialBalance(), getInviteReward(), getLocationCost(), getOutfitCost()
        );
    }
}
