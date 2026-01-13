package com.xiaoo.kaleido.user.infrastructure.component;

import com.xiaoo.kaleido.user.infrastructure.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 布隆过滤器初始化器
 * <p>
 * 在服务启动时将数据库中已有的邀请码加载到布隆过滤器中
 *
 * @author ouyucheng
 * @date 2026/1/13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BloomFilterInitializer implements CommandLineRunner {

    private final UserDao userDao;
    private final RBloomFilter<String> inviteCodeBloomFilter;

    /**
     * 服务启动时执行，加载历史邀请码到布隆过滤器
     *
     * @param args 命令行参数
     */
    @Override
    public void run(String... args) {
        try {
            log.info("开始加载历史邀请码到布隆过滤器...");

            // 分批加载邀请码，避免一次性加载过多数据
            int batchSize = 1000;
            int offset = 0;
            int totalLoaded = 0;

            while (true) {
                // 分批查询邀请码
                List<String> inviteCodes = userDao.selectInviteCodesWithLimit(offset, batchSize);

                if (inviteCodes.isEmpty()) {
                    break;
                }

                // 将邀请码添加到布隆过滤器
                for (String inviteCode : inviteCodes) {
                    inviteCodeBloomFilter.add(inviteCode);
                }

                totalLoaded += inviteCodes.size();
                offset += batchSize;

                log.debug("已加载 {} 个邀请码到布隆过滤器，当前总数: {}", inviteCodes.size(), totalLoaded);

                // 如果返回的数量小于批次大小，说明已经加载完所有数据
                if (inviteCodes.size() < batchSize) {
                    break;
                }
            }

            log.info("历史邀请码加载完成，共加载 {} 个邀请码到布隆过滤器", totalLoaded);

        } catch (Exception e) {
            log.error("加载历史邀请码到布隆过滤器失败", e);
            // 注意：初始化失败不应阻止应用启动，布隆过滤器会动态添加新数据
        }
    }
}
