package com.xiaoo.kaleido.wardrobe.domain.image.chain.handler;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.wardrobe.domain.image.chain.AbstractImageProcessingHandler;
import com.xiaoo.kaleido.wardrobe.domain.image.context.ImageProcessingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 验证处理器
 * <p>
 * 责任链中的第一个处理器，负责验证图片基本信息
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Slf4j
@Component
public class ValidationHandler extends AbstractImageProcessingHandler {
    
    @Override
    protected void doHandle(ImageProcessingContext context) {
        log.debug("开始验证图片信息: {}", context.getBasicImageInfo());
        
        // 验证路径不能为空
        if (StrUtil.isBlank(context.getBasicImageInfo().getPath())) {
            context.setError("图片路径不能为空");
            log.warn("图片路径验证失败: 路径为空");
            return;
        }
        
        // 验证图片顺序
        Integer imageOrder = context.getBasicImageInfo().getImageOrder();
        if (imageOrder == null || imageOrder < 0) {
            context.setError("图片顺序必须大于等于0");
            log.warn("图片顺序验证失败: {}", imageOrder);
            return;
        }
        
        // 验证是否为主图（可以为null，表示非主图）
        Boolean isPrimary = context.getBasicImageInfo().getIsPrimary();
        if (isPrimary == null) {
            // 默认为false
            context.setAttribute("isPrimary", false);
        }
        
        log.debug("图片信息验证通过: {}", context.getBasicImageInfo().getPath());
    }
    
    @Override
    protected String getHandlerName() {
        return "ValidationHandler";
    }
}
