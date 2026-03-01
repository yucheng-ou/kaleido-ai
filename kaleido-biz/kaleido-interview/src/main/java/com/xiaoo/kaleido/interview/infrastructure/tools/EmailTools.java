package com.xiaoo.kaleido.interview.infrastructure.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 邮件相关工具类
 * <p>
 * 提供Offer邮件发送功能供AI调用（模拟实现）
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailTools {

    /**
     * 发送Offer邮件
     *
     * @param candidateName 候选人姓名
     * @param email        候选人邮箱
     * @param position     职位名称
     * @param salary       薪资
     * @param onboardDate  入职日期
     * @return 操作结果
     */
    @Tool(value = "发送Offer邮件给候选人", name = "sendOfferEmail")
    public String sendOfferEmail(
            @P("候选人姓名") String candidateName,
            @P("候选人邮箱地址") String email,
            @P("职位名称") String position,
            @P("薪资，例如：20000元/月") String salary,
            @P("入职日期，格式为yyyy-MM-dd") String onboardDate) {

        log.info("AI调用发送Offer邮件工具，候选人: {}, 邮箱: {}, 职位: {}, 薪资: {}, 入职日期: {}",
                candidateName, email, position, salary, onboardDate);

        try {
            // 模拟邮件发送
            String emailContent = generateOfferEmailContent(candidateName, position, salary, onboardDate);
            
            log.info("=== 模拟发送邮件 ===");
            log.info("收件人: {}", email);
            log.info("主题: 【录用通知】恭喜您加入我们！");
            log.info("内容:\n{}", emailContent);
            log.info("==================");

            return String.format("Offer邮件已成功发送给 %s（%s）！职位: %s，薪资: %s，入职日期: %s",
                    candidateName, email, position, salary, onboardDate);
        } catch (Exception e) {
            log.error("发送Offer邮件失败: {}", e.getMessage(), e);
            return "发送Offer邮件失败: " + e.getMessage();
        }
    }

    /**
     * 生成Offer邮件内容
     */
    private String generateOfferEmailContent(String candidateName, String position, 
                                              String salary, String onboardDate) {
        return String.format("""
                尊敬的 %s：
                
                您好！
                
                经过我司招聘团队的评估与面试，我们很高兴地通知您，您已被正式录用！
                
                录用信息如下：
                - 职位：%s
                - 薪资：%s
                - 入职日期：%s
                
                请于入职当日携带以下材料到公司报到：
                1. 身份证原件及复印件
                2. 学历证书原件及复印件
                3. 离职证明（前一份工作）
                4. 一寸免冠照片2张
                
                如有任何疑问，请随时联系我们。
                
                祝贺您加入我们！
                
                人力资源部
                %s
                """, 
                candidateName, 
                position, 
                salary, 
                onboardDate,
                LocalDate.now());
    }
}
