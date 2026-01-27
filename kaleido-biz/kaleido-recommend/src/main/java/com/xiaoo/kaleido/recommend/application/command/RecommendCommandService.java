package com.xiaoo.kaleido.recommend.application.command;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xiaoo.kaleido.api.admin.user.command.SendSmsCodeCommand;
import com.xiaoo.kaleido.api.admin.user.response.SmsCodeResponse;
import com.xiaoo.kaleido.api.coin.IRpcCoinService;
import com.xiaoo.kaleido.api.coin.enums.CoinBizTypeEnum;
import com.xiaoo.kaleido.api.wardrobe.IRpcOutfitService;
import com.xiaoo.kaleido.api.wardrobe.command.CreateOutfitWithClothingsCommand;
import com.xiaoo.kaleido.api.wardrobe.command.OutfitImageInfoCommand;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.limiter.annotation.RateLimit;
import com.xiaoo.kaleido.lock.annotation.DistributedLock;
import com.xiaoo.kaleido.recommend.config.RecommendConfig;
import com.xiaoo.kaleido.recommend.domain.recommend.adapter.repository.IRecommendRecordRepository;
import com.xiaoo.kaleido.recommend.domain.recommend.model.aggregate.RecommendRecordAggregate;
import com.xiaoo.kaleido.recommend.domain.recommend.service.IRecommendRecordDomainService;
import com.xiaoo.kaleido.recommend.types.exception.RecommendErrorCode;
import com.xiaoo.kaleido.recommend.types.exception.RecommendException;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐命令服务
 * <p>
 * 负责编排推荐相关的命令操作，包括创建推荐记录、删除推荐记录等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendCommandService {

    private final IRecommendRecordDomainService recommendRecordDomainService;
    private final IRecommendRecordRepository recommendRecordRepository;
    private final RecommendConfig recommendConfig;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcCoinService rpcCoinService;

    @DubboReference(version = RpcConstants.DUBBO_VERSION)
    private IRpcOutfitService rpcOutfitService;

    /**
     * 创建推荐记录
     * <p>
     * 用户输入提示词，创建推荐记录
     *
     * @param userId 用户ID
     * @param prompt 用户输入的推荐需求提示词
     * @return 创建的推荐记录ID
     */
//    @GlobalTransactional
    @SentinelResource(
            value = "createRecommendRecord",
            blockHandler = "createRecommendRecordBlockHandler",
            fallback = "createRecommendRecordFallback",
            exceptionsToIgnore = {IllegalArgumentException.class, BizException.class}
    )
    @RateLimit(key = "'recommend:user:' + #userId", limit = 5, window = 60, message = "请求频繁，请稍后再试")
    @DistributedLock(key = "'recommend:user:' + #userId")
    public String createRecommendRecord(String userId, String prompt) {
        log.info("开始创建推荐记录，用户ID: {}, 提示词: {}", userId, prompt);


        // 1.校验金币是否足够
        Result<Boolean> booleanResult = rpcCoinService.checkBalance(userId, CoinBizTypeEnum.OUTFIT_RECOMMEND);
        if (!booleanResult.getSuccess()) {
            throw RecommendException.of(booleanResult.getCode(), booleanResult.getMsg());
        }
        if (!booleanResult.getData()) {
            throw RecommendException.of(RecommendErrorCode.COIN_INSUFFICIENT);

        }

        // 2.生成穿搭信息（使用测试数据，因为AI服务还没有）
        CreateOutfitWithClothingsCommand command = CreateOutfitWithClothingsCommand.builder()
                .name("测试穿搭" + RandomUtil.randomString(4))
                .description("测试穿搭")
                .clothingIds(List.of("2016037684459589632", "2016037709516361728"))
                .images(List.of(OutfitImageInfoCommand.builder().path("20260121/69f6a835-0630-4293-b62a-ef4d1660716e.png").build()))
                .build();

        //3.保存穿搭
        Result<String> outfitRes = rpcOutfitService.createOutfitWithClothingsAndImages(userId, command);
        if (!outfitRes.getSuccess()) {
            throw RecommendException.of(outfitRes.getCode(), outfitRes.getMsg());
        }
        String outfitId = outfitRes.getData();

        // 4.调用领域服务创建推荐记录
        RecommendRecordAggregate recommendRecord = recommendRecordDomainService.createRecommendRecord(userId, prompt, outfitId);

        // 5.保存推荐记录
        recommendRecordRepository.save(recommendRecord);

        // 6.扣减金币
        deductCoins(userId, recommendRecord.getId());

        // 7.记录日志（穿搭保存部分暂时跳过，因为AI服务还没有）
        log.info("推荐记录创建成功，记录ID: {}, 用户ID: {}, 穿搭ID: {}",
                recommendRecord.getId(), userId, outfitId);

        return recommendRecord.getId();
    }


    /**
     * 扣减金币
     * <p>
     * 调用金币RPC服务扣减金币
     * 注意：这里依赖扣费服务进行余额检查
     *
     * @param userId            用户ID
     * @param recommendRecordId 推荐记录ID
     */
    private void deductCoins(String userId, String recommendRecordId) {
        try {
            Result<Void> result = rpcCoinService.processRecommendGeneration(userId, recommendRecordId);

            if (!Boolean.TRUE.equals(result.getSuccess())) {
                log.error("金币扣减失败，用户ID: {}, 推荐记录ID: {}, 错误: {}",
                        userId, recommendRecordId, result.getMsg());
                throw RecommendException.of(result.getCode(), result.getMsg());
            }

            log.info("金币扣减成功，用户ID: {}, 推荐记录ID: {}", userId, recommendRecordId);
        } catch (Exception e) {
            log.error("调用金币服务异常，用户ID: {}, 推荐记录ID: {}", userId, recommendRecordId, e);
            throw RecommendException.of(RecommendErrorCode.COIN_SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    /**
     * 创建推荐记录限流处理函数
     *
     * @param userId 用户ID
     * @param prompt 用户输入的推荐需求提示词
     * @param ex     限流异常
     * @return 限流响应
     */
    public String createRecommendRecordBlockHandler(String userId, String prompt, BlockException ex) {
        log.warn("创建推荐记录接口触发限流，用户ID: {}, 异常: {}", userId, ex.getClass().getSimpleName());
        throw RecommendException.of(RecommendErrorCode.SYSTEM_BUSY, "请求过于频繁，请稍后再试");
    }

    /**
     * 创建推荐记录降级处理函数
     *
     * @param userId    用户ID
     * @param prompt    用户输入的推荐需求提示词
     * @param throwable 降级异常
     * @return 降级响应
     */
    public String createRecommendRecordFallback(String userId, String prompt, Throwable throwable) {
        log.error("创建推荐记录接口触发降级，用户ID: {}, 异常: {}", userId, throwable.getMessage(), throwable);
        throw RecommendException.of(RecommendErrorCode.SYSTEM_DEGRADED, "服务暂时不可用，请稍后重试");
    }

}
